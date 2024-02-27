package br.com.unitechdesafio.business.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(NON_NULL)
public class LoginRequest {

    private String username;
    private  String password;

}