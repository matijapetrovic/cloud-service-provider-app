package models;

import java.util.ArrayList;
import java.util.List;

public class Organization implements Model<String> {
	private String name;
	private String description;
	private String logo;
	private List<User> users;
	private List<VirtualMachine> virtualMachines;
	private List<Drive> drives;

	public Organization(String name, String description, String logo) {
		this.name = name;
		this.description = description;
		this.logo = logo;
		this.users = new ArrayList<User>();
		this.virtualMachines = new ArrayList<VirtualMachine>();
	}
	
	public Organization(String name, String description, String logo, List<User> users,
			List<VirtualMachine> virtualMachines, List<Drive> drives) {
		super();
		this.name = name;
		this.description = description;
		this.logo = logo;
		this.users = users;
		this.virtualMachines = virtualMachines;
		this.drives = drives;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<VirtualMachine> getVirtualMachines() {
		return virtualMachines;
	}

	public void setVirtualMachines(List<VirtualMachine> virtualMachines) {
		this.virtualMachines = virtualMachines;
	}

	public List<Drive> getDrives() {
		return drives;
	}

	public void setDrives(List<Drive> drives) {
		this.drives = drives;
	}

	public boolean addUser(User u) {
		if (u == null)
			return false;

		if (users.contains(u))
			return false;

		return users.add(u);
	}

	public boolean addVirtualMachine(VirtualMachine vm) {
		if (vm == null)
			return false;

		if (virtualMachines.contains(vm))
			return false;

		return virtualMachines.add(vm);
	}

	public boolean addDrive(Drive drive) {
		if (drive == null)
			return false;

		if (drives.contains(drive))
			return false;

		return drives.add(drive);
	}

	public boolean removeUser(User u) {
		if (u == null)
			return false;

		return users.remove(u);
	}

	public boolean removeVirtualMachine(VirtualMachine vm) {
		if (vm == null)
			return false;

		return virtualMachines.remove(vm);
	}

	public boolean removeDrive(Drive drive) {
		if (drive == null)
			return false;

		return drives.remove(drive);
	}

	public boolean updateUser(String email, User u) {
		if (u == null)
			return false;

		int idx = users.indexOf(u);
		if (idx == -1)
			return false;

		users.remove(idx);
		users.add(idx, u);
		return true;
	}

	public boolean updateVirtualMachine(String name, VirtualMachine vm) {
		if (vm == null)
			return false;

		int idx = virtualMachines.indexOf(vm);
		if (idx == -1)
			return false;

		virtualMachines.remove(idx);
		virtualMachines.add(idx, vm);
		return true;
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
		Organization other = (Organization) obj;
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
