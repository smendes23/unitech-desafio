package br.com.unitechdesafio.business.resource;


import br.com.unitechdesafio.business.domain.dto.request.LoginRequest;
import br.com.unitechdesafio.infrastructure.resource.BackOfficeResource;

public interface AuthenticationResource extends BackOfficeResource<LoginRequest> {
}
