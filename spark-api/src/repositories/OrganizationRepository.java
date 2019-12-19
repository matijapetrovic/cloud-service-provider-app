package repositories;

import java.util.*;
import models.Organization;
import models.User;
import serializer.organization.OrganizationSerializer;


public class OrganizationRepository extends JSONFileRepository<String, Organization> {

    public OrganizationRepository(String filePath) {
        super(filePath);
        Organization o = new Organization("Doktori", "Mateo i Kabi", null);
        o.addUser(new User("mattheo@gmail.com", "Mateo", "Mateovic", null, User.Role.ADMIN));
        save(o);
    }

    @Override
    protected void setSerializer() {
        this.serializer = new OrganizationSerializer();
    }

    @Override
    public Optional<Organization> findByKey(String name) {
        return findAll()
                .stream()
                .filter(x -> x.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}

