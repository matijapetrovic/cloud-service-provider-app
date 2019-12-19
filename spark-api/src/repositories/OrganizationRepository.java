package repositories;

import java.util.*;
import models.Organization;
import models.User;


public class OrganizationRepository extends JSONFileRepository<String, Organization> {

    public OrganizationRepository(String filePath) {
        super(filePath);
        loadEntities(Organization[].class);
        Organization o = new Organization("Doktori", "Mateo i Kabi", null);
        o.addUser(new User("Mateo", "Mateo", "Mateovic", null, User.Role.ADMIN));
        save(o);
    }

    @Override
    public Optional<Organization> findbyKey(String name) {
        return findAll()
                .stream()
                .filter(x -> x.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}

