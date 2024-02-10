package org.example.demo.demoproject.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Getter
@Setter
public class UserRequest {

    private String name;

    private String email;

    private String phone;

    @JsonFormat(pattern = "dd:MM:yyyy")
    private LocalDate dateOfBirth;

    private Integer size;

    private Integer offset;

}
