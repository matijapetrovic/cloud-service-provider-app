package api.organization;

import java.util.List;

public class OrganizationDTO {
    private String name;
    private String description;
    private String logo;
    private List<String> users;
    private List<String> virtualMachines;
    private List<String> drives;

    public List<String> getUsers() {
        return users;
    }

    public List<String> getVirtualMachines() {
        return virtualMachines;
    }

    public List<String> getDrives() {
        return drives;
    }

    public String getDescription() {
        return description;
    }

    public String getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDrives(List<String> drives) {
        this.drives = drives;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public void setVirtualMachines(List<String> virtualMachines) {
        this.virtualMachines = virtualMachines;
    }
}
