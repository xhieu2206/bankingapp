package fpt.banking.system.util;

public class RandomGenerator {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String generateOTP() {
		int min = 100000;
		int max = 999999;
		int randomOtp = (int) (Math.random() * (max - min + 1) + min);
		return String.valueOf(randomOtp);
	}
	
	public static String generatePinCode() {
		int min = 1000;
		int max = 9999;
		int randomOtp = (int) (Math.random() * (max - min + 1) + min);
		return String.valueOf(randomOtp);
	}

	public static String generateAccountNumber() {
		long min = 10000000;
		long max = 99999999;
		long randomAccountNumber = (long) (Math.random() * (max - min + 1) + min);
		return "4441" + String.valueOf(randomAccountNumber);
	}

	public static String generateCardNumber() {
		long min = 10000000;
		long max = 99999999;
		long randomAccountNumber = (long) (Math.random() * (max - min + 1) + min);
		return "5551" + String.valueOf(randomAccountNumber);
	}

	public static String generatePassword() {
		StringBuilder builder = new StringBuilder();
		int count = 8;
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
}
