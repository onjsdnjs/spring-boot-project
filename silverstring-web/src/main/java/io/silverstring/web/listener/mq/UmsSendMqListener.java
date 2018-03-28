package io.silverstring.web.listener.mq;


import java.io.UnsupportedEncodingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.silverstring.core.service.UmsService;
import io.silverstring.domain.hibernate.EmailConfirm;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UmsSendMqListener {

    private final UmsService umsService;

    @Autowired
    public UmsSendMqListener(UmsService umsService) {
        this.umsService = umsService;
    }

    @RabbitListener(queues = "email_confirm")
    public void onEmailConfirmMessage(final EmailConfirm emailConfirm) throws UnsupportedEncodingException, CannotSendEmailException {
        try {
            log.error("* email_confirm::onMessage : {}", emailConfirm);
            umsService.emailConfirmEmailSend(emailConfirm);
        } catch (Exception ex) {
            log.error("onEmailConfirmMessage error : {}", ex.getMessage());
        }
    }
}
