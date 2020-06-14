package de.fhms.sweng.event_management.security;


import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.repositories.BusinessUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    private BusinessUserRepository userRepository;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public JwtUserDetailsService(BusinessUserRepository userRepository) {
        this.userRepository = userRepository;
    };


    @Override
    public UserDetails loadUserByUsername(String username) {
        LOGGER.debug("loading user by username {}", username);
        Optional<BusinessUser> optionalUser = userRepository.findByMail(username);
        if (optionalUser.isPresent()) {
            LOGGER.debug("user {} is present in database", username);
            BusinessUser user = optionalUser.get();
            return org.springframework.security.core.userdetails.User.withUsername(
                    user.getMail())
                    .authorities(user.getRole())
                    .password("***")
                    .build();
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }
}

