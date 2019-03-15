package io.blueharvest.ams.services.persistence.controllers;

import io.blueharvest.ams.common.apisec.ApiKeyValidator;
import io.blueharvest.ams.common.spring.InternalServerErrorException;
import io.blueharvest.ams.services.persistence.AccountDAO;
import io.blueharvest.ams.services.persistence.TransactionDAO;
import io.blueharvest.ams.services.persistence.domain.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/transaction")
public class TransactionController extends BaseController<Transaction,
        io.blueharvest.ams.domain.dtos.Transaction, TransactionDAO> {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    public TransactionController(TransactionDAO dao, ApiKeyValidator apiKeyValidator) {
        super(dao, apiKeyValidator);
    }

    @Override
    protected void applyChangesBeforeAdd(Transaction transaction) {
        transaction.setTimestamp(new Date().getTime());
    }

    @Override
    protected void applyChangesAfterAdd(Transaction transaction) {
        transaction.getAccount().setBalance(
                transaction.getAccount().getBalance() + transaction.getChange());
        try {
            accountDAO.update(transaction.getAccount());
        } catch (Throwable t){
            // TODO Log here
        }
    }

    @Override
    protected Transaction makeFromDTO(io.blueharvest.ams.domain.dtos.Transaction dto) throws Exception {
        Transaction entity = new Transaction();
        entity.setId(dto.getId());
        entity.setAccount(accountDAO.getOne(dto.getAccountId()));
        entity.setChange(dto.getChange());
        entity.setTimestamp(0);
        return entity;
    }

    @Override
    protected io.blueharvest.ams.domain.dtos.Transaction makeDTO(Transaction entity) {
        io.blueharvest.ams.domain.dtos.Transaction dto = new io.blueharvest.ams.domain.dtos.Transaction();
        dto.setId(entity.getId());
        dto.setAccountId(entity.getAccount().getId());
        dto.setChange(entity.getChange());
        dto.setTimestamp(entity.getTimestamp());
        return dto;
    }

    @GetMapping("ofAccount/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<io.blueharvest.ams.domain.dtos.Transaction> ofAccount(@RequestParam(name = "apiKey") String apiKey,
                                                               @PathVariable String accountId) {
        validateApiKey(apiKey);
        try {
            Set<io.blueharvest.ams.domain.dtos.Transaction> results = new HashSet<>();
            for (Transaction entity : dao.getOfAccount(accountId)) {
                results.add(makeDTO(entity));
            }
            return results;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new InternalServerErrorException();
        }
    }
}
