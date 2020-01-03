package api.organization;

import api.Service;
import domain.organization.Organization;
import storage.organization.OrganizationRepository;

public class OrganizationService extends Service<String, Organization> {
    public OrganizationService() {
        super(new OrganizationRepository("./data/organizations.json"));
    }
}
