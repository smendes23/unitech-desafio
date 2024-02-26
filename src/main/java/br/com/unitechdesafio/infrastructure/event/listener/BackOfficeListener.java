package br.com.unitechdesafio.infrastructure.event.listener;

import br.com.unitechdesafio.business.domain.entity.UserDetailsEntity;
import br.com.unitechdesafio.infrastructure.event.BackOfficeEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class BackOfficeListener implements ApplicationListener<BackOfficeEvent> {

    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(BackOfficeEvent event) {
        try {
            sendVerificationEmail(event.getObj());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVerificationEmail(UserDetailsEntity entity) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email  de Verificacao";
        String senderName = "Recebemos o seu cadastro em nosso  Portal de Serviço";
        String mailContent = "<p> Ola professor, "+ entity.getName()+ ", </p>"+
                "<p>Agradeemos pelo seu registro de interesse em nosso portal!"+
                "Segue suas credencias para acesso</p>"+
                "<p>usuario:"+entity.getUsername()+ "</p>"+
                "<p>senha:"+entity.getPassword()+ "</p>"+
                "<p> Atenciosamente <br> Equipe Unitech";
        var messageHelper = new MimeMessageHelper(mailSender.createMimeMessage());
        messageHelper.setFrom("name@mail.com", senderName);
        messageHelper.setTo(entity.getUsername());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(mailSender.createMimeMessage());
    }
}
