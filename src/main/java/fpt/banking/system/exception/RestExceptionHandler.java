package fpt.banking.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import fpt.banking.system.exception.ErrorResponse;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(UserNotFoundException exc) {

		// create CustomerErrorResponse

		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(),
				System.currentTimeMillis());

		// return ResponseEntity

		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(AuthorizedException exc) {

		// create CustomerErrorResponse

		ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), exc.getMessage(),
				System.currentTimeMillis());

		// return ResponseEntity

		return new ResponseEntity<ErrorResponse>(error, HttpStatus.FORBIDDEN);
	}

	// Add another exception handler ... to catch any exception (catch all)

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception exc) {

		// create CustomerErrorResponse

		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), 
				"Bad request",
				System.currentTimeMillis());

		// return ResponseEntity

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
