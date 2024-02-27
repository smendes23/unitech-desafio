package br.com.unitechdesafio.business.service;

import br.com.unitechdesafio.BaseTestRunner;
import br.com.unitechdesafio.business.domain.entity.UserDetailsEntity;
import br.com.unitechdesafio.business.domain.repository.UserDetailsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserDetailsService.class})
class UserDetailsServiceTest extends BaseTestRunner {
  @MockBean
  private UserDetailsRepository userDetailsRepository;

  @Autowired
  private UserDetailsService userDetailsService;

  @Test
  void testFindByUsername() {
    // Arrange
    Mono<UserDetailsEntity> justResult = Mono.just(new UserDetailsEntity());
    when(userDetailsRepository.findByUsername(Mockito.<String>any())).thenReturn(justResult);

    // Act
    userDetailsService.findByUsername("janedoe");

    // Assert
    verify(userDetailsRepository).findByUsername(eq("janedoe"));
  }

  @Test
  void testFindByUsername2() {
    // Arrange
    Mono<UserDetailsEntity> mono = mock(Mono.class);
    Mono<UserDetails> justResult = Mono.just(new UserDetailsEntity());
    when(mono.cast(Mockito.<Class<UserDetails>>any())).thenReturn(justResult);
    when(userDetailsRepository.findByUsername(Mockito.<String>any())).thenReturn(mono);

    // Act
    Mono<UserDetails> actualFindByUsernameResult = userDetailsService.findByUsername("janedoe");

    // Assert
    verify(userDetailsRepository).findByUsername(eq("janedoe"));
    verify(mono).cast(Mockito.<Class<UserDetails>>any());
    assertSame(justResult, actualFindByUsernameResult);
  }
}
