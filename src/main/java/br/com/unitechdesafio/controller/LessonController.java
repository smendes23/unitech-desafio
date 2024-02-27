package br.com.unitechdesafio.controller;

import br.com.unitechdesafio.business.domain.dto.request.LessonRequest;
import br.com.unitechdesafio.business.domain.entity.LessonEntity;
import br.com.unitechdesafio.business.resource.LessonResource;
import br.com.unitechdesafio.business.service.LessonService;
import br.com.unitechdesafio.infrastructure.api.BackOfficeController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lesson")
public class LessonController extends BackOfficeController<LessonEntity, LessonRequest> implements LessonResource {

    public LessonController(LessonService service){
        super(service);
    }

}
