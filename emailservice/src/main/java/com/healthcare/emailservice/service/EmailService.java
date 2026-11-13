package com.healthcare.emailservice.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.healthcare.emailservice.dto.EmailDetails;


@Service
public class EmailService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    public void sendEmail(EmailDetails emailDetails){
        try {
            SimpleMailMessage mailMsg = new SimpleMailMessage();
            mailMsg.setFrom(emailSender);
            mailMsg.setTo(emailDetails.getRecipient());
            mailMsg.setText(emailDetails.getMsgBody());
            mailMsg.setSubject(emailDetails.getSubject());
            mailMsg.setSentDate(new Date());
            javaMailSender.send(mailMsg);
            logger.info("Mail has been sent successfully");
        }catch (MailException exception){
        	logger.error("Failure occurred while sending email");
        }
    }
}
