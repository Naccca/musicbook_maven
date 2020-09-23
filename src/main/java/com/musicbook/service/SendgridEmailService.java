package com.musicbook.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.musicbook.entity.Artist;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class SendgridEmailService implements EmailService {

	@Override
	public void sendVerificationEmail(Artist artist) {
		
		Email from = new Email(System.getenv("MUSICBOOK_SENDGRID_FROM_EMAIL"));
		Email to = new Email(artist.getEmail());
		String subject = "Musicbook - Verification email";
		String url = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/artists/verify?token=" + artist.getToken();
		String contentString = "Click on link below to verify your Musicbook account:<br /><a href=\"" + url + "\">Click here</a>";
		Content content = new Content("text/html", contentString);
		
		Mail mail = new Mail(from, subject, to, content);
		SendGrid sg = new SendGrid(System.getenv("MUSICBOOK_SENDGRID_API_KEY"));
		
		try {
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getHeaders());
			System.out.println(response.getBody());
		}
		catch(IOException e) {
			System.out.println("Sendgrid error: " + e.getMessage());
		}
	}
}
