package io.blueharvest.ams.clients.proxies.rest;

import io.blueharvest.ams.clients.proxies.AccountPersistenceProxy;
import io.blueharvest.ams.common.spring.ConflictException;
import io.blueharvest.ams.common.spring.InternalServerErrorException;
import io.blueharvest.ams.common.spring.NotFoundException;
import io.blueharvest.ams.domain.dtos.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

public class AccountPersistenceProxyImpl implements AccountPersistenceProxy {

    @Autowired
    @Qualifier("account-persistence-endpoint")
    private String accountEndpoint;

    @Autowired
    @Qualifier("api-key")
    private String apiKey;

    @Override
    public String add(Account account) {
        RestTemplate restTemplate = new RestTemplate();
        String url = accountEndpoint + "?apiKey=" + apiKey;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(account), String.class);
        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            throw new InternalServerErrorException();
        } else if (response.getStatusCode() == HttpStatus.CONFLICT) {
            throw new ConflictException();
        }
        return response.getBody();
    }

    @Override
    public void update(Account account) {
        // TODO
    }

    @Override
    public void delete(String id) {
        // TODO
    }

    @Override
    public Set<Account> getAll() {
        // TODO
        return null;
    }

    @Override
    public Set<Account> getCustomerAccounts(String customerId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = accountEndpoint + "/ownedBy/" + customerId + "?apiKey=" + apiKey;
        ResponseEntity<Set<Account>> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<Set<Account>>() {});
        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            throw new InternalServerErrorException();
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NotFoundException();
        }
        return response.getBody();
    }

    @Override
    public Account getOne(String id) {
        // TODO
        return null;
    }
}
