package io.blueharvest.ams.domain.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddSecondaryCurrentAccount {

    private int initialCredit;
    private String customerId;

    public AddSecondaryCurrentAccount() {
    }

    @JsonCreator
    public AddSecondaryCurrentAccount(@JsonProperty(value = "initialCredit", required = true) int initialCredit,
                                      @JsonProperty(value = "customerId", required = true) String customerId) {
        this.initialCredit = initialCredit;
        this.customerId = customerId;
    }

    public int getInitialCredit() {
        return initialCredit;
    }

    public void setInitialCredit(int initialCredit) {
        this.initialCredit = initialCredit;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
