package repositories;

import models.Organization;
import serializer.organization.OrganizationSerializer;

public class OrganizationRepository extends JSONFileRepository<String, Organization> {
    public OrganizationRepository(String filePath) {
        super(new OrganizationSerializer(), filePath);
    }
}
