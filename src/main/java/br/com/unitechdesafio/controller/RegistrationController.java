package br.com.unitechdesafio.controller;

import br.com.unitechdesafio.business.domain.dto.request.RegistrationRequest;
import br.com.unitechdesafio.business.domain.entity.RegistrationEntity;
import br.com.unitechdesafio.business.resource.RegistrationResource;
import br.com.unitechdesafio.business.service.RegistrationService;
import br.com.unitechdesafio.infrastructure.api.BackOfficeController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("registration")
public class RegistrationController extends BackOfficeController<RegistrationEntity, RegistrationRequest>
        implements RegistrationResource {

    public RegistrationController(RegistrationService service){
        super(service);
    }
}
