package com.rush.banking.userservice.service.impl;

import com.rush.banking.userservice.constants.Constants;
import com.rush.banking.userservice.service.MailSenderServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("MailSenderServiceFacade")
public class MailSenderServiceFacadeImpl implements MailSenderServiceFacade {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(String toEmail, String emailBody, String emailSubject) {

        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
        simpleMailMessage.setFrom(Constants.EMAIL_FROM);
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setText(emailBody);
        simpleMailMessage.setSubject(emailSubject);

        javaMailSender.send(simpleMailMessage);
    }
}
