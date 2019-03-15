package io.blueharvest.ams.services.persistence.inmem;

import io.blueharvest.ams.services.persistence.DAO;
import io.blueharvest.ams.services.persistence.DuplicateEntityException;
import io.blueharvest.ams.services.persistence.EntityNotFoundException;
import io.blueharvest.ams.services.persistence.Predicate;
import io.blueharvest.ams.services.persistence.domain.Entity;

import java.util.*;

public class DAOImpl<T extends Entity> implements DAO<T> {

    protected Map<String, T> repository;

    public DAOImpl() {
        repository = new HashMap<>();
    }

    public void add(T entity) throws DuplicateEntityException {
        if (repository.containsKey(entity.getId())) {
            throw new DuplicateEntityException();
        }
        repository.put(entity.getId(), entity);
    }

    public void update(T entity) throws EntityNotFoundException {
        if (!repository.containsKey(entity.getId())) {
            throw new EntityNotFoundException();
        }
        if (repository.get(entity.getId()) == entity) {
            return;
        }
        repository.replace(entity.getId(), entity);
    }

    public void delete(String id) throws EntityNotFoundException {
        if (!repository.containsKey(id)) {
            throw new EntityNotFoundException();
        }
        repository.remove(id);
    }

    public T getOne(String id) throws EntityNotFoundException {
        if (!repository.containsKey(id)) {
            throw new EntityNotFoundException();
        }
        return repository.get(id);
    }

    public Set<T> getAll() {
        return new HashSet<>(repository.values());
    }

    public Set<T> getMatches(Predicate<T> predicate) {
        Set<T> results = new HashSet<>();
        for (T entity : repository.values()) {
            if (predicate.match(entity)) {
                results.add(entity);
            }
        }
        return results;
    }
}
