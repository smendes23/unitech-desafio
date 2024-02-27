package br.com.unitechdesafio.controller;

import br.com.unitechdesafio.business.domain.dto.request.LoginRequest;
import br.com.unitechdesafio.business.domain.entity.JwtEntity;
import br.com.unitechdesafio.business.resource.AuthenticationResource;
import br.com.unitechdesafio.business.service.AuthenticationService;
import br.com.unitechdesafio.infrastructure.api.BackOfficeController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class AuthenticationController extends BackOfficeController<JwtEntity, LoginRequest> implements AuthenticationResource {
    public AuthenticationController(AuthenticationService service) {
        super(service);
    }
}
