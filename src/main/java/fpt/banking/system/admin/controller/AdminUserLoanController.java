package fpt.banking.system.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.payload.LoanProfileRequest;
import fpt.banking.system.security.UserPrincipal;

@RestController
@RequestMapping("/api/admin")
public class AdminUserLoanController {

	@PostMapping("/users/{user_id}/loanprofile")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> addLoanProfile(@PathVariable long user_id, @RequestBody LoanProfileRequest payload, @AuthenticationPrincipal UserPrincipal user) {
		
		return null;
	}
}
