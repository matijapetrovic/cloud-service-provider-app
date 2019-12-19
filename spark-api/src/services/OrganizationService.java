package services;

import java.util.Optional;

import models.Organization;
import repositories.OrganizationRepository;


public class OrganizationService {
    private final OrganizationRepository repository = new OrganizationRepository("../data/organizations.json");

    public Iterable<Organization> findAll() {
        return repository.findAll();
    }

    public Optional<Organization> findByName(String name) {
        return repository.findbyKey(name);
    }

    public boolean add(Organization org) {
        Optional<Organization> o = repository.findbyKey(org.getName());
        if (o.isPresent())
            return false;

        repository.save(org);
        return true;
    }

    // TODO check if org name is fine
    public boolean update(String name, Organization org) {
        Optional<Organization> o = repository.findbyKey(name);
        if (!o.isPresent())
            return false;

        repository.save(org);
        return true;
    }

    public boolean delete(String name) {
        Optional<Organization> o = repository.findbyKey(name);
        if (!o.isPresent())
            return false;

        repository.delete(o.get());
        return true;
    }
}
