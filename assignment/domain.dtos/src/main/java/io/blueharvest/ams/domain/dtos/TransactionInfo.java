package io.blueharvest.ams.domain.dtos;

public class TransactionInfo {

    private int change;
    private long timestamp;

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
