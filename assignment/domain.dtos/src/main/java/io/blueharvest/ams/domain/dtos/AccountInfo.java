package io.blueharvest.ams.domain.dtos;

import java.util.List;

public class AccountInfo {

    private String id;
    private int balance;
    private List<TransactionInfo> transactionInfos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<TransactionInfo> getTransactionInfos() {
        return transactionInfos;
    }

    public void setTransactionInfos(List<TransactionInfo> transactionInfos) {
        this.transactionInfos = transactionInfos;
    }
}
