package domain.organization;

import java.util.List;

public interface OrganizationService {
    List<Organization> getAll();
    Organization getSingle(String name);
    Organization post(Organization organization);
    Organization put(String name, Organization organization);
}