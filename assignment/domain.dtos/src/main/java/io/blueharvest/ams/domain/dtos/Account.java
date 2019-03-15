package io.blueharvest.ams.domain.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account extends Entity {

    private boolean current;
    private boolean secondary;
    private String accountOwnerId;
    private int balance;

    public Account() {}

    @JsonCreator
    public Account(@JsonProperty(value = "id", required = true) String id,
                   @JsonProperty(value = "current", required = true) boolean current,
                   @JsonProperty(value = "secondary", required = true) boolean secondary,
                   @JsonProperty(value = "accountOwnerId", required = true) String accountOwnerId,
                   @JsonProperty(value = "balance", required = true) int balance) {
        setId(id);
        setCurrent(current);
        setSecondary(secondary);
        setAccountOwnerId(accountOwnerId);
        setBalance(balance);
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public boolean isSecondary() {
        return secondary;
    }

    public void setSecondary(boolean secondary) {
        this.secondary = secondary;
    }

    public String getAccountOwnerId() {
        return accountOwnerId;
    }

    public void setAccountOwnerId(String accountOwnerId) {
        this.accountOwnerId = accountOwnerId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public int hashCode() {
        int temp1 = Helper.cantorPair(super.hashCode(), secondary ? 200 : 30);
        int temp2 = Helper.cantorPair(temp1, secondary ? 200 : 30);
        int temp3 = Helper.cantorPair(temp2, accountOwnerId.hashCode());
        return Helper.cantorPair(temp3, balance);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null ) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Account) {
            Account another = (Account)obj;
            return super.equals(obj) &&
                   this.current == another.current &&
                   this.secondary == another.secondary &&
                   this.accountOwnerId.equals(another.accountOwnerId) &&
                   this.balance == another.balance;
        }
        return false;
    }
}
