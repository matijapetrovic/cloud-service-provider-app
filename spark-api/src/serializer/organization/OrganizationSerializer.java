package serializer.organization;

import com.google.gson.GsonBuilder;
import models.Organization;
import serializer.JSONSerializer;

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
        // TODO resi
    }
}
