package org.example.demo.demoproject.securety.service;


import org.example.demo.demoproject.model.request.LoginRequest;
import org.example.demo.demoproject.model.response.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
public interface AuthenticationService {

    /**
     * Authenticates the user based on the login request.
     *
     * @param request the login request
     * @return the authentication response with a JWT token
     * @throws AuthenticationCredentialsNotFoundException if the user is not found
     */
     AuthenticationResponse authenticate(LoginRequest request);

}
