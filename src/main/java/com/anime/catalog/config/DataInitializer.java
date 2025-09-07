package com.anime.catalog.config;

import com.anime.catalog.model.Role;
import com.anime.catalog.model.User;
import com.anime.catalog.repository.RoleRepository;
import com.anime.catalog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.user}")
    private String adminUser;
    @Value("${admin.email}")
    private String adminEmail;
    @Override
    public void run(String... args) {
        // ✅ Ensure ROLE_USER exists
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

        // ✅ Ensure ROLE_ADMIN exists
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

        // ✅ Ensure default admin user exists
        userRepository.findByUsername(adminUser).orElseGet(() -> {
            User admin = new User();
            admin.setUsername(adminUser);
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword)); // encoded password
            admin.setRoles(Set.of(adminRole)); // assign ADMIN role
            return userRepository.save(admin);
        });

        // ✅ Ensure all existing users have at least ROLE_USER
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u.getRoles() == null || u.getRoles().isEmpty()) {
                u.setRoles(Set.of(userRole));
                userRepository.save(u);
                System.out.println("🔹 Assigned ROLE_USER to: " + u.getUsername());
            }
        }
    }
}
