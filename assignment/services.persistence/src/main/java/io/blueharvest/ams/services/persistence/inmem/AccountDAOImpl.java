package io.blueharvest.ams.services.persistence.inmem;

import io.blueharvest.ams.services.persistence.AccountDAO;
import io.blueharvest.ams.services.persistence.domain.Account;

import java.util.Set;

public class AccountDAOImpl extends DAOImpl<Account> implements AccountDAO {

    @Override
    public Set<Account> getOwnedBy(String customerId) {
        return getMatches(entity -> entity.getAccountOwner().getId().equals(customerId));
    }
}
