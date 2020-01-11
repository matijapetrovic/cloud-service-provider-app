package api.virtual_machine;

import api.vm_category.CategoryDTO;

import java.util.List;

public class VirtualMachineDTO {
    private String name;
    private CategoryDTO category;
    private List<String> drives;
    private String organization;

    public VirtualMachineDTO() {

    }

    public List<String> getDrives() {
        return drives;
    }

    public String getName() {
        return name;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDrives(List<String> drives) {
        this.drives = drives;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
