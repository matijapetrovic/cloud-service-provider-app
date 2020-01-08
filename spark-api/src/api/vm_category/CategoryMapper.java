package api.vm_category;

import api.user.UserDTO;
import api.user.UserMapper;
import domain.user.User;
import domain.vm_category.VMCategory;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryDTO toCategoryDTO(VMCategory category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCpus(category.getCpus());
        dto.setRam(category.getRam());
        dto.setGpus(category.getGpus());
        dto.setName(category.getName());

        return dto;
    }

    public static List<CategoryDTO> toCategoryDTOList(List<VMCategory> categories) {
        return categories
                .stream()
                .map(CategoryMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }
}
