package fpt.banking.system.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fpt.banking.system.exception.AppException;
import fpt.banking.system.model.Role;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.ApiResponse;
import fpt.banking.system.payload.JwtAuthenticationResponse;
import fpt.banking.system.payload.LoginRequest;
import fpt.banking.system.payload.SignUpRequest;
import fpt.banking.system.repository.RoleRepository;
import fpt.banking.system.repository.UserRepository;
import fpt.banking.system.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
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
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtAuthenticationResponse response = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    	if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
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
