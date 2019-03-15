package io.blueharvest.ams.services.persistence.controllers;

import io.blueharvest.ams.common.apisec.ApiKeyValidator;
import io.blueharvest.ams.common.spring.InternalServerErrorException;
import io.blueharvest.ams.services.persistence.AccountDAO;
import io.blueharvest.ams.services.persistence.CustomerDAO;
import io.blueharvest.ams.services.persistence.domain.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/account")
public class AccountsController extends BaseController<Account, io.blueharvest.ams.domain.dtos.Account, AccountDAO> {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    public AccountsController(AccountDAO dao, ApiKeyValidator apiKeyValidator) {
        super(dao, apiKeyValidator);
    }

    @Override
    protected void applyChangesBeforeAdd(Account account) {
        // We set balance to 0 when adding a new account
        account.setBalance(0);
    }

    @Override
    protected Account makeFromDTO(io.blueharvest.ams.domain.dtos.Account dto) throws Exception {
        Account entity = new Account();
        entity.setId(dto.getId());
        // Account must have an owner
        entity.setAccountOwner(customerDAO.getOne(dto.getAccountOwnerId()));
        entity.setBalance(dto.getBalance());
        entity.setCurrent(dto.isCurrent());
        entity.setSecondary(dto.isSecondary());
        return entity;
    }

    @Override
    protected io.blueharvest.ams.domain.dtos.Account makeDTO(Account entity) {
        io.blueharvest.ams.domain.dtos.Account dto = new io.blueharvest.ams.domain.dtos.Account();
        dto.setId(entity.getId());
        dto.setAccountOwnerId(entity.getAccountOwner().getId());
        dto.setBalance(entity.getBalance());
        dto.setCurrent(entity.isCurrent());
        dto.setSecondary(entity.isSecondary());
        return dto;
    }

    @GetMapping("ownedBy/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<io.blueharvest.ams.domain.dtos.Account> ownedBy(@RequestParam(name = "apiKey") String apiKey,
                                                               @PathVariable String customerId) {
        validateApiKey(apiKey);
        try {
            Set<io.blueharvest.ams.domain.dtos.Account> results = new HashSet<>();
            for (Account entity : dao.getOwnedBy(customerId)) {
                results.add(makeDTO(entity));
            }
            return results;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new InternalServerErrorException();
        }
    }
}
