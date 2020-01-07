package storage.organization.serializing;

import com.google.gson.GsonBuilder;
import application.App;
import domain.organization.Organization;
import domain.user.User;
import domain.virtual_machine.VirtualMachine;
import storage.JSONSerializer;


import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class OrganizationSerializer extends JSONSerializer<Organization> {

    public OrganizationSerializer() {
        gson = new GsonBuilder()
                .addSerializationExclusionStrategy(
                        new OrganizationExclusionStrategy())
                .addDeserializationExclusionStrategy(
                        new OrganizationExclusionStrategy())
                .create();
    }

    @Override
    public void serialize(Collection<Organization> data, FileWriter writer) {
        gson.toJson(data, writer);
    }

    @Override
    public List<Organization> deserialize(FileReader reader) {
        List<Organization> data = Arrays.asList(gson.fromJson(reader, Organization[].class));
        buildReferences(data);
        return data;
    }

    private void buildReferences(List<Organization> data) {
        data.forEach(this::buildReferences);
    }

    private void buildReferences(Organization org) {
        buildUserReferences(org);
//        buildVMReferences(org);
//      buildDriveReferences(org);
    }

    private void buildUserReferences(Organization org) {
        List<User> users = new ArrayList<User>();
        org.getUsers()
                .forEach(x -> App.userService
                        .findByKey(x.getKey())
                        .ifPresent(users::add));
        org.setUsers(users);
    }

//    private void buildVMReferences(Organization org) {
//        List<VirtualMachine> vms = new ArrayList<VirtualMachine>();
//        org.getVirtualMachines()
//                .forEach(x -> App.vmService
//                        .findByKey(x.getKey())
//                        .ifPresent(vms::add));
//        org.setVirtualMachines(vms);
//    }

    private void buildDriveReferences(Organization org) {
        List<Drive> drives = new ArrayList<Drive>();
        org.getDrives()
                .forEach(x -> App.driveService
                        .findByKey(x.getKey())
                        .ifPresent(drives::add));
        org.setDrives(drives);
    }
}
