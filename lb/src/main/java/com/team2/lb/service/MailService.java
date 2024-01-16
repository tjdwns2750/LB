package com.team2.lb.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender javaMailSender;
	private static final String senderEmail = "tjdwns2755@gmail.com";
	private static int number;

	public static void createNumber() {
		number = (int) (Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
	}

	public MimeMessage CreateMail(String mail) {
		createNumber();
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			message.setFrom(senderEmail);
			message.setRecipients(MimeMessage.RecipientType.TO, mail);
			message.setSubject("이메일 인증");
			String body = "";
			body += "<div style=\"border: 3px solid black; padding: 50px; width: 600px;\">";
			body += "<div style=\"text-align: center; font-weight: bold;\">";
			body += "<div style=\"margin-bottom: 20px;\">";
			body += "<img src=\"https://cdn2.iconfinder.com/data/icons/marketing-and-seo-48/512/02_E-mail_Marketing-256.png\" style=\"width:100px;\">";
			body += "</div>";
			body += "<div style=\"font-size: 30px;\">";
			body += "<span>환영합니다! </span><span style=\"color:red;\">인증번호</span><span>를 입력해 주세요.</span>";
			body += "</div></br>";
			body += "<div style=\"font-size:20px;\">";
			body += "<p>이메일 인증을 위한 메일이 발송되었습니다.</p>";
			body += "<p>회원가입 완료를 위한 이메일 인증을 진행해주세요.</p></br>";
			body += "</div>";
			body += "<div style=\"font-size:25px;\">";
			body += "<span>인증번호 : </span>" + number;
			body += "</div>";
			body += "</div></div>";
			
			message.setText(body, "UTF-8", "html");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return message;
	}

	public int sendMail(String mail) {

		MimeMessage message = CreateMail(mail);

		javaMailSender.send(message);

		return number;
	}
}
