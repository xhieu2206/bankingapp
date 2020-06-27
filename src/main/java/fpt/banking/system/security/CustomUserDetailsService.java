package fpt.banking.system.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fpt.banking.system.dao.UserDAO;
import fpt.banking.system.exception.ResourceNotFoundException;
import fpt.banking.system.model.User;
import fpt.banking.system.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
    UserRepository userRepository;
	
	@Autowired
	UserDAO userDAO;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
        );
        
        user.setMembership(userDAO.getUser(user.getId().intValue()).getMembership());
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        user.setMembership(userDAO.getUser(user.getId().intValue()).getMembership());
        return UserPrincipal.create(user);
    }
}
