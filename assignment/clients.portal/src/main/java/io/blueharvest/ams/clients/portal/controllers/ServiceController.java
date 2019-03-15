package io.blueharvest.ams.clients.portal.controllers;

import io.blueharvest.ams.domain.dtos.AddSecondaryCurrentAccount;
import io.blueharvest.ams.domain.dtos.Customer;
import io.blueharvest.ams.domain.dtos.CustomerInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@RestController
@RequestMapping("svc")
public class ServiceController {

    @Autowired
    @Qualifier("api-key")
    private String apiKey;

    @Autowired
    @Qualifier("account-endpoint")
    private String accountEndpoint;

    @Autowired
    @Qualifier("transaction-endpoint")
    private String transactionEndpoint;

    @Autowired
    @Qualifier("customer-persistence-endpoint")
    private String customerPersistenceEndpoint;

    @GetMapping(value = "initCustomers")
    @ResponseStatus(HttpStatus.OK)
    public void initCustomers() {
        RestTemplate restTemplate = new RestTemplate();
        String url = customerPersistenceEndpoint + "/init?apiKey=" + apiKey;
        restTemplate.exchange(url, HttpMethod.GET,null, String.class);
    }

    @GetMapping(value = "customers")
    @ResponseStatus(HttpStatus.OK)
    public Set<Customer> getCustomers() {
        RestTemplate restTemplate = new RestTemplate();
        String url = transactionEndpoint + "/info/customers?apiKey=" + apiKey;
        ResponseEntity<Set<Customer>> response = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<Set<Customer>>() {});
        return response.getBody();
    }

    @GetMapping("addSecondaryCurrentAccount/{customerId},{initialCredit}")
    @ResponseStatus(HttpStatus.OK)
    public String addSecondaryCurrentAccount(@PathVariable String customerId,
                                             @PathVariable int initialCredit) {
        AddSecondaryCurrentAccount dto = new AddSecondaryCurrentAccount();
        dto.setCustomerId(customerId);
        dto.setInitialCredit(initialCredit);
        RestTemplate restTemplate = new RestTemplate();
        String url = accountEndpoint + "/manage/addSecondaryCurrentAccount?apiKey=" + apiKey;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(dto), String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            return "{\"result\": \"Failure\"}";
        }
        return "{\"result\": \"Success\"}";
    }

    @GetMapping("customerInfo/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerInfo getCustomerInfo(@PathVariable String customerId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = transactionEndpoint + "/info/customerInfo/" + customerId + "?apiKey=" + apiKey;
        ResponseEntity<CustomerInfo> response = restTemplate.exchange(url, HttpMethod.GET,
                null, CustomerInfo.class);
        return response.getBody();
    }
}
