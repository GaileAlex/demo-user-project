package org.example.demo.demoproject.model.request;

import lombok.Getter;
import lombok.Setter;
import org.example.demo.demoproject.model.enums.EnumSubject;

import javax.validation.constraints.NotBlank;

/**
 * @author Aleksei Gaile 09-Feb-24
 */
@Getter
@Setter
public class UserDataRequest {

    @NotBlank
    private String data;

    private EnumSubject subject;

}
