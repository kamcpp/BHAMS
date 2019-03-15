package io.blueharvest.ams.clients.proxies.rest;

import io.blueharvest.ams.clients.proxies.CustomerPersistenceProxy;
import io.blueharvest.ams.common.spring.InternalServerErrorException;
import io.blueharvest.ams.common.spring.NotFoundException;
import io.blueharvest.ams.domain.dtos.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

public class CustomerPersistenceProxyImpl implements CustomerPersistenceProxy {

    @Autowired
    @Qualifier("customer-persistence-endpoint")
    private String customerEndpoint;

    @Autowired
    @Qualifier("api-key")
    private String apiKey;

    @Override
    public String add(Customer dto) {
        return null;
    }

    @Override
    public void update(Customer dto) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Set<Customer> getAll() {
        RestTemplate restTemplate = new RestTemplate();
        String url = customerEndpoint + "?apiKey=" + apiKey;
        ResponseEntity<Set<Customer>> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<Set<Customer>>() {});
        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            throw new InternalServerErrorException();
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NotFoundException();
        }
        return response.getBody();
    }

    @Override
    public Customer getOne(String id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = customerEndpoint + "/" + id + "?apiKey=" + apiKey;
        ResponseEntity<Customer> response = restTemplate.exchange(url, HttpMethod.GET,
                null, Customer.class);
        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            throw new InternalServerErrorException();
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new NotFoundException();
        }
        return response.getBody();
    }
}
