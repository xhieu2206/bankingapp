package fpt.banking.system.util;

public class MobilePhoneUtil {
	public static String convertPhone(String phone, String countryCodePhoneNumber) {
		
		return phone.replaceFirst("0", countryCodePhoneNumber);
	}
}
