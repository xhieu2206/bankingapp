package fpt.banking.system;

import java.io.IOException;
import java.util.Properties;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class EmailTesting {

	public static void main(String[] args) throws IOException {
		Email from = new Email("xhieu94@gmail.com");
		Email to = new Email("xhieu94@gmail.com");
		String subject = "Testing from main app";
		Content content = new Content("text/plain", "Test");
		Mail mail = new Mail(from, subject, to, content);
		
		SendGrid sg = new SendGrid("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");
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
