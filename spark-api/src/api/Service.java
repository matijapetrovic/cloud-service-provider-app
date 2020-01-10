package api;

import domain.Model;
import storage.json_storage.JSONFileRepository;

import java.util.List;
import java.util.Optional;

public abstract class Service<K, E extends Model<K>> {
    private final JSONFileRepository<K, E> repository;

    public Service(JSONFileRepository<K, E> repository) {
        this.repository = repository;
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    public Optional<E> findByKey(K key) {
        return repository.findByKey(key);
    }

    public boolean add(E entity) {
        Optional<E> e = repository.findByKey(entity.getKey());
        if (e.isPresent())
            return false;

        repository.save(entity);
        return true;
    }

    // TODO check if org name is fine
    public boolean update(K key, E entity) {
        Optional<E> e = repository.findByKey(key);
        if (!e.isPresent())
            return false;

        repository.save(entity);
        return true;
    }

    public boolean delete(K key) {
        Optional<E> e = repository.findByKey(key);
        if (!e.isPresent())
            return false;

        repository.delete(e.get());
        return true;
    }
}
