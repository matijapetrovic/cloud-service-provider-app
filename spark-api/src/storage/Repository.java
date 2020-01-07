package storage;

import domain.Model;

import java.util.Collection;
import java.util.Optional;


public interface Repository<K, E extends Model<K>> {
    void save(E entity);
    void delete(E entity);
    Collection<E> findAll();
    Optional<E> findByKey(K key);
    void saveChanges();
}
