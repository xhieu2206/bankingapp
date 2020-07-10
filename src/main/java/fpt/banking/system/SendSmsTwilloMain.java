package fpt.banking.system;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSmsTwilloMain {
	public static final String ACCOUNT_SID = "AC8717ab4c6bf213bf26a82673996330b7";
	public static final String AUTH_TOKEN = "dde48b4c3188d62837530fe3d65fb84d";

	public static void main(String[] args) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		try {
			Message message = Message.creator(new PhoneNumber("+84936230865"), new PhoneNumber("+12056289465"),
					"Test").create();

			System.out.println(message.getSid());
		} catch (com.twilio.exception.ApiException e) {
			System.out.println("ABC");
			e.printStackTrace();
		}
		System.out.println("S");
	}

}
