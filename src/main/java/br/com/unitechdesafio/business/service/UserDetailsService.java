package br.com.unitechdesafio.business.service;

import br.com.unitechdesafio.business.domain.entity.UserDetailsEntity;
import br.com.unitechdesafio.business.domain.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserDetailsService  implements ReactiveUserDetailsService {

  private final UserDetailsRepository usersRepository;
  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return usersRepository.findByUsername(username)
            .cast(UserDetails.class);
  }

  public Mono<UserDetailsEntity> findDetails(String username) {
    return usersRepository.findByUsername(username);
  }

}
