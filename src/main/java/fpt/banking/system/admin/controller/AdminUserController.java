package fpt.banking.system.admin.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.BadRequestException;
import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.exception.UserNotFoundException;
import fpt.banking.system.model.Role;
import fpt.banking.system.model.User;
import fpt.banking.system.model.UserForAdmin;
import fpt.banking.system.payload.FindUserForTransferPayload;
import fpt.banking.system.payload.RegisterUserRequestPayload;
import fpt.banking.system.payload.SearchByIdCardNumberRequest;
import fpt.banking.system.payload.SearchByPhoneNumberRequest;
import fpt.banking.system.payload.UserIdRequestPayload;
import fpt.banking.system.payload.UsersResponse;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.AccountService;
import fpt.banking.system.service.CardService;
import fpt.banking.system.service.NotificationService;
import fpt.banking.system.service.UserService;
import fpt.banking.system.util.DateUtils;
import fpt.banking.system.util.EmailValidation;
import fpt.banking.system.util.SendEmail;

@RestController
@RequestMapping("/api")
public class AdminUserController {
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CardService cardService;

	@PostMapping("/admin/user/find")
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public UserForAdmin user(@RequestBody FindUserForTransferPayload payload, @AuthenticationPrincipal UserPrincipal user) {
		if (!payload.getType().equals("PHONENUMBER") && !payload.getType().equals("IDCARDNUMBER")) {
			throw new BadRequestException("Cannot find user with this type of data");
		}
		User result = userService.findUser(payload.getTerm(), payload.getType());
		if (result == null) {
			throw new UserNotFoundException("User Not Found");
		}
		String role = "";
		for (Role r: result.getRoles()) {
			role = r.getName();
		}
		if (!role.equals("ROLE_USER")) {
			throw new UserNotFoundException("User Not Found");
		}
		return new UserForAdmin(result.getId(), result.getEmail(), result.getUsername(), result.getFullname(), result.getBirthday(), result.getAddress(), result.getGender(), 
				result.getImage(), result.getIdCardNumber(), result.getPhone(), result.isLocked(), result.getMembership());
	}
	
	@PostMapping("/admin/user/register")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> createUser(
			@RequestBody RegisterUserRequestPayload payload) throws ParseException {
		if (userService.findUser(payload.getEmail().trim(), "EMAIL") != null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This email has already been used",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (userService.findUser(payload.getUsername().trim(), "USERNAME") != null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This username has already been used",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (userService.findUser(payload.getIdCardNumber().trim(), "IDCARDNUMBER") != null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This id card number has already been used",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (userService.findUser(payload.getPhone().trim(), "PHONENUMBER") != null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This phone number has already been used",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (payload.getIdCardNumber().trim().length() < 12) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Id card number is not corrected format",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		if (EmailValidation.isValidEmail(payload.getEmail().trim()) == false) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Email is not corrected format",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		Date theBirthday = DateUtils.parseDate(payload.getBirthday());
		long accountId = userService.saveUser(
				payload.getUsername().trim(), 
				payload.getEmail().trim(), 
				payload.getFullName().trim().toUpperCase(), 
				theBirthday, 
				payload.getAddress().trim(), 
				payload.getGender().trim(), 
				payload.getIdCardNumber().trim(), 
				payload.getPhone().trim(), 
				payload.getMembershipId(), 
				payload.getImage().trim());

		notificationService.saveNotification(
				"Ban da tao mot tai khoan thanh cong tai FPT Banking System",
				accountService.getAccount(accountId).getUser());

		try {
			SendEmail.sendEmail(
					accountService.getAccount(accountId).getUser().getEmail(),
					"Ban da tao mot tai khoan thanh cong tai FPT Banking System");
		} catch (IOException e) {
			System.out.println("Couldn't send email");
		}

		SuccessfulResponse res = new SuccessfulResponse(
				HttpStatus.OK.value(),
				"Create user successfully",
				System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
	@PostMapping("/admin/user/unlock")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> unlockUser(@RequestBody UserIdRequestPayload payload) {
		User user = userService.getUser(payload.getUserId());
		if (user == null) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
		}
		if (user.isLocked() == false) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This user is not locked",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		String role = "";
		for (Role r: user.getRoles()) {
			role = r.getName();
		}
		if (!role.equals("ROLE_USER")) {
			ErrorResponse error = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "This user is not an user",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
		}
		userService.unlockAnUser(user.getId());

		notificationService.saveNotification(
				"Tai khoan cua ban da duoc mo khoa thanh cong"
				, user);

		try {
			SendEmail.sendEmail(
					user.getEmail(),
					"Tai khoan cua ban da duoc mo khoa thanh cong");
		} catch (IOException e) {
			System.out.println("Couldn't send email");
		}

		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), "Unlock user successfully", System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
	
	@GetMapping("/admin/users")
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public UsersResponse getUsers(
			@AuthenticationPrincipal UserPrincipal admin,
			@RequestParam("page") Optional<Integer> page) {
		int pageNumber = 1;
		if (page.isPresent()) {
			pageNumber = page.get();
		}
		return userService.getUsersWithPage(pageNumber);
	}
}
