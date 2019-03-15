package io.blueharvest.ams.services.persistence;

import io.blueharvest.ams.services.persistence.domain.Entity;

public interface Predicate<T extends Entity> {

    boolean match(T entity);
}
