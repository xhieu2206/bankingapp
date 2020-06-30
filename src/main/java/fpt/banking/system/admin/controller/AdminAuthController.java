package fpt.banking.system.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.model.Role;
import fpt.banking.system.payload.JwtAuthenticationResponse;
import fpt.banking.system.payload.LoginRequest;
import fpt.banking.system.repository.RoleRepository;
import fpt.banking.system.repository.UserRepository;
import fpt.banking.system.security.JwtTokenProvider;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    	try {
    		Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );
    		long id = 0;
			if (userService.findUser(loginRequest.getUsernameOrEmail(), "EMAIL") != null) {
				id = userService.findUser(loginRequest.getUsernameOrEmail(), "EMAIL").getId();
			} else {
				id = userService.findUser(loginRequest.getUsernameOrEmail(), "USERNAME").getId();
			}
			String role = "";
			for (Role r: userService.getUser(id).getRoles()) {
				role = r.getName();
			}
			if (role.equals("ROLE_USER")) {
				ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "You are not an Admin",
        				System.currentTimeMillis());
        		return new ResponseEntity<ErrorResponse>(error, HttpStatus.FORBIDDEN);
			}
    		SecurityContextHolder.getContext().setAuthentication(authentication);
            JwtAuthenticationResponse response = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(response);
    	} catch (LockedException e) {
    		String mess = "Your account had been locked, please contact admin to unlock your account";
    		ErrorResponse error = new ErrorResponse(HttpStatus.LOCKED.value(), mess,
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.LOCKED);
    	} catch (BadCredentialsException e) {
    		ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Wrong Username/Email or password",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.FORBIDDEN);
    	}
    }
}
