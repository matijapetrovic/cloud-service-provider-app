package api.user;

import domain.user.User;

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
        dto.setOrganization(user.getOrganization().getName());

        return dto;
    }

    public static List<UserDTO> toUserDTOList(List<User> users) {
        return users
                .stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }
}
