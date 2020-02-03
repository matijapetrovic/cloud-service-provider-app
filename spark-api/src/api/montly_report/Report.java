package api.montly_report;

import domain.drive.Drive;
import domain.organization.Organization;
import domain.virtual_machine.DateRange;
import domain.virtual_machine.VirtualMachine;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Report {
    private DateRange dateRange;

    private Map<VirtualMachine, Double> virtualMachinePrices = new LinkedHashMap<VirtualMachine, Double>();
    private Map<Drive, Double> drivePrices = new LinkedHashMap<Drive, Double>();

    public Report(Organization organization, DateRange dateRange) {
        this.dateRange = dateRange;
        calculateVirtualMachinePrices(organization.getVirtualMachines());
        calculateDrivePrices(organization.getDrives());
    }

    private void calculateVirtualMachinePrices(List<VirtualMachine> virtualMachines) {
        virtualMachines.forEach(this::calculateVirtualMachinePrice);
    }

    private void calculateVirtualMachinePrice(VirtualMachine virtualMachine) {
        virtualMachinePrices.put(virtualMachine, virtualMachine.getPrice(dateRange));
    }

    private void calculateDrivePrices(List<Drive> drives) {
        drives.forEach(this::calculateDrivePrice);
    }

    private void calculateDrivePrice(Drive drive) {
        drivePrices.put(drive, drive.getPrice(dateRange));
    }

    public Map<Drive, Double> getDrivePrices() {
        return drivePrices;
    }

    public Map<VirtualMachine, Double> getVirtualMachinePrices() {
        return virtualMachinePrices;
    }

    public void setDrivePrices(Map<Drive, Double> drivePrices) {
        this.drivePrices = drivePrices;
    }

    public void setVirtualMachinePrices(Map<VirtualMachine, Double> virtualMachinePrices) {
        this.virtualMachinePrices = virtualMachinePrices;
    }
}
