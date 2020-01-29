package domain.virtual_machine;

import domain.drive.Drive;
import domain.Model;
import domain.organization.Organization;
import domain.vm_category.VMCategory;

import java.util.Date;
import java.util.List;

public class VirtualMachine implements Model<String> {
	private String name;
	private VMCategory category;
	private List<Drive> drives;
	private Organization organization;
	private boolean turnedOn;
	private List<DateRange> activity;

	public VirtualMachine(String name) {
		this.name = name;
	}

	public VirtualMachine(String name, VMCategory category, List<Drive> drives,
						  Organization organization, boolean turnedOn, List<DateRange> activity) {
		super();
		this.name = name;
		this.category = category;
		this.drives = drives;
		this.organization = organization;
		this.turnedOn = turnedOn;
		this.activity = activity;
	}

	public void update(VirtualMachine other) {
		if (other.name != null)
			this.name = other.name;

		if (other.category != null)
			this.category = other.category;

		if (other.drives != null)
			this.drives = other.drives;

		if (other.organization != null)
			this.organization = other.organization;

		if (other.activity != null)
			this.activity = other.activity;
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

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<DateRange> getActivity() {
		return activity;
	}

	public void setActivity(List<DateRange> activity) {
		this.activity = activity;
	}

	public boolean isTurnedOn() {
		return turnedOn;
	}

	public void toggle() {
		turnedOn = !turnedOn;
		if (turnedOn)
			activity.add(new DateRange(new Date()));
		else
			activity.get(activity.size() - 1).setEndDate(new Date());
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

	public void updateDrive(int idx, Drive drive) {
		if (drive == null)
			return;
		drives.set(idx, drive);
	}

	public void detachDrives() {
		drives.forEach(drive -> drive.setVm(null));
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
