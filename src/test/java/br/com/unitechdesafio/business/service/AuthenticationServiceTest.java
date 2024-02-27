package br.com.unitechdesafio.business.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.unitechdesafio.BaseTestRunner;
import br.com.unitechdesafio.business.domain.dto.request.LoginRequest;
import br.com.unitechdesafio.business.domain.entity.UserDetailsEntity;
import br.com.unitechdesafio.infrastructure.config.security.JwtSupport;

import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {AuthenticationService.class})
class AuthenticationServiceTest extends BaseTestRunner {
    @MockBean
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private JwtSupport jwtSupport;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void testProcessObject() {
        // Arrange
        Mono<UserDetails> justResult = Mono.just(new UserDetailsEntity());
        when(userDetailsService.findByUsername(Mockito.<String>any())).thenReturn(justResult);

        // Act
        authenticationService.processObject(new LoginRequest("janedoe", "iloveyou"));

        // Assert
        verify(userDetailsService).findByUsername(eq("janedoe"));
    }

    @Test
    void testProcessObject2() {
        // Arrange
        Mono<UserDetails> mono = mock(Mono.class);
        Mono<UserDetails> justResult = Mono.just(new UserDetailsEntity());
        when(mono.switchIfEmpty(Mockito.<Mono<UserDetails>>any())).thenReturn(justResult);
        when(userDetailsService.findByUsername(Mockito.<String>any())).thenReturn(mono);

        // Act
        authenticationService.processObject(new LoginRequest("janedoe", "iloveyou"));

        // Assert
        verify(userDetailsService).findByUsername(eq("janedoe"));
        verify(mono).switchIfEmpty(Mockito.<Mono<UserDetails>>any());
    }


    @Test
    void testProcessObject3() {
        // Arrange
        Mono<UserDetails> mono = mock(Mono.class);
        Mono<Object> justResult = Mono.just("Data");
        when(mono.flatMap(Mockito.<Function<UserDetails, Mono<Object>>>any())).thenReturn(justResult);
        Mono<UserDetails> mono2 = mock(Mono.class);
        when(mono2.switchIfEmpty(Mockito.<Mono<UserDetails>>any())).thenReturn(mono);
        when(userDetailsService.findByUsername(Mockito.<String>any())).thenReturn(mono2);

        // Act
        Mono<String> actualProcessObjectResult = authenticationService
                .processObject(new LoginRequest("janedoe", "iloveyou"));

        // Assert
        verify(userDetailsService).findByUsername(eq("janedoe"));
        verify(mono).flatMap(Mockito.<Function<UserDetails, Mono<Object>>>any());
        verify(mono2).switchIfEmpty(Mockito.<Mono<UserDetails>>any());
        assertSame(justResult, actualProcessObjectResult);
    }
}
