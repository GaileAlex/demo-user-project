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
public class UserDataForChangeRequest {

    @NotBlank
    private String oldData;

    @NotBlank
    private String newData;

    private EnumSubject subject;

}
