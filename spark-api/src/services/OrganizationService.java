package services;

import models.Organization;
import repositories.OrganizationRepository;

public class OrganizationService extends Service<String, Organization> {
    public OrganizationService() {
        super(new OrganizationRepository("./data/organizations.json"));
    }
}
