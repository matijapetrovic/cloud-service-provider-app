package api.virtual_machine;

import domain.drive.Drive;
import domain.virtual_machine.VirtualMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VirtualMachineMapper {
    public static VirtualMachineDTO toVirtualMachineDTO(VirtualMachine virtualMachine) {
        VirtualMachineDTO dto = new VirtualMachineDTO();
        dto.setName(virtualMachine.getName());
        dto.setCpus(virtualMachine.getCpus());
        dto.setRam(virtualMachine.getRam());
        dto.setGpus(virtualMachine.getGpus());
        dto.setDrives(new ArrayList<String>(virtualMachine
                .getDrives()
                .stream()
                .map(Drive::getName)
                .collect(Collectors.toList())
        ));
        return dto;
    }

    public static List<VirtualMachineDTO> toVirtualMachineDTOList(List<VirtualMachine> virtualMachines) {
        return virtualMachines
                .stream()
                .map(VirtualMachineMapper::toVirtualMachineDTO)
                .collect(Collectors.toList());
    }
}
