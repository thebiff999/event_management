package de.fhms.sweng.event_management.security;


import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.repositories.BusinessUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private BusinessUserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(BusinessUserRepository userRepository) {
        this.userRepository = userRepository;
    };


    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<BusinessUser> optUserEntity = userRepository.findByMail(username);
        if (optUserEntity.isPresent()) {
            BusinessUser userEntity = optUserEntity.get();
            return org.springframework.security.core.userdetails.User.withUsername(
                    userEntity.getMail())
                    .authorities(userEntity.getRole())
                    .password("***")
                    .build();
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }
}

