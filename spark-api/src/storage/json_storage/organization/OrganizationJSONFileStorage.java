package storage.json_storage.organization;

import domain.organization.Organization;
import domain.organization.OrganizationStorage;
import storage.json_storage.JSONDbContext;
import storage.json_storage.JSONFileRepository;

import java.util.Optional;

public class OrganizationJSONFileStorage implements OrganizationStorage {
    JSONFileRepository<String, Organization> repository;

    public OrganizationJSONFileStorage(JSONDbContext context) {
        repository = context.getOrganizationsRepository();
    }

    @Override
    public Iterable<Organization> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Organization> findByName(String name) {
        return repository.findByKey(name);
    }

    @Override
    public boolean add(Organization entity) {
        if (repository.findByKey(entity.getKey()).isPresent())
            return false;

        repository.save(entity);
        return true;
    }

    @Override
    public boolean update(String name, Organization entity) {
        if (!delete(name))
            return false;
        repository.save(entity);
        return true;
    }

    @Override
    public boolean delete(String name) {
        Optional<Organization> entity = repository.findByKey(name);
        if (!entity.isPresent())
            return false;

        repository.delete(entity.get());
        return true;
    }
}
