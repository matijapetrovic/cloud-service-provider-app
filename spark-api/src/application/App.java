package application;

import static spark.Spark.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import api.authentication.LoginController;
import api.vm_category.CategoryController;
import com.google.gson.Gson;

import api.drive.DriveController;
import api.organization.OrganizationController;
import api.user.UserController;
import api.virtual_machine.VirtualMachineController;
import com.google.gson.GsonBuilder;
import storage.json_storage.JSONDbContext;
import storage.json_storage.organization.OrganizationJSONFileStorage;
import storage.json_storage.virtual_machine.VirtualMachineJSONFileStorage;
import storage.json_storage.drive.DriveJSONFileStorage;
import storage.json_storage.user.UserJSONFileStorage;

public class App {
    public static final Gson g = new GsonBuilder().setPrettyPrinting().create();
    public static final Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) throws IOException {
        port(8080);
        staticFiles.externalLocation(new File("./../vue-app").getCanonicalPath());

        path("/api", () -> {
            before("/*", (q, a) -> logger.log(Level.INFO, "Received API call: "+ q.requestMethod() + " " + q.uri()));
            before("/*", LoginController::ensureUserIsLoggedIn);
            after("/*", (q, a) -> a.type("application/json"));

            path("/login", () -> {
                post("", LoginController.handlePost);
            });
        });
        JSONDbContext dbContext = new JSONDbContext("data");
        new OrganizationController(
                new OrganizationJSONFileStorage(dbContext));

        new VirtualMachineController(
                new VirtualMachineJSONFileStorage(dbContext));

        new UserController(
                new UserJSONFileStorage(
                        "./data/users.json"
                ));
        new DriveController(
                new DriveJSONFileStorage(
                    "./data/drives.json"
                ));
//        new CategoryController(
//                new CategoryJSONFileStorage(dbContext));
    }
}