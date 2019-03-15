package io.blueharvest.ams.clients.proxies.rest;

import io.blueharvest.ams.clients.proxies.TransactionPersistenceProxy;
import io.blueharvest.ams.common.spring.ConflictException;
import io.blueharvest.ams.common.spring.InternalServerErrorException;
import io.blueharvest.ams.common.spring.NotFoundException;
import io.blueharvest.ams.domain.dtos.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

public class TransactionPersistenceProxyImpl implements TransactionPersistenceProxy {

    @Autowired
    @Qualifier("transaction-persistence-endpoint")
    private String transactionEndpoint;

    @Autowired
    @Qualifier("api-key")
    private String apiKey;

    @Override
    public String add(Transaction transaction) {
        RestTemplate restTemplate = new RestTemplate();
        String url = transactionEndpoint + "?apiKey=" + apiKey;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(transaction), String.class);
        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            throw new InternalServerErrorException();
        } else if (response.getStatusCode() == HttpStatus.CONFLICT) {
            throw new ConflictException();
        }
        return response.getBody();
    }

    @Override
    public void update(Transaction dto) {
        // TODO
    }

    @Override
    public void delete(String id) {
        // TODO
    }

    @Override
    public Set<Transaction> getAll() {
        // TODO
        return null;
    }

    @Override
    public Transaction getOne(String id) {
        // TODO
        return null;
    }

    @Override
    public Set<Transaction> getAccountTransactions(String accountId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = transactionEndpoint + "/ofAccount/" + accountId + "?apiKey=" + apiKey;
        ResponseEntity<Set<Transaction>> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<Set<Transaction>>() {});
        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            throw new InternalServerErrorException();
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NotFoundException();
        }
        return response.getBody();
    }
}
