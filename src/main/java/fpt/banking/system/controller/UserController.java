package fpt.banking.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.exception.UserNotFoundException;
import fpt.banking.system.model.Role;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.FindUserForTransferPayload;
import fpt.banking.system.payload.PasswordRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/find")
	@PreAuthorize("hasRole('ROLE_USER')")
	public User find(@RequestBody FindUserForTransferPayload
			findUserForTransferPayload) {
		if (userService.findUser(findUserForTransferPayload.getTerm(),
				findUserForTransferPayload.getType()) == null) {
			throw new UserNotFoundException("User Not Found");
		}
		return userService.findUser(findUserForTransferPayload.getTerm(),
				findUserForTransferPayload.getType());
	}

	@GetMapping("/test")
	@PreAuthorize("hasRole('ROLE_USER')")
    public String getMessage() {
        return "Hello from USER API controller";
    }
	
	@GetMapping("/current")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public UserPrincipal getCurrentUserLoggedIn(@AuthenticationPrincipal UserPrincipal user) {
		return user;
	}
	
	@GetMapping("/current/role")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public String getCurrentUserLoggedInRole(@AuthenticationPrincipal UserPrincipal user) {
		String role = "";
		for (Role r: userService.getUser(user.getId()).getRoles()) {
			role = r.getName();
		}
		return role;
	}
	
	@PostMapping("/current/change-password")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public ResponseEntity<?> changePassword(
			@AuthenticationPrincipal UserPrincipal user,
			@RequestBody PasswordRequestPayload payload) {
		if (payload.getPassword().length() < 8 || payload.getPasswordConfirm().length() < 8) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Your password must longer than 8 characters",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (!payload.getPassword().equals(payload.getPasswordConfirm())) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Your password and your confirm password are not matched",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		userService.changePassword(user.getId(), payload.getPassword());
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Change password successfully", System.currentTimeMillis());
		notificationService.saveNotification(
				"You have change your password"
				, userService.getUser(user.getId()));
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
