package fpt.banking.system.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SendSms {
	public static final String ACCOUNT_SID = "7b03369937628a62fb312fb6c4ba7178CA";
	public static final String AUTH_TOKEN = "d48bf56d3ef03573826d8813c4b84edd";

	public static void sendSms(String phone, String smsMessage) throws com.twilio.exception.ApiException {
		Twilio.init(StringUtils.reverseString(ACCOUNT_SID), StringUtils.reverseString(AUTH_TOKEN));
		
		Message message = Message.creator(new PhoneNumber(phone), new PhoneNumber("+12056289465"),
				smsMessage).create();

		System.out.println(message.getSid());
	}
}
