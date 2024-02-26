package br.com.unitechdesafio.business.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(NON_NULL)
public class UserDetailsRequest{
    private String id;
    private String name;
    private String username;
    private String password;
    private String code;
    private String roles = "ROLE_USER";
    private Boolean enabled;
}
