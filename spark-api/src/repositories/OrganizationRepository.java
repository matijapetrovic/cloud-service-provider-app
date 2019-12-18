package repositories;

import com.google.gson.stream.JsonReader;
import main.App;
import models.Organization;

import java.io.*;
import java.util.*;
import java.util.logging.Level;


public class OrganizationRepository implements Repository<String, Organization> {
    private final String filePath;
    private Collection<Organization> repo;

    public OrganizationRepository(String filePath) {
        this.filePath = filePath;
        repo = new ArrayList<Organization>();
        loadOrganizations();
}

    @Override
    public void add(Organization org) {
        repo.add(org);
        saveOrganizations();
    }

    @Override
    public void delete(Organization org) {
        repo.remove(org);
        saveOrganizations();
    }

    @Override
    public Iterable<Organization> findAll() {
        return repo;
    }

    @Override
    public Optional<Organization> findbyKey(String name) {
        return repo.stream().filter(x -> x.getName().equalsIgnoreCase(name)).findFirst();
    }

    private void loadOrganizations() {
        List<Organization> data;
        try (JsonReader reader = new JsonReader(new FileReader(filePath))) {
            data = Arrays.asList(App.g.fromJson(reader, Organization[].class));
            repo.addAll(data);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            App.logger.log(Level.WARNING, "Reading organizations from " + filePath + " failed");
        }
    }

    private void saveOrganizations() {
        try (FileWriter writer = new FileWriter(filePath)) {
            App.g.toJson(repo, writer);
        } catch(IOException e) {
            App.logger.log(Level.WARNING, "Saving organizations to " + filePath + " failed");
        }
    }
}

