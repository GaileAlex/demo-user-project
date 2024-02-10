package org.example.demo.demoproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Getter
@Setter
public class DataWrapper {

    @JsonProperty("data")
    private List<Account> data = new ArrayList<>();

}
