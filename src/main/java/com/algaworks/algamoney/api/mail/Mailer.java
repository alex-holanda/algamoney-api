package com.algaworks.algamoney.api.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Usuario;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	public void avisarSobreLancamentosVencidos(List<Lancamento> vencidos, List<Usuario> destinatarios) {
		Map<String, Object> variaveis = new HashMap<>();
		
		variaveis.put("lancamentos", vencidos);
		
		List<String> emails = destinatarios.stream().map(u -> u.getEmail()).collect(Collectors.toList());
		
		this.enviarEmail("servico.aplicativo@gmail.com",
				emails,
				"Lançamentos vencidos",
				"mail/aviso-lancamentos-vencidos",
				variaveis);
		
	}
		
	private void enviarEmail(String remetente, List<String> destinatarios, String assunto,
			String template, Map<String, Object> variaveis) {
		
		Context context = new Context(new Locale("pt", "BR"));
		
		variaveis.entrySet().forEach(e -> context.setVariable(e.getKey(), e.getValue()));
		
		String mensagem = thymeleaf.process(template, context);
		
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
	}
	
	private void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) {
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem, true);
			
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException("Problemas com o envio de e-mail!", e);
		}
	}
}

//	@Autowired
//	private LancamentoRepository repository;

//	TESTE DE ENVIO DE E-MAIL SIMPLES
//	@EventListener
//	public void teste(ApplicationReadyEvent event) {
//		this.enviarEmail("servico.aplicativo@gmail.com",
//				Arrays.asList("alex@perfilnet.com.br"),
//				"teste de envio de e-mail",
//				"Olá<br/> Teste ok.");
//		
//		System.out.println(">>> Terminado o envio de e-mail");
//	}


//	TESTE DE ENVIO DE E-MAIL COM TEMPLATE
//	@EventListener
//	public void teste(ApplicationReadyEvent event) {
//		String template = "mail/aviso-lancamentos-vencidos";
//		
//		List<Lancamento> lista = repository.findAll();
//		
//		Map<String, Object> variaveis = new HashMap<>();
//		
//		variaveis.put("lancamentos", lista);
//		
//		this.enviarEmail("servico.aplicativo@gmail.com",
//				Arrays.asList("alex@perfilnet.com.br"),
//				"teste de envio de e-mail",
//				template,
//				variaveis);
//		
//		System.out.println(">>> Terminado o envio de e-mail");
//	}