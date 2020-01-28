package storage.json_storage.user;

import domain.user.User;
import storage.json_storage.JSONDbContext;

public class UserReferenceBuilder {
    private JSONDbContext context;

    public UserReferenceBuilder(JSONDbContext context) {
        this.context = context;
    }

    public void buildReferences(User user) {
        buildOrganizationReferences(user);
    }

    private void buildOrganizationReferences(User user) {
        if (user.getRole() != User.Role.SUPER_ADMIN)
            user.setOrganization(context.getOrganizationsRepository()
                .findByKey(user
                        .getOrganization()
                        .getName())
                .orElse(null));
    }

}
