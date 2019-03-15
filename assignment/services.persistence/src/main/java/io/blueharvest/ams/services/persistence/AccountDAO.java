package io.blueharvest.ams.services.persistence;

import io.blueharvest.ams.services.persistence.domain.Account;

import java.util.Set;

public interface AccountDAO extends DAO<Account>  {

    Set<Account> getOwnedBy(String customerId);
}
