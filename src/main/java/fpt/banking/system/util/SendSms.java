package fpt.banking.system.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSms {
	public static final String ACCOUNT_SID = "AC8717ab4c6bf213bf26a82673996330b7";
	public static final String AUTH_TOKEN = "f5b3f8e6a4506c0a90bfb6feb60801d7";

	public static void sendSms(String phone, String smsMessage) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		Message message = Message.creator(new PhoneNumber(phone), new PhoneNumber("+12056289465"),
				smsMessage).create();

		System.out.println(message.getSid());
	}
}
