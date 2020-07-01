package fpt.banking.system.exception;

public class WrongOTPCode extends RuntimeException {
	public WrongOTPCode() {
	}

	public WrongOTPCode(String message) {
		super(message);
	}

	public WrongOTPCode(Throwable cause) {
		super(cause);
	}

	public WrongOTPCode(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongOTPCode(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
