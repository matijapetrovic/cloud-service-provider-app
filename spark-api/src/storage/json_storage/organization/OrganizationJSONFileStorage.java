package storage.json_storage.organization;

import domain.organization.Organization;
import domain.organization.OrganizationStorage;
import storage.json_storage.JSONDbContext;
import storage.json_storage.JSONFileRepository;

import java.io.*;
import java.util.Base64;
import java.util.Optional;

public class OrganizationJSONFileStorage implements OrganizationStorage {
    private JSONFileRepository<String, Organization> repository;
    private JSONDbContext context;

    public OrganizationJSONFileStorage(JSONDbContext context) {
        this.context = context;
        repository = context.getOrganizationsRepository();
    }

    @Override
    public Iterable<Organization> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Organization> findByName(String name) {
        return repository.findByKey(name);
    }

    @Override
    public boolean add(Organization entity) {
        if (repository.findByKey(entity.getKey()).isPresent())
            return false;

        addLogo(entity);
        repository.save(entity);
        context.saveDb();
        return true;
    }

    @Override
    public boolean update(String name, Organization entity) {
        Optional<Organization> toUpdate = repository.findByKey(name);
        if (!toUpdate.isPresent())
            return false;

        if (!name.equalsIgnoreCase(entity.getName()) && repository.findByKey(entity.getName()).isPresent())
            return false;

        updateLogo(entity);
        toUpdate.get().update(entity);
        context.saveDb();
        return true;
    }

    @Override
    public boolean delete(String name) {
        Optional<Organization> entity = repository.findByKey(name);
        if (!entity.isPresent())
            return false;

        repository.delete(entity.get());
        context.saveDb();
        return true;
    }

    private void addLogo(Organization entity) {
        if (entity.getLogo() == null) {
            entity.setLogo("logo.png");
            return;
        }
        saveLogo(entity);
    }

    private void updateLogo(Organization entity) {
        if (entity.getLogo() == null || !entity.getLogo().contains("base64"))
            return;
        saveLogo(entity);
    }

    private void saveLogo(Organization entity) {
        String encodedImage = entity.getLogo();
        if (encodedImage.contains(","))
            encodedImage = entity.getLogo().split(",")[1];

        byte[] decodedImage = Base64.getDecoder().decode(encodedImage);
        String fileName = entity.getName() + ".png";
        try (OutputStream out = new FileOutputStream(new File("./src/main/resources/public/" + fileName))) {
            out.write(decodedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (OutputStream out = new FileOutputStream(new File("./out/production/spark-api/public/" + fileName))) {
            out.write(decodedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        entity.setLogo(fileName);
    }
}
