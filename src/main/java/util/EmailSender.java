/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.User;
import java.io.UnsupportedEncodingException;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class EmailSender {

    @Resource(name = "java/mail/kwetter")
    Session mailSession;

    public void sendEmail(User user, String registrationKey) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(mailSession);

        message.setSubject("Welcome to Kwetter");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail(), user.getUsername()));
        message.setText("Congratz with you new account! \nClick here to verify your email: "
                + "http://localhost:4200/verify/" + registrationKey);
        Transport.send(message);
    }
}
