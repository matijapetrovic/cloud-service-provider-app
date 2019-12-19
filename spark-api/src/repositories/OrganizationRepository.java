package repositories;

import java.util.*;

import models.Organization;


public class OrganizationRepository extends JSONFileRepository<String, Organization> {

    public OrganizationRepository(String filePath) {
        super(filePath);
        loadEntities(Organization.class);
    }

    @Override
    public Optional<Organization> findbyKey(String name) {
        return findAll()
                .stream()
                .filter(x -> x.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}

