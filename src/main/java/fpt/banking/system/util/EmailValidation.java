package fpt.banking.system.util;

import org.apache.commons.validator.routines.EmailValidator;

public class EmailValidation {
	public static boolean isValidEmail(String email) {
       // create the EmailValidator instance
       EmailValidator validator = EmailValidator.getInstance();

       // check for valid email addresses using isValid method
       return validator.isValid(email);
   }
}
