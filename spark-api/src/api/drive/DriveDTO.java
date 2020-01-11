package api.drive;

import domain.drive.Drive;
import domain.virtual_machine.VirtualMachine;

public class DriveDTO {

    private String name;
    private Drive.DriveType type;
    private int capacity;
    private String vm;

    public Drive.DriveType getType() {
        return type;
    }

    public void setType(Drive.DriveType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getVm() {
        return vm;
    }

    public void setVm(String vm) {
        this.vm = vm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
