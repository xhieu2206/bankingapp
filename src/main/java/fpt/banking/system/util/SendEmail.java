package fpt.banking.system.util;

import java.io.IOException;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class SendEmail {

	public static void sendEmail(String emailTo, String contentEmail) throws IOException {
		Email from = new Email("xhieu94@gmail.com");
		Email to = new Email(emailTo);
		String subject = "FPT BANKING SYSTEM";
		Content content = new Content("text/plain", contentEmail);
		Mail mail = new Mail(from, subject, to, content);
		
		SendGrid sg = new SendGrid(StringUtils.reverseString("Qlcg3dnq6f1JWFSYe4ld1VBz_kpJBBb7d08qR4AG54Y.wxQi6i6b9BpmaRLQ92BzdZ.GS"));
		Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      throw ex;
	    }
	}
}
