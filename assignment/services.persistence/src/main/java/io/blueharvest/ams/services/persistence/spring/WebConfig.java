package io.blueharvest.ams.services.persistence.spring;

import io.blueharvest.ams.common.apisec.ApiKeyValidator;
import io.blueharvest.ams.common.apisec.BypassApiKeyValidator;
import io.blueharvest.ams.services.persistence.AccountDAO;
import io.blueharvest.ams.services.persistence.CustomerDAO;
import io.blueharvest.ams.services.persistence.TransactionDAO;
import io.blueharvest.ams.services.persistence.inmem.AccountDAOImpl;
import io.blueharvest.ams.services.persistence.inmem.CustomerDAOImpl;
import io.blueharvest.ams.services.persistence.inmem.TransactionDAOImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public ApiKeyValidator getApiKeyValidator() {
        return new BypassApiKeyValidator();
    }

    @Bean
    public AccountDAO getAccountDAO() {
        return new AccountDAOImpl();
    }

    @Bean
    public CustomerDAO getCustomerDAO() {
        return new CustomerDAOImpl();
    }

    @Bean
    public TransactionDAO getTransactionDAO() {
        return new TransactionDAOImpl();
    }
}
