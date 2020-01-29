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

    public DriveJSONFileStorage(JSONDbContext context) {
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

        repository.save(entity);
        return true;
    }

    @Override
    public boolean update(String name, Drive entity) {
        Optional<Drive> toUpdate = repository.findByKey(name);
        if (!toUpdate.isPresent())
            return false;

        referenceBuilder.buildReferences(entity);
        toUpdate.get().update(entity);
        repository.save(entity);
        return true;
    }

    @Override
    public boolean delete(String name) {
        Optional<Drive> entity = repository.findByKey(name);
        if (!entity.isPresent())
            return false;

        entity.get().detachVirtualMachine();

        repository.delete(entity.get());
        return true;
    }

}
