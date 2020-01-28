package api.virtual_machine;

import domain.virtual_machine.VirtualMachine;
import domain.virtual_machine.VirtualMachineService;
import domain.virtual_machine.VirtualMachineStorage;

import java.util.List;
import java.util.Optional;

import static spark.Spark.halt;

public class VirtualMachineRESTService implements VirtualMachineService {
    private VirtualMachineStorage storage;

    public VirtualMachineRESTService(VirtualMachineStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<VirtualMachine> getAll() {
        return (List<VirtualMachine>) storage.findAll();
    }

    @Override
    public VirtualMachine getSingle(String name) {
        Optional<VirtualMachine> virtualMachine = storage.findByName(name);
        if (!virtualMachine.isPresent())
            halt(404, "Virtual machine with the name "
                    + name + " not found");
        return virtualMachine.get();
    }

    @Override
    public VirtualMachine post(VirtualMachine virtualMachine) {
        if (!storage.add(virtualMachine))
            halt(409, "Virtual machine with the name "
                    + virtualMachine.getName() + " already exists");
        return storage.findByName(virtualMachine.getName()).orElse(null);
    }

    @Override
    public VirtualMachine put(String name, VirtualMachine virtualMachine) {
        if (!storage.update(name, virtualMachine))
            halt(404, "Virtual machine with the name "
                    + name + " not found");
        return storage.findByName(virtualMachine.getName()).orElse(null);
    }

    @Override
    public void delete(String name) {
        if (!storage.delete(name))
            halt(404, "Virtual machine with the name "
                    + name + " not found");
    }
}
