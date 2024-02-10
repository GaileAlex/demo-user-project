package org.example.demo.demoproject.securety.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.demoproject.entity.UserEntity;
import org.example.demo.demoproject.repository.UserRepository;
import org.example.demo.demoproject.securety.config.JwtService;
import org.example.demo.demoproject.model.request.LoginRequest;
import org.example.demo.demoproject.model.response.AuthenticationResponse;
import org.example.demo.demoproject.securety.model.UserDetail;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplementation implements AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(LoginRequest request) {
        UserEntity user;
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword()));
            user = repository.findUserByPhoneOrEmail(request.getPhone())
                    .orElseThrow();
        } else if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            user = repository.findUserByPhoneOrEmail(request.getEmail())
                    .orElseThrow();
        } else {
            throw new AuthenticationCredentialsNotFoundException("user not found");
        }

        var userDetail = UserDetail.builder()
                .id(user.getId())
                .username(user.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        var jwtToken = jwtService.generateToken(userDetail);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
