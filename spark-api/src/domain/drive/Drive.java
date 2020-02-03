package domain.drive;

import domain.Model;
import domain.organization.Organization;
import domain.virtual_machine.DateRange;
import domain.virtual_machine.VirtualMachine;

public class Drive implements Model<String> {
	public enum DriveType {
		SSD(0.1), HDD(0.3);

		private double pricePerGB;

		DriveType(double pricePerGB) {
			this.pricePerGB = pricePerGB;
		}

		public double getPricePerGB() {
			return pricePerGB;
		}
	}

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

	public void buildReferences() {
		if (vm != null)
			vm.addDrive(this);
		organization.addDrive(this);
	}

	public void removeReferences() {
		if (vm != null)
			vm.removeDrive(this);
		organization.removeDrive(this);
	}

	public void update(Drive other) {
		removeReferences();
		if (other.name != null)
			this.name = other.name;

		if (other.type != null)
			this.type = other.type;

		if (other.vm != null)
			this.vm = other.vm;
		buildReferences();
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

	public double getPrice(DateRange dateRange) {
		final double MONTHLY_PRICE_PER_GB = type.getPricePerGB();
		final double HOURLY_PRICE_PER_GB = MONTHLY_PRICE_PER_GB / 30 / 24;
		final long HOURS = dateRange.getHours();

		return HOURLY_PRICE_PER_GB * HOURS;
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
