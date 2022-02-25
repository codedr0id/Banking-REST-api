package paytm.assignment.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import paytm.assignment.Models.MyUserDetails;
import paytm.assignment.Models.User;
import paytm.assignment.Repositories.UserRepository;

import java.util.Optional;

@Component
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    // Get Logger for the User
    private static final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("CustomUserDetailsService : Verifying User : " + username);

        Optional<User> user = userRepo.findById(username);
        if (user != null) {
            logger.debug("CustomUserDetailsService : User is present in Database");
            UserDetails userDetails = new MyUserDetails(user);
            return userDetails;
        } else {
            logger.error("CustomUserDetailsService : User not found");
            throw new UsernameNotFoundException("User not found");
        }
    }
}
