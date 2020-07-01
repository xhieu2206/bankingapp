package fpt.banking.system.controller;

import java.util.Collections;

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

import fpt.banking.system.exception.AppException;
import fpt.banking.system.exception.ErrorResponse;
import fpt.banking.system.exception.ErrorResponseWithLoggedInFailed;
import fpt.banking.system.model.Role;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.ApiResponse;
import fpt.banking.system.payload.JwtAuthenticationResponse;
import fpt.banking.system.payload.LoginRequest;
import fpt.banking.system.payload.SignUpRequest;
import fpt.banking.system.repository.RoleRepository;
import fpt.banking.system.repository.UserRepository;
import fpt.banking.system.security.JwtTokenProvider;
import fpt.banking.system.service.UserService;

@RestController
@RequestMapping("/api/user/auth")
public class AuthController {
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
			if (!role.equals("ROLE_USER")) {
				ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "You are not an User",
        				System.currentTimeMillis());
        		return new ResponseEntity<ErrorResponse>(error, HttpStatus.FORBIDDEN);
			}
    		SecurityContextHolder.getContext().setAuthentication(authentication);
            JwtAuthenticationResponse response = tokenProvider.generateToken(authentication);
            
			userService.resetAttemptedLoginFail(id);
            return ResponseEntity.ok(response);
    	} catch (LockedException e) {
    		String mess = "Your account had been locked, please contact admin to unlock your account";
    		ErrorResponse error = new ErrorResponse(HttpStatus.LOCKED.value(), mess,
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.LOCKED);
    	} catch (BadCredentialsException e) {
    		if (userService.findUser(loginRequest.getUsernameOrEmail(), "EMAIL") != null ||
    				userService.findUser(loginRequest.getUsernameOrEmail(), "USERNAME") != null) {
    			long id = 0;
    			if (userService.findUser(loginRequest.getUsernameOrEmail(), "EMAIL") != null) {
    				id = userService.findUser(loginRequest.getUsernameOrEmail(), "EMAIL").getId();
    			} else {
    				id = userService.findUser(loginRequest.getUsernameOrEmail(), "USERNAME").getId();
    			}
    			long attempedLoginFailed = userService.increaseAttemptedLoginFail(id);
    			String mess = "";
    			int time = 0;
    			if (attempedLoginFailed == 1) {
    				mess = userService.getUser(id).getUsername() + " have entered incorrected password 1 time, you have 2 times remaining before your account to be locked";
    				time = 1;
    			} else if (attempedLoginFailed == 2) {
    				mess = userService.getUser(id).getUsername() + " have entered incorrected password 2 times, you have 1 times remaining before your account to be locked";
    				time = 2;
    			} else if (attempedLoginFailed == 3) {
    				mess = userService.getUser(id).getUsername() + " have entered incorrected password 3 times, last tried before your account to be locked";
    				time = 3;
    			} else {
    				mess = userService.getUser(id).getUsername() + " haved entered wrong password 4 times, your account is lock now, please contact admin to unlock your account";
    				userService.lockAnUser(id);
    				ErrorResponse error = new ErrorResponse(HttpStatus.LOCKED.value(), mess,
            				System.currentTimeMillis());
            		return new ResponseEntity<ErrorResponse>(error, HttpStatus.LOCKED);
    			}
    			ErrorResponseWithLoggedInFailed error = new ErrorResponseWithLoggedInFailed(HttpStatus.FORBIDDEN.value(), mess,
        				System.currentTimeMillis(), time);
        		return new ResponseEntity<ErrorResponseWithLoggedInFailed>(error, HttpStatus.FORBIDDEN);
    		}
    		ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Wrong Username/Email or password",
    				System.currentTimeMillis());
    		return new ResponseEntity<ErrorResponse>(error, HttpStatus.FORBIDDEN);
    	}
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    	if (userService.findUser(signUpRequest.getUsername(), "USERNAME") != null) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if (userService.findUser(signUpRequest.getEmail(), "USERNAME") != null) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }
        
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setFullname(signUpRequest.getFullname());
        user.setEmail(signUpRequest.getEmail());
        user.setAddress(signUpRequest.getAddress());
        user.setPhone(signUpRequest.getPhone());
        user.setStatus(true);
        
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new AppException("User Role not set."));
        
        user.setRoles(Collections.singleton(userRole));
        
        User result = userRepository.save(user);
        ApiResponse<User> apiResponse = new ApiResponse<User>(true, "User registered successfully");
        apiResponse.setData(result);
        
        return new ResponseEntity(apiResponse, HttpStatus.CREATED);
    }
}
