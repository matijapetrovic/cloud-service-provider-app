package api.virtual_machine;

import domain.virtual_machine.VirtualMachine;

import java.util.List;

public class VirtualMachineFilter {

    public static void filterByOrganization(String name, List<VirtualMachine> virtualMachines) {
        virtualMachines.removeIf(vm -> !vm.getOrganization().getName().contains(name));
    }

    public static void filterByName(String name, List<VirtualMachine> virtualMachines) {
        virtualMachines.removeIf(vm -> !vm.getName().contains(name));
    }

    public static void filterByCpuFrom(int cpuFrom, List<VirtualMachine> virtualMachines) {
        virtualMachines.removeIf(vm -> vm.getCpus() < cpuFrom);
    }

    public static void filterByCpuTo(int cpuTo, List<VirtualMachine> virtualMachines) {
        virtualMachines.removeIf(vm -> vm.getCpus() > cpuTo);
    }

    public static void filterByRamFrom(int ramFrom, List<VirtualMachine> virtualMachines) {
        virtualMachines.removeIf(vm -> vm.getRam() < ramFrom);
    }

    public static void filterByRamTo(int ramTo, List<VirtualMachine> virtualMachines) {
        virtualMachines.removeIf(vm -> vm.getRam() > ramTo);
    }

    public static void filterByGpuFrom(int gpuFrom, List<VirtualMachine> virtualMachines) {
        virtualMachines.removeIf(vm -> vm.getGpus() < gpuFrom);
    }

    public static void filterByGpuTo(int gpuTo, List<VirtualMachine> virtualMachines) {
        virtualMachines.removeIf(vm -> vm.getGpus() > gpuTo);
    }
}
