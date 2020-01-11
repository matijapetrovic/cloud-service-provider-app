package api.drive;

import domain.drive.Drive;

import java.util.List;
import java.util.stream.Collectors;

public class DriveMapper {
    public static DriveDTO toDriveDTO(Drive drive) {
        DriveDTO dto = new DriveDTO();
        dto.setName(drive.getName());
        dto.setType(drive.getType());
        dto.setCapacity(drive.getCapacity());
        dto.setVm(drive.getVm().getName());

        return dto;
    }

    public static List<DriveDTO> toDriveDTOList(List<Drive> drives) {
        return drives
                .stream()
                .map(DriveMapper::toDriveDTO)
                .collect(Collectors.toList());
    }
}
