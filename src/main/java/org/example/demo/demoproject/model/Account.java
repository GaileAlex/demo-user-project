package org.example.demo.demoproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @JsonIgnore
    private Long id;

    private BigDecimal balance;

}
