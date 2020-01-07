package storage.virtual_machine;

import domain.virtual_machine.VirtualMachine;
import storage.JSONFileRepository;
import storage.virtual_machine.serializing.VirtualMachineSerializer;

public class VirtualMachineRepository extends JSONFileRepository<String, VirtualMachine> {
    public VirtualMachineRepository(String filePath) {
        super(new VirtualMachineSerializer(), filePath);
    }
}