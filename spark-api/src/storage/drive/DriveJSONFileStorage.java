package storage.drive;

import domain.drive.Drive;
import domain.drive.DriveStorage;
import storage.JSONFileRepository;
import storage.drive.serializing.DriveSerializer;

import java.util.Optional;

public class DriveJSONFileStorage implements DriveStorage {
    JSONFileRepository<String, Drive> repository;

    public DriveJSONFileStorage(String filePath) {
        repository = new JSONFileRepository<>(new DriveSerializer(), filePath);
    }

    @Override
    public Iterable<Drive> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Drive> findByName(String name) {
        return repository.findByKey(name);
    }

    @Override
    public boolean add(Drive entity) {
        if (repository.findByKey(entity.getKey()).isPresent())
            return false;

        repository.save(entity);
        return true;
    }

    @Override
    public boolean update(String name, Drive entity) {
        if (!delete(name))
            return false;
        repository.save(entity);
        return true;
    }

    @Override
    public boolean delete(String name) {
        Optional<Drive> entity = repository.findByKey(name);
        if (!entity.isPresent())
            return false;

        repository.delete(entity.get());
        return true;
    }

}
