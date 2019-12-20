package repositories;

import models.VirtualMachine;
import serializer.virtualmachine.VMSerializer;

public class VMRepository extends JSONFileRepository<String, VirtualMachine> {
    public VMRepository(String filePath) {
        super(new VMSerializer(), filePath);
    }
}