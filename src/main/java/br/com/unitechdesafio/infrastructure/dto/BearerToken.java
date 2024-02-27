package br.com.unitechdesafio.infrastructure.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class BearerToken extends AbstractAuthenticationToken {

    private final String value;

    public BearerToken(String value) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.value = value;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return value;
    }

    @Override
    public Object getPrincipal() {
        return value;
    }

    public String getValue() {
        return value;
    }
}