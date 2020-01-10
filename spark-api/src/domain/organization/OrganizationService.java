package domain.organization;

import java.util.List;

public interface OrganizationService {
    List<Organization> getAll();
    Organization getSingle(String name);
    void post(Organization organization);
    void put(String name, Organization organization);
}