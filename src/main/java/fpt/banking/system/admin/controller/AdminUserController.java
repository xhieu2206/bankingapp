package fpt.banking.system.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.BadRequestException;
import fpt.banking.system.exception.UserNotFoundException;
import fpt.banking.system.model.Role;
import fpt.banking.system.model.User;
import fpt.banking.system.model.UserForAdmin;
import fpt.banking.system.payload.FindUserForTranferPayload;
import fpt.banking.system.payload.SearchByIdCardNumberRequest;
import fpt.banking.system.payload.SearchByPhoneNumberRequest;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api")
public class AdminUserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/admin/user/find")
	@PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_TRANSACTIONMANAGER', 'ROLE_BRANCHMANAGER', 'ROLE_BANKMANAGER')")
	public UserForAdmin user(@RequestBody FindUserForTranferPayload payload, @AuthenticationPrincipal UserPrincipal user) {
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
		return new UserForAdmin(result.getId(), result.getFullname(), result.getBirthday(), result.getAddress(), result.getGender(), 
				result.getImage(), result.getIdCardNumber(), result.getPhone(), result.isLocked());
	}
	
}
