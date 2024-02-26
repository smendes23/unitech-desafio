package br.com.unitechdesafio.infrastructure.event;

import br.com.unitech.business.domain.entity.UserDetailsEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class BackOfficeEvent extends ApplicationEvent {
    private UserDetailsEntity obj;

    public BackOfficeEvent(UserDetailsEntity obj){
        super(obj);
        this.obj = obj;
    }
}
