package webshop.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.common.model.Email;
import webshop.order.command.domain.OrderState;
import webshop.util.MailService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static webshop.order.command.domain.QOrder.order;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class MailServiceTest {

    private MailService mailService;
    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp(){
        mailService = new MailService(javaMailSender);
    }

    @Test
    void sendSimpleEmail() {
        //Given
        String subject = "subject";
        Email email = new Email("test@example.com");
        String content = "content";

        //When
        mailService.sendSimpleEmail(subject,email,content);

        //Then
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));

    }

}
