package fpt.banking.system.exception;

public class AccountIsNotActive extends RuntimeException {
	public AccountIsNotActive() {
	}

	public AccountIsNotActive(String message) {
		super(message);
	}

	public AccountIsNotActive(Throwable cause) {
		super(cause);
	}

	public AccountIsNotActive(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountIsNotActive(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
