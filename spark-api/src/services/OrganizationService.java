package services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import models.Organization;
import models.User;


public class OrganizationService {
    private final Set<Organization> organizations = loadOrganizationsFromFile("organizations.json");

    public Iterable<Organization> getAllOrganizations() {
        return organizations;
    }

    public Optional<Organization> getOrganization(String name) {
        return organizations.stream().filter(o -> o.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<Organization> updateOrganization(String name, Organization o) {
        if (!organizations.contains(o))
            return Optional.empty();
        
        organizations.removeIf(org -> org.getName().equalsIgnoreCase(name));
        o.setName(name);
        organizations.add(o);

        return Optional.of(o);
    }

    public boolean deleteOrganization(String name) {
        if (organizations.stream().noneMatch(org -> org.getName().equalsIgnoreCase(name)))
            return false;
        
        organizations.removeIf(org -> org.getName().equalsIgnoreCase(name));
        return true;
    }
    // Admin
    public Iterable<User> getAllUsersFromOrganization(String organizatioName){
    	return getOrganization(organizatioName).get().getUsers();
    }
    
    public boolean AddUserToOrganization(User user, Organization organization) {
    	if(!getOrganization(organization.getName()).isPresent()) 
    		return false;
    	
    	return organization.getUsers().add(user);
    }
    private Set<Organization> loadOrganizationsFromFile(String path) {
        HashSet<Organization> result = new HashSet<Organization>();
        result.add(new Organization("Glavne baje", "Samo najjaci gym momci", null));
        result.add(new Organization("Nubovi", "Kivun i dzon", null));
        return result;
    }
}
