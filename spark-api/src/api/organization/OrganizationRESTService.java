package api.organization;

import domain.organization.Organization;
import domain.organization.OrganizationService;
import domain.organization.OrganizationStorage;

import java.util.List;
import java.util.Optional;

import static spark.Spark.halt;

class OrganizationRESTService implements OrganizationService {
    private OrganizationStorage storage;

    public OrganizationRESTService(OrganizationStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<Organization> getAll() {
        return (List<Organization>) storage.findAll();
    }

    @Override
    public Organization getSingle(String name) {
        Optional<Organization> organization = storage.findByName(name);
        if (!organization.isPresent())
            halt(404, "Organization with the name "
                    + name + " not found");

        return organization.get();
    }

    @Override
    public Organization post(Organization organization) {
        if (!storage.add(organization))
            halt(409, "Organization with the name "
                    + organization.getName() + " already exists");
        return storage.findByName(organization.getName()).orElse(null);
    }

    @Override
    public Organization put(String name, Organization organization) {
        if (!storage.update(name, organization))
            halt(404, "Organization with the name "
                    + name + " not found");
        return storage.findByName(organization.getName()).orElse(null);
    }
}
