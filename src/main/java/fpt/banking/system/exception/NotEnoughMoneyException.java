package fpt.banking.system.exception;

public class NotEnoughMoneyException extends RuntimeException {
	public NotEnoughMoneyException() {
	}

	public NotEnoughMoneyException(String message) {
		super(message);
	}

	public NotEnoughMoneyException(Throwable cause) {
		super(cause);
	}

	public NotEnoughMoneyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughMoneyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
