package br.com.unitechdesafio.infrastructure.dto;

public class Jwt {

    private final String token;

    public Jwt(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}