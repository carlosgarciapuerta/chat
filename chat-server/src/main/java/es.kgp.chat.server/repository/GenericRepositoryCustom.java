package es.kgp.chat.server.repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kgp on 19/01/2014.
 */
public interface GenericRepositoryCustom<OBJECT, ID extends Serializable> {

    OBJECT save(OBJECT obj);

    OBJECT findById(ID id);

    void update(OBJECT obj);

    void delete(Long id);

    List<OBJECT> findAll();

}
