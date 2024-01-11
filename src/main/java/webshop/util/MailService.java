package webshop.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import webshop.common.model.Email;

public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleEmail(String subject, Email email, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setTo(email.getValue());
        message.setText(content);

        javaMailSender.send(message);
    }
}
