package api.virtual_machine;

import api.vm_category.CategoryDTO;
import domain.virtual_machine.DateRange;

import java.util.List;

public class VirtualMachineDTO {
    private String name;
    private CategoryDTO category;
    private List<String> drives;
    private String organization;
    private boolean turnedOn;
    private List<DateRange> activity;

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

    public boolean isTurnedOn() {
        return turnedOn;
    }

    public void setTurnedOn(boolean turnedOn) {
        this.turnedOn = turnedOn;
    }

    public List<DateRange> getActivity() {
        return activity;
    }

    public void setActivity(List<DateRange> activity) {
        this.activity = activity;
    }
}
