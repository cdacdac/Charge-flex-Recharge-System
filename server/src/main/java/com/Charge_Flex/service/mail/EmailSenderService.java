package com.Charge_Flex.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String toEmail,String subject,String body) {
			
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("teamflexipay@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		
		 try {
	            mailSender.send(message);
	            System.out.println("Email sent successfully");
	        } catch (MailSendException e) {
	            System.out.println("Failed to send email: " + e.getMessage());
	        } catch (Exception e) {
	            System.out.println("An unexpected error occurred: " + e.getMessage());
	        }
	}
}
