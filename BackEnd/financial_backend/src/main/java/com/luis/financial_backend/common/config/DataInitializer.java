package com.luis.financial_backend.common.config;


import com.luis.financial_backend.modules.auth.entity.Role;
import com.luis.financial_backend.modules.auth.entity.User;
import com.luis.financial_backend.modules.auth.repository.RoleRepository;
import com.luis.financial_backend.modules.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository){

        return args -> {
            List<String> required = List.of("ROLE_USER", "ROLE_ADMIN");

            List<String> missing = required.stream()
                    .filter(name -> !roleRepository.existsByName(name))
                    .collect(Collectors.toList());

            for(String name : missing){
                try {
                    roleRepository.save(Role.builder().name(name).build());
                    log.info("Role created: {}", name);
                }
                catch (DataIntegrityViolationException e){
                    log.warn("Role already exists (concurrent): {}", name);
                }
            }

        };
    }


    @Bean
    CommandLineRunner createInitAdmin
            (
                    RoleRepository roleRepository,
                    UserRepository userRepository,
                    PasswordEncoder passwordEncoder
            ){
        return args -> {
            String adminEmail = System.getenv("INITIAL_ADMIN_EMAIL");
            String adminPassword = System.getenv("INITIAL_ADMIN_PASSWORD");
            String adminUsername = System.getenv("INITIAL_ADMIN_USERNAME");

            if(adminEmail == null || adminPassword == null){
                return;
            }

            String email = adminEmail.trim().toLowerCase();
            String username = adminUsername != null ? adminUsername.trim() : "admin";

            if(userRepository.existsByEmail(email)){
                log.info("Admin user already exists with email {}", email);
                return;
            }

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(()-> new IllegalStateException("ROLE_ADMIN not found"));

            User admin = User.builder()
                    .username(username)
                    .email(email)
                    .password(passwordEncoder.encode(adminPassword))
                    .roles(Set.of(adminRole))
                    .build();

            userRepository.save(admin);
            log.info("Initial admin created {}", email);
        };
    }
}
