package br.com.unitechdesafio.business.domain.entity;

import br.com.unitechdesafio.infrastructure.model.BackOfficeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Document("REGISTRATION")
public class RegistrationEntity implements BackOfficeEntity{

    @Id
    private String id;
    private String name;
    private String email;
    private String status;

}
