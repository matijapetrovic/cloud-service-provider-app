package storage.json_storage.organization.serializing;

import com.google.gson.GsonBuilder;
import application.App;
import domain.drive.Drive;
import domain.organization.Organization;
import domain.user.User;
import storage.json_storage.JSONSerializer;


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
        return data;
    }

}
