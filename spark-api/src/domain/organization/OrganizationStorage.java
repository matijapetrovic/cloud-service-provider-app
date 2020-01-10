package domain.organization;

import java.util.Optional;

public interface OrganizationStorage {
    Iterable<Organization> findAll();
    Optional<Organization> findByName(String name);
    boolean add(Organization entity);
    boolean update(String name, Organization entity);
    boolean delete(String name);
}