package api.drive;

import domain.drive.Drive;
import domain.organization.Organization;
import domain.virtual_machine.VirtualMachine;

import java.util.List;
import java.util.stream.Collectors;

class DriveMapper {
    public static DriveDTO toDriveDTO(Drive drive) {
        DriveDTO dto = new DriveDTO();
        dto.setName(drive.getName());
        dto.setType(drive.getType());
        dto.setCapacity(drive.getCapacity());
        dto.setVm(drive.getVm() != null ? drive.getVm().getName() : null);
        dto.setOrganization(drive.getOrganization().getName());

        return dto;
    }

    public static List<DriveDTO> toDriveDTOList(List<Drive> drives) {
        return drives
                .stream()
                .map(DriveMapper::toDriveDTO)
                .collect(Collectors.toList());
    }

    public static Drive fromDriveDTO(DriveDTO dto) {
        return new Drive(
                dto.getName(),
                dto.getType(),
                dto.getCapacity(),
                new VirtualMachine(dto.getVm()),
                new Organization(dto.getOrganization()));
    }
}
