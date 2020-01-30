package domain.drive;

import api.drive.DriveDTO;
import domain.Model;
import domain.organization.Organization;
import domain.virtual_machine.VirtualMachine;

public class Drive implements Model<String> {

	public enum DriveType {SSD, HDD}

	private String name;
	private DriveType type;
	private int capacity;
	private VirtualMachine vm;
	private Organization organization;

	public Drive(String name) {
		this.name = name;
	}
	
	public Drive(String name, DriveType type, int capacity, VirtualMachine vm, Organization organization) {
		super();
		this.name = name;
		this.type = type;
		this.capacity = capacity;
		this.vm = vm;
		this.organization = organization;
	}

	public void removeReferences() {
		vm.removeDrive(this);
		organization.removeDrive(this);
	}

	public void update(Drive other) {
		if (other.name != null)
			this.name = other.name;

		if (other.type != null)
			this.type = other.type;

		if (other.vm != null)
			this.vm = other.vm;

		if(other.organization != null)
			this.organization = other.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Organization getOrganization() {
		return organization;
	}

	public DriveType getType() {
		return type;
	}

	public void setType(DriveType type) {
		this.type = type;
	}

	public String getTypeToString() { return this.type.toString(); }

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public VirtualMachine getVm() {
		return vm;
	}

	public void setVm(VirtualMachine vm) {
		this.vm = vm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Drive other = (Drive) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String getKey() {
		return this.name;
	}

}
