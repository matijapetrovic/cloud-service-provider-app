package storage.virtual_machine;

import domain.virtual_machine.VirtualMachine;
import storage.JSONFileRepository;
import storage.virtual_machine.serializing.VMSerializer;

public class VMRepository extends JSONFileRepository<String, VirtualMachine> {
    public VMRepository(String filePath) {
        super(new VMSerializer(), filePath);
    }
}