package io.blueharvest.ams.services.persistence;

import io.blueharvest.ams.services.persistence.domain.Entity;

import java.util.Set;

public interface DAO<T extends Entity> {

    void add(T entity) throws DuplicateEntityException;

    void update(T entity) throws EntityNotFoundException;

    void delete(String id) throws EntityNotFoundException;

    T getOne(String id) throws EntityNotFoundException;

    Set<T> getAll();

    Set<T> getMatches(Predicate<T> predicate);
}
