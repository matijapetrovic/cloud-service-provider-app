package api.virtual_machine;

import java.util.List;

public class VirtualMachineDTO {
    private String name;
    private int cpus;
    private int ram;
    private int gpus;
    private List<String> drives;

    public VirtualMachineDTO() {

    }

    public List<String> getDrives() {
        return drives;
    }

    public String getName() {
        return name;
    }

    public int getGpus() {
        return gpus;
    }

    public int getCpus() {
        return cpus;
    }

    public int getRam() {
        return ram;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDrives(List<String> drives) {
        this.drives = drives;
    }

    public void setGpus(int gpus) {
        this.gpus = gpus;
    }

    public void setCpus(int cpus) {
        this.cpus = cpus;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }
}
