package domain.vm_category;

import domain.Model;

public class VMCategory implements Model<String> {
	private String name;
	private int cpus;
	private int ram;
	private int gpus;

	public VMCategory(String name) {
		this.name = name;
	}

	public VMCategory(String name, int cpus, int ram, int gpus) {
		super();
		this.name = name;
		this.cpus = cpus;
		this.ram = ram;
		this.gpus = gpus;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCpus() {
		return cpus;
	}
	
	public void setCpus(int cpus) {
		this.cpus = cpus;
	}
	
	public int getRam() {
		return ram;
	}
	
	public void setRam(int ram) {
		this.ram = ram;
	}
	
	public int getGpus() {
		return gpus;
	}
	
	public void setGpus(int gpus) {
		this.gpus = gpus;
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
		VMCategory other = (VMCategory) obj;
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
