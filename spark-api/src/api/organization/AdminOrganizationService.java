package api.organization;

import domain.organization.Organization;
import domain.organization.OrganizationService;
import domain.organization.OrganizationStorage;
import domain.user.User;

import java.util.List;

import static spark.Spark.halt;

public class AdminOrganizationService implements OrganizationService {
    private OrganizationStorage storage;

    public AdminOrganizationService(OrganizationStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<Organization> getAll() {
        halt(403, "Forbidden");
        return null;
    }

    @Override
    public Organization getSingle(String name) {
        // resi da moze user da dohvati svoju organizaciju
        halt(403, "Forbidden");
        return null;
    }

    @Override
    public void post(Organization organization) {
        halt(403, "Forbidden");
    }

    @Override
    public void put(String name, Organization organization) {

    }
}
