package br.com.unitechdesafio.business.domain.entity;

import br.com.unitechdesafio.infrastructure.model.BackOfficeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class JwtEntity implements BackOfficeEntity {

    private String id;

    private String token;

    public JwtEntity(String token) {
        this.token = token;
    }
}