package application;

import static spark.Spark.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import api.authentication.LoginController;
import com.google.gson.Gson;

import api.drive.DriveController;
import api.drive.DriveService;
import api.organization.OrganizationController;
import api.user.UserController;
import api.virtual_machine.VMService;
import api.virtual_machine.VirtualMachineController;
import api.vm_category.CategoryController;
import api.vm_category.CategoryService;
import com.google.gson.GsonBuilder;
import domain.user.UserService;
import storage.organization.OrganizationJSONFileStorage;
import storage.user.UserJSONFileStorage;

public class App {
    public static final Gson g = new GsonBuilder().setPrettyPrinting().create();
    public static final Logger logger = Logger.getAnonymousLogger();

    public static UserService userService;
    public static VMService vmService;
    public static DriveService driveService;
    public static CategoryService categoryService;

    static {
        userService = new UserService();
        vmService = new VMService();
        driveService = new DriveService();
        categoryService = new CategoryService();
    }

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

//            path("/organizations", () -> {
//                get("", OrganizationController.handleGetAll);
//                get("/:name", OrganizationController.handleGetSingle);
//                post("/add", OrganizationController.handlePost);
//                put("/update/:name", OrganizationController.handlePut);
//            });

            path ("/users", () -> {
                get("", UserController.serveUserPage);
                get("/currentUser", UserController.serveCurrentUser);
                get("/:name", UserController.handleGetSingle);
                get("/organizations/:name", UserController.handleUsersOrganization);
                post("/add", UserController.handlePost);
                put("/update/:name", UserController.handlePut);
                delete("/delete/:name",UserController.handleDelete);
            });

            path("/virtualmachines", () -> {
                get("", VirtualMachineController.handleGetAll);
                get("/:name", VirtualMachineController.handleGetSingle);
                post("/add", VirtualMachineController.handlePost);
                put("/update/:name", VirtualMachineController.handlePut);
            });

            path ("/drives", () -> {
                get("", DriveController.handleGetAll);
                get("/:name", DriveController.handleGetSingle);
                get("/organizations/:name", DriveController.handleDrivesOrganization);
                post("/add", DriveController.handlePost);
                put("/update/:name", DriveController.handlePut);
                delete("/delete/:name",DriveController.handleDelete);
            });


            path ("/categories", () -> {
                get("", CategoryController.handleGetAll);
                get("/:name", CategoryController.handleGetSingle);
                post("/add", CategoryController.handlePost);
                put("/update/:name", CategoryController.handlePut);
                delete("/delete/:name",CategoryController.handleDelete);
            });
        });

        new OrganizationController(
                new OrganizationJSONFileStorage(
                        "./data/organizations.json"
                ));
        new UserController(
                new UserJSONFileStorage(
                        "./data/users.json"
                ));

        new DriveController(
                new DriveJSONFileStorage(
                    "./data/drives.json"
                ));

        }
}