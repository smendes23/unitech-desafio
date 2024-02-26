package br.com.unitechdesafio.business.domain.entity;


import br.com.unitechdesafio.infrastructure.model.BackOfficeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Document("LESSON")
public class LessonEntity implements BackOfficeEntity {

	@Id
	private String id;

	private String title;
	
	private String description;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	private Date date;

	@DocumentReference(lazy=true)
	private RegistrationEntity teacher;
}
