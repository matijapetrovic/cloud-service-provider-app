package domain.user;

import domain.Model;
import domain.organization.Organization;
import domain.virtual_machine.VirtualMachine;

public class User  implements Model<String> {
	public enum Role
	{
		USER (0),
		ADMIN (1),
		SUPER_ADMIN(2);

		private int strength;

		Role(int strength) {
			this.strength = strength;
		}

		public boolean weakerThan(Role other) {
			return this.compare(other) < 0;
		}
		private int compare(Role other) {
			return this.strength - other.strength;
		}

	};
	private String email;
	private String password;
	private String name;
	private String surname;
	private Organization organization;
	private Role role;

	public User(String email) {
		this.email = email;
	}
	
	public User(String email, String password, String name, String surname, Organization organization, Role role) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.organization = organization;
		this.role = role;
	}

	public void update(User other) {
		if (other.email != null)
			this.email = other.email;

		if (other.name != null)
			this.name = other.name;

		if (other.surname != null)
			this.surname = other.surname;

		if (other.password != null)
			this.password = other.password;

		if (other.role != null)
			this.role = other.role;

		if (other.organization != null)
			this.organization = other.organization;
	}

	public void detachOrganization(){
		setOrganization(null);
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String getKey() {
		return email;
	}

}
