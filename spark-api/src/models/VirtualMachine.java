package models;

import java.util.Collection;

public class VirtualMachine implements Model<String> {
	private String name;
	private VMCategory category;
	private Collection<Drive> drives;
	
	public VirtualMachine(String name, VMCategory category, Collection<Drive> drives) {
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
	
	public int getCores() {
		return category.getCores();
	}
	
	public int getMemory() {
		return category.getMemory();
	}
	
	public int getGpus() {
		return category.getGpus();
	}
	
	public Collection<Drive> getDrives() {
		return drives;
	}
	
	public void setDrives(Collection<Drive> drives) {
		this.drives = drives;
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
