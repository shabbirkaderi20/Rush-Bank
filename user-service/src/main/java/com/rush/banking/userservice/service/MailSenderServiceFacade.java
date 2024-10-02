package com.rush.banking.userservice.service;

public interface MailSenderServiceFacade {

    public void sendMail(String toEmail, String emailBody, String emailSubject);
}
