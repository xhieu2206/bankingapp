package fpt.banking.system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER')")
    public String getMessage() {
        return "Hello from Admin API controller";
    }
}
