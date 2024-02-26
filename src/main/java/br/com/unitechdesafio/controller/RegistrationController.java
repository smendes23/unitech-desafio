package br.com.unitechdesafio.controller;

import br.com.unitech.business.domain.dto.request.RegistrationRequest;
import br.com.unitech.business.domain.entity.RegistrationEntity;
import br.com.unitech.business.resource.RegistrationResource;
import br.com.unitech.business.service.RegistrationService;
import br.com.unitech.infrastructure.api.BackOfficeController;
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
