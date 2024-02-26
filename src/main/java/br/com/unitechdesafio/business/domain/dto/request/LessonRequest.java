package br.com.unitechdesafio.business.domain.dto.request;

import br.com.unitech.business.domain.entity.RegistrationEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class LessonRequest {

    private String id;

    private String title;

    private String description;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date date;

    private RegistrationEntity teacher;
}
