package io.blueharvest.ams.clients.proxies;

import java.util.Set;

public interface PersistenceProxy<DTOType> {

    String add(DTOType dto);

    void update(DTOType dto);

    void delete(String id);

    Set<DTOType> getAll();

    DTOType getOne(String id);
}
