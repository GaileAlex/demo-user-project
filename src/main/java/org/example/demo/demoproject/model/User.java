package org.example.demo.demoproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Getter
@Setter
@NoArgsConstructor
public class User {

    @JsonIgnore
    private Long id;

    private String name;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;

    @JsonIgnore
    private String password;

    private Account account;

    private List<EmailData> emailDataList;

    private List<PhoneData> phoneDataList;

}
