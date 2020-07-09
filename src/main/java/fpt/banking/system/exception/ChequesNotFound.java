package fpt.banking.system.exception;

public class ChequesNotFound extends RuntimeException {

	public ChequesNotFound() {
	}

	public ChequesNotFound(String message) {
		super(message);
	}

	public ChequesNotFound(Throwable cause) {
		super(cause);
	}

	public ChequesNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	public ChequesNotFound(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
