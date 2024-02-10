package org.example.demo.demoproject.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Getter
@Setter
public class LoginRequest {

    private String email;

    private String phone;

    @NotBlank(message = "Password cannot be empty")
    private String password;

}
