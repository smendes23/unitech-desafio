package br.com.unitechdesafio.infrastructure.exception;

public class ResourceNotFoundException extends RuntimeException {

    public static void throwIf(boolean test) {

        if (test) {

            throw new ResourceNotFoundException();
        }
    }
}
