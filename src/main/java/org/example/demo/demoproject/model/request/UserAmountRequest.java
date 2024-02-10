package org.example.demo.demoproject.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Aleksei Gaile 09-Feb-24
 */
@Getter
@Setter
public class UserAmountRequest {

    private BigDecimal amount;

    private Long userId;

}
