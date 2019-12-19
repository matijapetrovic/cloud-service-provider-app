package repositories;

import models.VirtualMachine;

import java.util.Optional;

public class VirtualMachineRepository extends JSONFileRepository<String, VirtualMachine> {

    public VirtualMachineRepository(String filePath) {
        super(filePath);
        loadEntities(VirtualMachine[].class);
    }

    @Override
    public Optional<VirtualMachine> findbyKey(String name) {
        return findAll()
                .stream()
                .filter(x -> x.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
