package io.blueharvest.ams.services.persistence.inmem;

import io.blueharvest.ams.services.persistence.TransactionDAO;
import io.blueharvest.ams.services.persistence.domain.Transaction;

import java.util.Set;

public class TransactionDAOImpl extends DAOImpl<Transaction> implements TransactionDAO {

    @Override
    public Set<Transaction> getOfAccount(String accountId) {
        return getMatches(entity -> entity.getAccount().getId().equals(accountId));
    }
}
