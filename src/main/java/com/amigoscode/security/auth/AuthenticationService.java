package com.amigoscode.security.auth;

import com.amigoscode.security.auth.request.AuthenticationRequest;
import com.amigoscode.security.auth.request.RegisterRequest;
import com.amigoscode.security.auth.response.AuthenticationResponse;
import com.amigoscode.security.config.JwtService;
import com.amigoscode.security.user.Role;
import com.amigoscode.security.user.User;
import com.amigoscode.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        //to domain
        var user = User.builder()
                .firstName(request.firstname())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        //from domain
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // set/update security context
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findByEmail(request.email())
                .orElseThrow();


        var jwtToken = jwtService.generateToken(user);

        //from domain
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
