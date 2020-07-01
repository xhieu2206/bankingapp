package fpt.banking.system.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSms {
	public static final String ACCOUNT_SID = "ACf01820788e9e914d9166bd06ebd7b5e4";
	public static final String AUTH_TOKEN = "1af35dda1ec36db7f372cb5cd71b6ae3";
	
	public static void sendSms(String phone, String smsMessage) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		Message message = Message.creator(new PhoneNumber(phone), new PhoneNumber("+12513579376"),
				smsMessage).create();

		System.out.println(message.getSid());
	}
}
