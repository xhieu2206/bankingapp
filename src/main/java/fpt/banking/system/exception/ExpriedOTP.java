package fpt.banking.system.exception;

public class ExpriedOTP extends RuntimeException {
	public ExpriedOTP() {
	}

	public ExpriedOTP(String message) {
		super(message);
	}

	public ExpriedOTP(Throwable cause) {
		super(cause);
	}

	public ExpriedOTP(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpriedOTP(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
