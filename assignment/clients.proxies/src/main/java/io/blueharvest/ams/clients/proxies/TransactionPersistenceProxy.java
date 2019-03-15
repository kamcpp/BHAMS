package io.blueharvest.ams.clients.proxies;

import io.blueharvest.ams.domain.dtos.Transaction;

import java.util.Set;

public interface TransactionPersistenceProxy extends PersistenceProxy<Transaction> {

    Set<Transaction> getAccountTransactions(String accountId);
}
