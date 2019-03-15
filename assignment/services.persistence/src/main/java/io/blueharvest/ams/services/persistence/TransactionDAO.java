package io.blueharvest.ams.services.persistence;

import io.blueharvest.ams.services.persistence.domain.Transaction;

import java.util.Set;

public interface TransactionDAO extends DAO<Transaction>  {

    Set<Transaction> getOfAccount(String accountId);
}
