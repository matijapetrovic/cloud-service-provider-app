package api.vm_category;

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

    public static VMCategory fromCategoryDTO(CategoryDTO dto) {
        if (!validateDTO(dto))
            return null;
        return new VMCategory(
                dto.getName(),
                dto.getCpus(),
                dto.getRam(),
                dto.getGpus());
    }

    public static boolean validateDTO(CategoryDTO dto) {
        if (dto.getName() == null)
            return false;
        if (dto.getCpus() <= 0)
            return false;
        if (dto.getRam() <= 0)
            return false;
        return true;
    }
}
