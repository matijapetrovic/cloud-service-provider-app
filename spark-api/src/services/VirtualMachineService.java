package services;

import models.VirtualMachine;
import repositories.VirtualMachineRepository;

import java.util.Optional;

public class VirtualMachineService {
    private final VirtualMachineRepository repository = new VirtualMachineRepository("./data/organizations.json");

    public Iterable<VirtualMachine> findAll() {
        return repository.findAll();
    }

    public Optional<VirtualMachine> findByName(String name) {
        return repository.findbyKey(name);
    }

    public boolean add(VirtualMachine vm) {
        Optional<VirtualMachine> v = repository.findbyKey(vm.getName());
        if (v.isPresent())
            return false;

        repository.save(vm);
        return true;
    }

    public boolean update(String name, VirtualMachine vm) {
        Optional<VirtualMachine> v = repository.findbyKey(name);
        if (!v.isPresent())
            return false;

        repository.save(vm);
        return true;
    }

    public boolean delete(String name) {
        Optional<VirtualMachine> v = repository.findbyKey(name);
        if (!v.isPresent())
            return false;

        repository.delete(v.get());
        return true;
    }
}
