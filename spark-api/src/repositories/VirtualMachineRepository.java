package repositories;

import models.VirtualMachine;
import serializer.virtualmachine.VMSerializer;

import java.util.Optional;

public class VirtualMachineRepository extends JSONFileRepository<String, VirtualMachine> {

    public VirtualMachineRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected void setSerializer() {
        this.serializer = new VMSerializer();
    }

    @Override
    public Optional<VirtualMachine> findByKey(String name) {
        return findAll()
                .stream()
                .filter(x -> x.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
