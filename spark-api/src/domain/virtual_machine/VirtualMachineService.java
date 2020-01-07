package domain.virtual_machine;

import java.util.List;

public interface VirtualMachineService {
    List<VirtualMachine> getAll();
    VirtualMachine getSingle(String name);
    void post(VirtualMachine virtualMachine);
    void put(String name, VirtualMachine virtualMachine);
    void delete(String name);
}
