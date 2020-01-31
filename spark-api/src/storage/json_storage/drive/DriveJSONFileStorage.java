package storage.json_storage.drive;

import domain.drive.Drive;
import domain.drive.DriveStorage;
import domain.virtual_machine.VirtualMachine;
import storage.json_storage.JSONDbContext;
import storage.json_storage.JSONFileRepository;
import storage.json_storage.virtual_machine.VirtualMachineReferenceBuilder;

import java.util.Optional;

public class DriveJSONFileStorage implements DriveStorage {
    private JSONFileRepository<String, Drive> repository;
    private DriveReferenceBuilder referenceBuilder;
    private JSONDbContext context;

    public DriveJSONFileStorage(JSONDbContext context) {
        this.context = context;
        repository = context.getDrivesRepository();
        referenceBuilder = context.getDriveReferenceBuilder();
    }

    @Override
    public Iterable<Drive> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Drive> findByName(String name) {
        return repository.findByKey(name);
    }

    @Override
    public boolean add(Drive entity) {
        if (repository.findByKey(entity.getKey()).isPresent())
            return false;

        referenceBuilder.buildReferences(entity);
        entity.buildReferences();
        repository.save(entity);
        context.saveDb();
        return true;
    }

    @Override
    public boolean update(String name, Drive entity) {
        Optional<Drive> toUpdate = repository.findByKey(name);
        if (!toUpdate.isPresent())
            return false;

        if (repository.findByKey(entity.getName()).isPresent() && !name.equalsIgnoreCase(entity.getName()))
            return false;

        referenceBuilder.buildReferences(entity);
        toUpdate.get().update(entity);
        context.saveDb();
        return true;
    }

    @Override
    public boolean delete(String name) {
        Optional<Drive> entity = repository.findByKey(name);
        if (!entity.isPresent())
            return false;

        Drive drive = entity.get();
        drive.removeReferences();

        repository.delete(drive);
        context.saveDb();
        return true;
    }

}
