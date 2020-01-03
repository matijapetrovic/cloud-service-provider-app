package storage.organization;

import domain.organization.Organization;
import storage.JSONFileRepository;
import storage.organization.serializing.OrganizationSerializer;

public class OrganizationRepository extends JSONFileRepository<String, Organization> {
    public OrganizationRepository(String filePath) {
        super(new OrganizationSerializer(), filePath);
    }
}
