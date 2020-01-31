package api.user;

import api.virtual_machine.VirtualMachineDTO;
import api.vm_category.CategoryMapper;
import domain.drive.Drive;
import domain.organization.Organization;
import domain.user.User;
import domain.virtual_machine.VirtualMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setRole(user.getRole());
        if (user.getRole() != User.Role.SUPER_ADMIN)
            dto.setOrganization(user.getOrganization().getName());

        return dto;
    }

    public static List<UserDTO> toUserDTOList(List<User> users) {
        return users
                .stream()
                .filter(x -> x.getRole() != User.Role.SUPER_ADMIN)
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public static User fromUserDTO(UserDTO dto) {
        return new User(
                dto.getEmail(),
                dto.getPassword(),
                dto.getName(),
                dto.getSurname(),
                new Organization(dto.getOrganization()),
                dto.getRole());
    }
}
