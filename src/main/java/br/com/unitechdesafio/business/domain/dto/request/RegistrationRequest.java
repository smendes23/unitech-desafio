package br.com.unitechdesafio.business.domain.dto.request;

import br.com.unitech.business.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class RegistrationRequest {
    private String id;
    private String name;
    private String email;
    private String status;
    private List<Role> roles;
}
