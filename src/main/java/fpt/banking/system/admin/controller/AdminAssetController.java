package fpt.banking.system.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.model.Asset;
import fpt.banking.system.model.LoanProfile;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.AssetIdRequestPayload;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.LoanService;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api")
public class AdminAssetController {

	@Autowired
	private LoanService loanService;

	@Autowired
	private UserService userService;

	@PostMapping("/admin/loan-profile/assets")
	@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	public ResponseEntity<?> deleteAsset(
			@AuthenticationPrincipal UserPrincipal currentEmployee,
			@RequestBody AssetIdRequestPayload payload) {
		Asset asset = loanService.findAssetById(payload.getAssetId());
		User employee = userService.getUser(currentEmployee.getId());

		if (asset == null) {
			ErrorResponse res = new ErrorResponse(
					HttpStatus.NOT_FOUND.value(),
					"Asset not found",
    				System.currentTimeMillis());
			return new ResponseEntity<ErrorResponse>(res, HttpStatus.NOT_FOUND);
		}

		LoanProfile loanProfile = asset.getLoanProfile();
		// check if this loan profile was under management of this transaction office
		if (loanProfile.getTransactionOffice().getId() != employee.getTransactionOffice().getId()) {
			ErrorResponse res = new ErrorResponse(
					HttpStatus.UNAUTHORIZED.value(),
					"This loan profile doesn't belong to your manage of your office",
    				System.currentTimeMillis());
			return new ResponseEntity<ErrorResponse>(res, HttpStatus.UNAUTHORIZED);
		}

		if (!loanProfile.getStatus().equals("1")) {
			ErrorResponse res = new ErrorResponse(
					HttpStatus.NOT_ACCEPTABLE.value(),
					"This loan profile doesn't need to be confirmed",
					System.currentTimeMillis());
			return new ResponseEntity<ErrorResponse>(res, HttpStatus.NOT_ACCEPTABLE);
		}

		loanService.removeAsset(asset.getId());
		SuccessfulResponse res = new SuccessfulResponse(
				HttpStatus.OK.value(),
				"Remove asset successfully",
				System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK);
	}
}
