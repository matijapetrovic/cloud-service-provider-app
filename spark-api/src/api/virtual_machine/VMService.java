package api.virtual_machine;

import api.Service;
import domain.virtual_machine.VirtualMachine;
import storage.virtual_machine.VMRepository;

public class VMService extends Service<String, VirtualMachine> {
    public VMService() {
        super(new VMRepository("./data/vms.json"));
    }

}
