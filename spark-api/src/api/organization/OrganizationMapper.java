package api.organization;

import domain.drive.Drive;
import domain.organization.Organization;
import domain.user.User;
import domain.virtual_machine.VirtualMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizationMapper {
    public static OrganizationDTO toOrganizationDTO(Organization organization) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setName(organization.getName());
        dto.setDescription(organization.getDescription());
        dto.setLogo(organization.getLogo());
        dto.setUsers(new ArrayList<String>(organization
                .getUsers()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList())
        ));
        dto.setDrives(new ArrayList<String>(organization
                .getDrives()
                .stream()
                .map(Drive::getName)
                .collect(Collectors.toList())
        ));
        dto.setVirtualMachines(new ArrayList<String>(organization
                .getVirtualMachines()
                .stream()
                .map(VirtualMachine::getName)
                .collect(Collectors.toList())
        ));
        return dto;
    }

    public static List<OrganizationDTO> toOrganizationDTOList(List<Organization> organizations) {
        return organizations
                .stream()
                .map(OrganizationMapper::toOrganizationDTO)
                .collect(Collectors.toList());
    }
}
