package storage.json_storage.virtual_machine;

import com.google.gson.stream.JsonReader;
import domain.virtual_machine.VirtualMachineStorage;
import domain.virtual_machine.VirtualMachine;
import storage.json_storage.JSONDbContext;
import storage.json_storage.JSONFileRepository;

import java.util.List;
import java.util.Optional;

public class VirtualMachineJSONFileStorage implements VirtualMachineStorage {
    private JSONFileRepository<String, VirtualMachine> repository;
    private VirtualMachineReferenceBuilder referenceBuilder;
    private JSONDbContext context;

    public VirtualMachineJSONFileStorage(JSONDbContext context) {
        this.context = context;
        repository = context.getVirtualMachinesRepository();
        referenceBuilder = context.getVirtualMachineReferenceBuilder();
    }

    @Override
    public List<VirtualMachine> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<VirtualMachine> findByName(String name) {
        return repository.findByKey(name);
    }

    @Override
    public boolean add(VirtualMachine entity) {
        if (repository.findByKey(entity.getKey()).isPresent())
            return false;

        referenceBuilder.buildReferences(entity);
        entity.buildReferences();
        repository.save(entity);
        context.saveDb();
        return true;
    }

    @Override
    public boolean update(String name, VirtualMachine entity) {
        Optional<VirtualMachine> toUpdate = repository.findByKey(name);
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
        Optional<VirtualMachine> entity = repository.findByKey(name);
        if (!entity.isPresent())
            return false;

        entity.get().removeReferences();
        repository.delete(entity.get());
        context.saveDb();
        return true;
    }

    @Override
    public boolean toggle(String name) {
        Optional<VirtualMachine> entity = repository.findByKey(name);
        if (!entity.isPresent())
            return false;
        entity.get().toggle();
        context.saveDb();
        return true;
    }


}
