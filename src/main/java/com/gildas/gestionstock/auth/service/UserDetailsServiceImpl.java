package com.gildas.gestionstock.auth.service;


import com.gildas.gestionstock.auth.entity.User;
import com.gildas.gestionstock.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {

            throw new UsernameNotFoundException("Utilisateur inexistant");
        } else {
            return UserDetailsImpl.build(user);
        }


    }

}
