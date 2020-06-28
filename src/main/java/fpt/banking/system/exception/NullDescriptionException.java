package fpt.banking.system.exception;

public class NullDescriptionException extends RuntimeException {
	public NullDescriptionException() {
	}

	public NullDescriptionException(String message) {
		super(message);
	}

	public NullDescriptionException(Throwable cause) {
		super(cause);
	}

	public NullDescriptionException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullDescriptionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
