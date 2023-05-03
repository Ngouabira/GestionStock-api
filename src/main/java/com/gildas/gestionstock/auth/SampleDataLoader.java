package com.gildas.gestionstock.auth;

import com.gildas.gestionstock.auth.entity.ERole;
import com.gildas.gestionstock.auth.entity.User;
import com.gildas.gestionstock.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SampleDataLoader implements CommandLineRunner {

    UserRepository userRepository;
    PasswordEncoder encoder;


    @Override
    public void run(String... args) throws Exception {

/*
        User user1 = new User("NGOUABIRA", "Valdy", ERole.ROLE_ADMIN.toString(), "valdyngouabira@gmail.com", "778732815", "Masculin", encoder.encode("1234"));
        User user2 = new User("NKOUENE", "Irène",ERole.ROLE_USER.toString(), "irenenkouene@gmail.com", "778732814", "Feminin", encoder.encode("1234"));
        User user3 = new User("OYEWE", "Shekina",ERole.ROLE_USER.toString(), "oyewe@gmail.com", "778732813", "Feminin", encoder.encode("1234"));
        User user4 = new User("NGOUABIRA", "Gildas",ERole.ROLE_USER.toString(), "gildasngouabira@gmail.com", "778732812", "Masculin", encoder.encode("1234"));


        List<User> users = List.of(user1, user2, user3, user4);

        userRepository.saveAll(users);
*/

    }

}
