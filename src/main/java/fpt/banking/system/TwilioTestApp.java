package fpt.banking.system;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioTestApp {
	public static final String ACCOUNT_SID = "ACf01820788e9e914d9166bd06ebd7b5e4";
	public static final String AUTH_TOKEN = "1af35dda1ec36db7f372cb5cd71b6ae3";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		Message message = Message.creator(new PhoneNumber("+84963558935"), new PhoneNumber("+12513579376"),
				"ABC").create();

		System.out.println(message.getSid());
	}

}
