package br.com.unitechdesafio.infrastructure.dto;

public class Profile {

    private final String username;

    public Profile(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}