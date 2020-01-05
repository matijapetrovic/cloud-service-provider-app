package api.organization;

import domain.organization.Organization;
import domain.organization.OrganizationService;
import domain.organization.OrganizationStorage;

import java.util.List;

import static spark.Spark.halt;

class SuperAdminOrganizationService implements OrganizationService {
    private OrganizationStorage organizationStorage;

    public SuperAdminOrganizationService(OrganizationStorage organizationStorage) {
        this.organizationStorage = organizationStorage;
    }

    @Override
    public List<Organization> getAll() {
        return (List<Organization>) organizationStorage.findAll();
    }

    @Override
    public Organization getSingle(String name) {
        return organizationStorage
                .findByName(name)
                .orElse(null);
    }

    @Override
    public void post(Organization organization) {
        if (!organizationStorage.add(organization))
            halt(409, "Organization with the name "
                    + organization.getName() + " already exists");
    }

    @Override
    public void put(String name, Organization organization) {

    }
}
