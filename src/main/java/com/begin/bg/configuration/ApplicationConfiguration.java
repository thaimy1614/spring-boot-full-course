package com.begin.bg.configuration;

import com.begin.bg.enums.UserRole;
import com.begin.bg.enums.UserStatus;
import com.begin.bg.models.User;
import com.begin.bg.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@RequiredArgsConstructor
@Configuration
public class ApplicationConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(UserRole.ADMIN.name());
                User user = User
                        .builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123"))
                        .role(roles)
                        .status(UserStatus.ACTIVATED.name())
                        .build();
                userRepository.save(user);
            }

        };
    }
}
