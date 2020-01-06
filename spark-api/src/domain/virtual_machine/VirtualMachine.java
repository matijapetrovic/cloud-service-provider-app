package domain.virtual_machine;

import domain.drive.Drive;
import domain.Model;
import domain.vm_category.VMCategory;

import java.util.List;

public class VirtualMachine implements Model<String> {
	private String name;
	private VMCategory category;
	private List<Drive> drives;
	
	public VirtualMachine(String name, VMCategory category, List<Drive> drives) {
		super();
		this.name = name;
		this.category = category;
		this.drives = drives;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public VMCategory getCategory() {
		return category;
	}
	
	public void setCategory(VMCategory category) {
		this.category = category;
	}
	
	public int getCpus() {
		return category.getCpus();
	}
	
	public int getRam() {
		return category.getRam();
	}
	
	public int getGpus() {
		return category.getGpus();
	}
	
	public List<Drive> getDrives() {
		return drives;
	}
	
	public void setDrives(List<Drive> drives) {
		this.drives = drives;
	}

	public boolean addDrive(Drive drive) {
		if (drive == null)
			return false;

		if (drives.contains(drive))
			return false;

		return drives.add(drive);
	}

	public boolean removeDrive(Drive drive) {
		if (drive == null)
			return false;

		return drives.remove(drive);
	}

	public boolean updateDrive(String name, Drive drive) {
		if (drive == null)
			return false;

		int idx = drives.indexOf(drive);
		if (idx == -1)
			return false;

		drives.remove(idx);
		drives.add(idx, drive);
		return true;
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
		VirtualMachine other = (VirtualMachine) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	public String getKey() {
		return name;
	}
}