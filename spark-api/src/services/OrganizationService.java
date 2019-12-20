package services;

import repositories.JSONFileRepository;
import repositories.OrganizationRepository;

public class OrganizationService extends Service {
    public OrganizationService() {
        super(new OrganizationRepository("./data/organizations.json"));
    }
}
