package domain.virtual_machine;

import java.util.List;

public interface    VirtualMachineService {
    List<VirtualMachine> getAll();
    VirtualMachine getSingle(String name);
    VirtualMachine post(VirtualMachine virtualMachine);
    VirtualMachine put(String name, VirtualMachine virtualMachine);
    void delete(String name);
    VirtualMachine toggle(String name);
}
