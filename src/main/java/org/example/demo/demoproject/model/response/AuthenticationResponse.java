package org.example.demo.demoproject.model.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Getter
@Builder
public class AuthenticationResponse {

    private String token;

}
