package fpt.banking.system.exception;

public class AccountNotFound extends RuntimeException {
	public AccountNotFound() {
	}

	public AccountNotFound(String message) {
		super(message);
	}

	public AccountNotFound(Throwable cause) {
		super(cause);
	}

	public AccountNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountNotFound(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
