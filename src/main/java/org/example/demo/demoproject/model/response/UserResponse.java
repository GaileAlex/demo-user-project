package org.example.demo.demoproject.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.demoproject.model.User;

import java.util.List;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

    private List<User> users;

    private Integer pages;

}
