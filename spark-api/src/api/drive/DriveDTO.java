package api.drive;

import domain.drive.Drive;

class DriveDTO {
        private String name;
        private Drive.DriveType type;
        private int capacity;
        private String vm;
        private String organization;

        public Drive.DriveType getType() {
            return type;
        }

    public void setType(Drive.DriveType type) {
            this.type = type;
            }

    public int getCapacity() {
            return capacity;
            }

    public void setCapacity(int capacity) {
            this.capacity = capacity;
            }

    public String getVm() {
            return vm;
            }

    public void setVm(String vm) {
            this.vm = vm;
            }

    public String getName() {
            return name;
        }

    public void setName(String name) {
            this.name = name;
        }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }
}
