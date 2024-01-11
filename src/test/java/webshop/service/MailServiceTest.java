package webshop.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.util.MailService;
import webshop.common.model.Email;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class MailServiceTest {
    private GreenMail greenMail;
    private MailService mailService;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(3025);

    }

    //TODO
    @Test
    void sendSimpleEmail() throws MessagingException {
        String subject = "subject";
        Email email = new Email("test@example.com");
        String content = "content";

        mailService.sendSimpleEmail(subject,email,content);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1,receivedMessages.length);
        assertEquals(subject, receivedMessages[0].getSubject());
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }
}
