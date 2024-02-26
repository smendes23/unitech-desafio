package br.com.unitechdesafio.business.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class RegistrationRequest {
    private String id;
    private String name;
    private String email;
    private String status;
}
