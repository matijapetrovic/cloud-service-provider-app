package services;

import models.Model;
import models.VirtualMachine;
import repositories.VMRepository;

public class VMService extends Service<String, VirtualMachine> {
    public VMService() {
        super(new VMRepository("./data/vms.json"));
    }

}
