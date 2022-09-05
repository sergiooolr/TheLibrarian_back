package com.thelibrarian.integration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelibrarian.integration.utilities.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class EmailService {

   @Autowired
   private JavaMailSender emailSender;

   private ObjectMapper objectMapper = new ObjectMapper();
  public void sendEmail(String userName, String userEmail) throws IOException {
      Mail mail = objectMapper.readValue(new File("src/main/resources/emailData.json"), Mail.class);
       mail.setTo(userEmail);
     mail.setContent("Hola, " + userName + "!\n\n" + mail.getContent());

      SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mail.getSubject());
       message.setFrom(mail.getFrom());
       message.setTo(mail.getTo());
       message.setText(mail.getContent());

      emailSender.send(message);

    }

}
