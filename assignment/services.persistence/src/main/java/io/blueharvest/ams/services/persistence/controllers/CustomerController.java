package io.blueharvest.ams.services.persistence.controllers;

import io.blueharvest.ams.common.apisec.ApiKeyValidator;
import io.blueharvest.ams.services.persistence.CustomerDAO;
import io.blueharvest.ams.services.persistence.domain.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController<Customer, io.blueharvest.ams.domain.dtos.Customer, CustomerDAO> {

    @Autowired
    public CustomerController(CustomerDAO dao, ApiKeyValidator apiKeyValidator) {
        super(dao, apiKeyValidator);
    }

    @Override
    protected Customer makeFromDTO(io.blueharvest.ams.domain.dtos.Customer dto) throws Exception {
        Customer entity = new Customer();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        return entity;
    }

    @Override
    protected io.blueharvest.ams.domain.dtos.Customer makeDTO(Customer entity) {
        io.blueharvest.ams.domain.dtos.Customer dto = new io.blueharvest.ams.domain.dtos.Customer();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        return dto;
    }

    @GetMapping("init")
    @ResponseStatus(HttpStatus.OK)
    public void init(@RequestParam(name = "apiKey") String apiKey) {
        io.blueharvest.ams.domain.dtos.Customer customer1 =
                new io.blueharvest.ams.domain.dtos.Customer();
        customer1.setName("John");
        customer1.setSurname("Travolta");
        add(apiKey, customer1);

        io.blueharvest.ams.domain.dtos.Customer customer2 =
                new io.blueharvest.ams.domain.dtos.Customer();
        customer2.setName("Johnny");
        customer2.setSurname("Depp");
        add(apiKey, customer2);

        io.blueharvest.ams.domain.dtos.Customer customer3 =
                new io.blueharvest.ams.domain.dtos.Customer();
        customer3.setName("Tom");
        customer3.setSurname("Hanks");
        add(apiKey, customer3);
    }
}
