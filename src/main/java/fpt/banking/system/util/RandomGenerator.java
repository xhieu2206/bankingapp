package fpt.banking.system.util;

public class RandomGenerator {

	public static String generateOTP() {
		int min = 100000;
		int max = 999999;
		int random_otp = (int) (Math.random() * (max - min + 1) + min);
		return String.valueOf(random_otp);
	}
}
