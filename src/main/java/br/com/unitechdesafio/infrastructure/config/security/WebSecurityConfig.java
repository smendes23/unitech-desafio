package br.com.unitechdesafio.infrastructure.config.security;

import br.com.unitechdesafio.business.service.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/registration/**").permitAll()
                .pathMatchers(HttpMethod.PUT, "/registration/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.POST, "/lesson/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.PUT, "/lesson/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    ReactiveAuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    }
}
