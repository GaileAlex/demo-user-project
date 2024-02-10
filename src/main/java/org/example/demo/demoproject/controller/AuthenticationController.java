package org.example.demo.demoproject.controller;

import org.example.demo.demoproject.model.request.LoginRequest;
import org.example.demo.demoproject.model.response.AuthenticationResponse;
import org.example.demo.demoproject.securety.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping( "/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
