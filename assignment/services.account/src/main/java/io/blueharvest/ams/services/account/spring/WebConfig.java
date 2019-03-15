package io.blueharvest.ams.services.account.spring;

import io.blueharvest.ams.clients.proxies.AccountPersistenceProxy;
import io.blueharvest.ams.clients.proxies.TransactionPersistenceProxy;
import io.blueharvest.ams.clients.proxies.rest.AccountPersistenceProxyImpl;
import io.blueharvest.ams.clients.proxies.rest.TransactionPersistenceProxyImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    @Qualifier("api-key")
    public String getApiKey() {
        return "XYZ";
    }

    @Bean
    @Qualifier("account-persistence-endpoint")
    public String getAccountPersistenceEndpoint() {
        return "http://172.18.0.10:8080/account";
    }

    @Bean
    @Qualifier("customer-persistence-endpoint")
    public String getCustomerPersistenceEndpoint() {
        return "http://172.18.0.10:8080/customer";
    }

    @Bean
    @Qualifier("transaction-persistence-endpoint")
    public String getTransactionPersistenceEndpoint() {
        return "http://172.18.0.10:8080/transaction";
    }

    @Bean
    public AccountPersistenceProxy getAccountPersistenceProxy() {
        return new AccountPersistenceProxyImpl();
    }

    @Bean
    public TransactionPersistenceProxy getTransactionPersistenceProxy() {
        return new TransactionPersistenceProxyImpl();
    }
}
