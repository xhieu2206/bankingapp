package fpt.banking.system.exception;

public class PinCodeIncorrectedException extends RuntimeException {
	public PinCodeIncorrectedException() {
	}

	public PinCodeIncorrectedException(String message) {
		super(message);
	}

	public PinCodeIncorrectedException(Throwable cause) {
		super(cause);
	}

	public PinCodeIncorrectedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PinCodeIncorrectedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
