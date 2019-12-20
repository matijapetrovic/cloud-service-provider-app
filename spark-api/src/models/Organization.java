package models;

import java.util.Collection;
import java.util.HashSet;

public class Organization implements Model<String> {
	private String name;
	private String description;
	private String logo;
	private Collection<User> users;
	private Collection<VirtualMachine> resources;

	public Organization(String name, String description, String logo) {
		this.name = name;
		this.description = description;
		this.logo = logo;
		this.users = new HashSet<User>();
		this.resources = new HashSet<VirtualMachine>();
	}
	
	public Organization(String name, String description, String logo, Collection<User> users,
			Collection<VirtualMachine> resources) {
		super();
		this.name = name;
		this.description = description;
		this.logo = logo;
		this.users = users;
		this.resources = resources;
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

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Collection<VirtualMachine> getResources() {
		return resources;
	}

	public void setResources(Collection<VirtualMachine> resources) {
		this.resources = resources;
	}

	public boolean addUser(User u) {
		if (u == null)
			return false;

		return users.add(u);
	}

	public boolean addResource(VirtualMachine vm) {
		if (vm == null)
			return false;

		return resources.add(vm);
	}

	public boolean removeUser(User u) {
		if (u == null)
			return false;

		return users.remove(u);
	}

	public boolean removeResource(VirtualMachine vm) {
		if (vm == null)
			return false;

		return resources.remove(vm);
	}

	public boolean updateUser(String email, User u) {
		if (u == null)
			return false;

		if (!users.removeIf(x -> x.getEmail().equalsIgnoreCase(email)))
			return false;

		return users.add(u);
	}

	public boolean updateResource(String name, VirtualMachine vm) {
		if (vm == null)
			return false;

		if (!resources.removeIf(x -> x.getName().equalsIgnoreCase(name)))
			return false;

		return resources.add(vm);
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
