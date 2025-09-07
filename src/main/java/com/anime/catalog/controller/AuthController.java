package com.anime.catalog.controller;

import com.anime.catalog.dto.LoginRequest;
import com.anime.catalog.dto.SignupRequest;
import com.anime.catalog.dto.JwtResponse;
import com.anime.catalog.model.User;
import com.anime.catalog.repository.RoleRepository;
import com.anime.catalog.service.UserService;
import com.anime.catalog.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;
   

    // ✅ SIGNUP
  @PostMapping("/signup")
public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
    userService.registerUser(signupRequest);  // ✅ service handles everything
    return ResponseEntity.ok("User registered successfully!");
}



    // ✅ LOGIN (JWT)
    @PostMapping("/login")
public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    User user = userService.findByEmail(loginRequest.getEmail());
    String role = user.getRoles().stream()
            .findFirst()
            .map(r -> r.getName().replace("ROLE_", "")) // -> "ADMIN" or "USER"
            .orElse("USER");

    // ✅ pass email + role into token
    String jwt = jwtUtils.generateToken(user.getEmail(), role);

    return ResponseEntity.ok(new JwtResponse(jwt, user.getEmail(), role));
}

}