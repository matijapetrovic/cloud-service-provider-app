package services;

import models.VirtualMachine;
import repositories.JSONFileRepository;
import repositories.VMRepository;

public class VMService extends Service<String, VirtualMachine> {
    public VMService() {
        super(new VMRepository("./data/vms.json"));
    }
}
