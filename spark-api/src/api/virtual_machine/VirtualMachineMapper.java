package api.virtual_machine;

import api.vm_category.CategoryDTO;
import api.vm_category.CategoryMapper;
import domain.drive.Drive;
import domain.organization.Organization;
import domain.virtual_machine.VirtualMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VirtualMachineMapper {
    public static VirtualMachineDTO toVirtualMachineDTO(VirtualMachine virtualMachine) {
        VirtualMachineDTO dto = new VirtualMachineDTO();
        dto.setName(virtualMachine.getName());

        CategoryDTO catDTO = CategoryMapper.toCategoryDTO(virtualMachine.getCategory());
        dto.setCategory(catDTO);
        dto.setDrives(new ArrayList<String>(virtualMachine
                .getDrives()
                .stream()
                .map(Drive::getName)
                .collect(Collectors.toList())
        ));
        dto.setOrganization(virtualMachine.getOrganization().getName());
        dto.setTurnedOn(virtualMachine.isTurnedOn());
        dto.setActivity(virtualMachine.getActivity());
        return dto;
    }

    public static List<VirtualMachineDTO> toVirtualMachineDTOList(List<VirtualMachine> virtualMachines) {
        return virtualMachines
                .stream()
                .map(VirtualMachineMapper::toVirtualMachineDTO)
                .collect(Collectors.toList());
    }

    public static VirtualMachine fromVirtualMachineDTO(VirtualMachineDTO dto) {
        return new VirtualMachine(
                dto.getName(),
                CategoryMapper.fromCategoryDTO(dto.getCategory()),
                new ArrayList<Drive>(
                        dto.getDrives()
                        .stream()
                        .map(Drive::new)
                        .collect(Collectors.toList())),
                new Organization(dto.getOrganization()),
                dto.isTurnedOn(),
                dto.getActivity());
    }
}
