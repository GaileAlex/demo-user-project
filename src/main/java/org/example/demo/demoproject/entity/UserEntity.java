package org.example.demo.demoproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "user", schema = "public")
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dateOfBirth;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private AccountEntity account;

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<EmailDataEntity> emailDataList = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<PhoneDataEntity> phoneDataList = new ArrayList<>();

}
