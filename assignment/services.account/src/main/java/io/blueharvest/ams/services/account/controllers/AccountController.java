package io.blueharvest.ams.services.account.controllers;

import io.blueharvest.ams.clients.proxies.AccountPersistenceProxy;
import io.blueharvest.ams.clients.proxies.TransactionPersistenceProxy;
import io.blueharvest.ams.domain.dtos.Account;
import io.blueharvest.ams.domain.dtos.AddSecondaryCurrentAccount;
import io.blueharvest.ams.domain.dtos.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage")
public class AccountController {

    @Autowired
    private AccountPersistenceProxy accountPersistenceProxy;

    @Autowired
    private TransactionPersistenceProxy transactionPersistenceProxy;

    @PostMapping("addSecondaryCurrentAccount")
    @ResponseStatus(HttpStatus.OK)
    public void addSecondaryCurrentAccount(@RequestParam(name = "apiKey") String apiKey,
                                           @RequestBody AddSecondaryCurrentAccount dto) {
        // TODO: Make this transactional
        Account account = new Account();
        account.setId("");
        account.setAccountOwnerId(dto.getCustomerId());
        account.setCurrent(true);
        account.setSecondary(true);
        account.setBalance(0);
        String id = accountPersistenceProxy.add(account);
        if (dto.getInitialCredit() > 0) {
            Transaction transaction = new Transaction();
            transaction.setId("");
            transaction.setChange(dto.getInitialCredit());
            transaction.setAccountId(id);
            transactionPersistenceProxy.add(transaction);
        }
    }
}
