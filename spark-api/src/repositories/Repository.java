package repositories;

import java.util.Optional;


public interface Repository<ID, T> {
    void add(T entity);
    void delete(T entity);
    Iterable<T> findAll();
    Optional<T> findbyKey(ID id);
}
