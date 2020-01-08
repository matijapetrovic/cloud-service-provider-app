package storage.json_storage.user;

import domain.user.User;
import domain.user.UserStorage;
import storage.json_storage.JSONDbContext;
import storage.json_storage.JSONFileRepository;

import java.util.Optional;

public class UserJSONFileStorage implements UserStorage {
    JSONFileRepository<String, User> repository;

    public UserJSONFileStorage(JSONDbContext context) {
        repository = context.getUsersRepository();
    }

    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findByName(String name) {
        return repository.findByKey(name);
    }

    @Override
    public boolean add(User entity) {
        if (repository.findByKey(entity.getKey()).isPresent())
            return false;

        repository.save(entity);
        return true;
    }

    @Override
    public boolean update(String name, User entity) {
        if (!delete(name))
            return false;
        repository.save(entity);
        return true;
    }

    @Override
    public boolean delete(String name) {
        Optional<User> entity = repository.findByKey(name);
        if (!entity.isPresent())
            return false;

        repository.delete(entity.get());
        return true;
    }
}
