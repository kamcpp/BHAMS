package io.blueharvest.ams.clients.proxies;

import io.blueharvest.ams.domain.dtos.Account;

import java.util.Set;

public interface AccountPersistenceProxy extends PersistenceProxy<Account> {

    Set<Account> getCustomerAccounts(String customerId);
}
