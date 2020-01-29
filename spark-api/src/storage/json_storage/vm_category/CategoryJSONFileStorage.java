package storage.json_storage.vm_category;

import domain.user.User;
import domain.vm_category.CategoryStorage;
import domain.vm_category.VMCategory;
import storage.json_storage.JSONDbContext;
import storage.json_storage.JSONFileRepository;

import java.util.Optional;

public class CategoryJSONFileStorage implements CategoryStorage {
    JSONFileRepository<String, VMCategory> repository;

    public CategoryJSONFileStorage(JSONDbContext context) {
        repository = context.getVmCategoriesRepository();
    }

    @Override
    public Iterable<VMCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<VMCategory> findByName(String name) {
        return repository.findByKey(name);
    }

    @Override
    public boolean add(VMCategory entity) {
        if (repository.findByKey(entity.getKey()).isPresent())
            return false;

        repository.save(entity);
        return true;
    }

    @Override
    public boolean update(String name, VMCategory entity) {
        Optional<VMCategory> toUpdate = repository.findByKey(name);
        if (!toUpdate.isPresent())
            return false;

        toUpdate.get().update(entity);
        repository.save(entity);
        return true;
    }

    @Override
    public boolean delete(String name) {
        Optional<VMCategory> entity = repository.findByKey(name);
        if (!entity.isPresent())
            return false;

        repository.delete(entity.get());
        return true;
    }

}
