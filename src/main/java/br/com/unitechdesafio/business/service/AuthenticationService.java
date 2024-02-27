package br.com.unitechdesafio.business.service;

import br.com.unitechdesafio.business.domain.dto.request.LoginRequest;
import br.com.unitechdesafio.business.domain.entity.JwtEntity;
import br.com.unitechdesafio.infrastructure.config.security.JwtSupport;
import br.com.unitechdesafio.infrastructure.dto.Jwt;
import br.com.unitechdesafio.infrastructure.service.BackOfficeService;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;


@Service
public class AuthenticationService extends BackOfficeService<JwtEntity, LoginRequest> {
    private final JwtSupport jwtSupport;
    private final PasswordEncoder encoder;
    private final UserDetailsService users;
    public AuthenticationService(ModelMapper modelMapper,
                                 ApplicationEventPublisher applicationEventPublisher,
                                 JwtSupport jwtSupport,
                                 PasswordEncoder encoder,
                                 UserDetailsService users
    ) {
        super(null, modelMapper, applicationEventPublisher);
        this.jwtSupport = jwtSupport;
        this.encoder = encoder;
        this.users = users;
    }

    @Override
    protected Mono<String> processObject(LoginRequest valueObject) {
        return users.findByUsername(valueObject.getUsername())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)))
                .flatMap(user -> {
                    if (encoder.matches(valueObject.getPassword(), user.getPassword())) {
                        return users.findDetails(user.getUsername())
                                .map(usr -> new Jwt(jwtSupport.generate(usr).getValue()))
                                .map(Jwt::getToken);
                    }
                        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
                });
    }
}
