package io.blueharvest.ams.services.persistence.controllers;

import io.blueharvest.ams.common.apisec.ApiKeyValidator;
import io.blueharvest.ams.common.apisec.InvalidApiKeyException;
import io.blueharvest.ams.common.spring.ConflictException;
import io.blueharvest.ams.common.spring.InternalServerErrorException;
import io.blueharvest.ams.common.spring.NotFoundException;
import io.blueharvest.ams.common.spring.UnauthorizedException;

import io.blueharvest.ams.services.persistence.DAO;
import io.blueharvest.ams.services.persistence.DuplicateEntityException;
import io.blueharvest.ams.services.persistence.EntityNotFoundException;
import io.blueharvest.ams.services.persistence.domain.Entity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class BaseController<EntityType extends Entity,
        DTOType extends io.blueharvest.ams.domain.dtos.Entity,
        DAOType extends DAO<EntityType>> {

    private ApiKeyValidator apiKeyValidator;
    protected DAOType dao;

    BaseController(DAOType dao, ApiKeyValidator apiKeyValidator) {
        this.dao = dao;
        this.apiKeyValidator = apiKeyValidator;
    }

    protected abstract EntityType makeFromDTO(DTOType dto) throws Exception;
    protected abstract DTOType makeDTO(EntityType entity);

    protected void applyChangesBeforeAdd(EntityType entity) {
    }

    protected void applyChangesAfterAdd(EntityType entity) {
    }

    protected void validateApiKey(String apiKey) {
        try {
            apiKeyValidator.validateKey(apiKey);
        } catch (InvalidApiKeyException e) {
            throw new UnauthorizedException();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String add(@RequestParam(name = "apiKey") String apiKey,
                    @RequestBody DTOType dto) {
        dto.setId(UUID.randomUUID().toString());
        validateApiKey(apiKey);
        try {
            EntityType entity = makeFromDTO(dto);
            applyChangesBeforeAdd(entity);
            dao.add(entity);
            applyChangesAfterAdd(entity);
        } catch (DuplicateEntityException e) {
            throw new ConflictException();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new InternalServerErrorException();
        }
        return dto.getId();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestParam(name = "apiKey") String apiKey,
                       @RequestBody DTOType dto) {
        validateApiKey(apiKey);
        try {
            dao.update(makeFromDTO(dto));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam(name = "apiKey") String apiKey,
                       @PathVariable String id) {
        validateApiKey(apiKey);
        try {
            dao.delete(id);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<DTOType> getAll(@RequestParam(name = "apiKey") String apiKey) {
        validateApiKey(apiKey);
        try {
            Set<DTOType> results = new HashSet<>();
            for (EntityType entity : dao.getAll()) {
                results.add(makeDTO(entity));
            }
            return results;
        } catch (Throwable t) {
            t.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTOType getOne(@RequestParam(name = "apiKey") String apiKey,
                          @PathVariable String id) {
        validateApiKey(apiKey);
        try {
            return makeDTO(dao.getOne(id));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new InternalServerErrorException();
        }
    }
}
