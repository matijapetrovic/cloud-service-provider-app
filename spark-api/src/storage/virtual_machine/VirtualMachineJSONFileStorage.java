package storage.virtual_machine;

import domain.virtual_machine.VirtualMachineStorage;
import domain.virtual_machine.VirtualMachine;
import storage.JSONFileRepository;
import storage.virtual_machine.serializing.VirtualMachineSerializer;

import java.util.List;
import java.util.Optional;

public class VirtualMachineJSONFileStorage implements VirtualMachineStorage {
    private JSONFileRepository<String, VirtualMachine> repository;

    public VirtualMachineJSONFileStorage(String filePath) {
        repository = new JSONFileRepository<>(new VirtualMachineSerializer(), filePath);
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

        repository.save(entity);
        return true;
    }

    @Override
    public boolean update(String name, VirtualMachine entity) {
        repository.save(entity);
        return true;
    }

    @Override
    public boolean delete(String name) {
        Optional<VirtualMachine> entity = repository.findByKey(name);
        if (!entity.isPresent())
            return false;

        entity.get().detachDrives();

        repository.delete(entity.get());
        return true;
    }
}
