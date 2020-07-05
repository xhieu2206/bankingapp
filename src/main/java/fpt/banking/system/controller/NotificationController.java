package fpt.banking.system.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.payload.NotificationsResponse;
import fpt.banking.system.response.SuccessfulResponse;
import fpt.banking.system.security.UserPrincipal;
import fpt.banking.system.service.NotificationService;

@RestController
@RequestMapping("/api/users")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/current/notifications/totalUnread")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getTotalUnreadNotification(@AuthenticationPrincipal UserPrincipal user) {
		int totalUnread = notificationService.getTotalUnreadMessage(user.getId());
		SuccessfulResponse res = new SuccessfulResponse(HttpStatus.OK.value(), String.valueOf(totalUnread), System.currentTimeMillis());
		return new ResponseEntity<SuccessfulResponse>(res, HttpStatus.OK); 
	}
	
	@GetMapping("current/notifications")
	@PreAuthorize("hasRole('ROLE_USER')")
	public NotificationsResponse getNotificationsWithPage(@RequestParam("page") Optional<Integer> page, @AuthenticationPrincipal UserPrincipal user) {
		int pageNumber = 1;
		if (page.isPresent()) {
			pageNumber = page.get();
		}
		notificationService.markAsRead(user.getId());
		return notificationService.getNotificationsWithPage(user.getId(), pageNumber);
	}
}
