package io.blueharvest.ams.domain.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction extends Entity {

    private String accountId;
    private long timestamp;
    private int change;

    public Transaction() {}

    @JsonCreator
    public Transaction(@JsonProperty(value = "id", required = true) String id,
                       @JsonProperty(value = "accountId", required = true) String accountId,
                       @JsonProperty(value = "change", required = true) int change) {
        setId(id);
        setAccountId(accountId);
        setChange(change);
        timestamp = 0;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }

    @Override
    public int hashCode() {
        int temp1 = Helper.cantorPair(super.hashCode(), accountId.hashCode());
        int temp2 = Helper.cantorPair(temp1, (int)timestamp);
        return Helper.cantorPair(temp2, change);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null ) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Transaction) {
            Transaction another = (Transaction)obj;
            return super.equals(obj) &&
                    this.accountId.equals(another.accountId) &&
                    this.timestamp == another.timestamp &&
                    this.change == another.change;
        }
        return false;
    }
}
