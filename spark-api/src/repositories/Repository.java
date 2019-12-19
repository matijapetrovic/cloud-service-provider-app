package repositories;

import java.util.Collection;
import java.util.Optional;


public interface Repository<ID, T> {
    void save(T entity);
    void delete(T entity);
    Collection<T> findAll();
    Optional<T> findByKey(ID id);
}
