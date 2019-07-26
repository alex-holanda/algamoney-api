package com.algaworks.algamoney.api.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

//	TODO: CONFIGURAR EMAIL_USERNAME
	private static final String USERNAME = System.getenv("EMAIL_USERNAME");
	
//	TODO: CONFIGURAR EMAIL_PASSWORD
	private static final String PASSWORD = System.getenv("EMAIL_PASSWORD");
	
	@Bean
	public JavaMailSender javaMailSender() {
		Properties props = new Properties();
		
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.connectiontimeout", 10000);
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setJavaMailProperties(props);
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername(USERNAME);
		mailSender.setPassword(PASSWORD);
		
		return mailSender;
	}
	
}
