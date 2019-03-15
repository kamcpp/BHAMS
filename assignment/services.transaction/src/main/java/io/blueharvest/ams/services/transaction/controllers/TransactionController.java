package io.blueharvest.ams.services.transaction.controllers;

import io.blueharvest.ams.clients.proxies.AccountPersistenceProxy;
import io.blueharvest.ams.clients.proxies.CustomerPersistenceProxy;
import io.blueharvest.ams.clients.proxies.TransactionPersistenceProxy;

import io.blueharvest.ams.domain.dtos.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/info")
public class TransactionController {

    @Autowired
    private AccountPersistenceProxy accountPersistenceProxy;

    @Autowired
    private TransactionPersistenceProxy transactionPersistenceProxy;

    @Autowired
    private CustomerPersistenceProxy customerPersistenceProxy;

    @GetMapping("customers")
    @ResponseStatus(HttpStatus.OK)
    public Set<Customer> getCustomers(@RequestParam(name = "apiKey") String apiKey) {
        return customerPersistenceProxy.getAll();
    }

    @GetMapping("accountTransactions/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<Transaction> getAccountTransactions(@RequestParam(name = "apiKey") String apiKey,
                                                   @PathVariable String accountId) {
        return transactionPersistenceProxy.getAccountTransactions(accountId);
    }

    @GetMapping("customerInfo/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerInfo getCustomerInfo(@RequestParam(name = "apiKey") String apiKey,
                                        @PathVariable String customerId) {
        Customer customer = customerPersistenceProxy.getOne(customerId);
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setName(customer.getName());
        customerInfo.setSurname(customer.getSurname());

        Set<Account> accounts = accountPersistenceProxy.getCustomerAccounts(customerId);
        List<AccountInfo> accountInfos = new ArrayList<>();
        for (Account account : accounts) {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setId(account.getId());
            accountInfo.setBalance(account.getBalance());
            accountInfos.add(accountInfo);

            Set<Transaction> transactions = transactionPersistenceProxy.getAccountTransactions(account.getId());
            List<TransactionInfo> transactionInfos = new ArrayList<>();
            for (Transaction transaction : transactions) {
                TransactionInfo transactionInfo = new TransactionInfo();
                transactionInfo.setChange(transaction.getChange());
                transactionInfo.setTimestamp(transaction.getTimestamp());
                transactionInfos.add(transactionInfo);
            }
            accountInfo.setTransactionInfos(transactionInfos);
        }
        customerInfo.setAccountInfos(accountInfos);
        return customerInfo;
    }
}
