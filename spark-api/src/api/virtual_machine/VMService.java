package api.virtual_machine;

import api.Service;
import domain.virtual_machine.VirtualMachine;
import storage.virtual_machine.VirtualMachineRepository;

public class VMService extends Service<String, VirtualMachine> {
    public VMService() {
        super(new VirtualMachineRepository("./data/vms.json"));
    }

}
