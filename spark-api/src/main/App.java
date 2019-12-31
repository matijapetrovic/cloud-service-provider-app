package main;

import static spark.Spark.*;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

import controllers.LoginController;
import controllers.UserController;
import controllers.OrganizationController;
import controllers.VirtualMachineController;
import models.User;
import models.User.Role;
import services.OrganizationService;
import services.UserService;
import services.VMService;

public class App {
    public static final Gson g = new Gson();
    public static final Logger logger = Logger.getAnonymousLogger();

    public static UserService userService;
    public static VMService vmService;
    public static OrganizationService organizationService;

    static {
        userService = new UserService();
        vmService = new VMService();
        organizationService = new OrganizationService();
    }

    public static void main(String[] args) throws IOException {
        port(8080);
        staticFiles.externalLocation(new File("./../vue-app").getCanonicalPath());

        path("/api", () -> {
            before("/*", (q, a) -> logger.log(Level.INFO, "Received API call: "+ q.requestMethod() + " " + q.uri()));
            before("/*", LoginController::ensureUserIsLoggedIn);

            path("/login", () -> {
                post("", LoginController.handlePost);
            });

            path("/organizations", () -> {
                get("", OrganizationController.handleGetAll);
                get("/:name", OrganizationController.handleGetSingle);
                post("/add", OrganizationController.handlePost);
                put("/update/:name", OrganizationController.handlePut);
            });

            path ("/users", () -> {
                get("", UserController.serveUserPage);
                get("/currentUser", UserController.serveCurrentUser);
                get("/:name", UserController.handleGetSingle);
                get("/organizations/:name", UserController.handleUsersOrganization);
                post("/add", UserController.handlePost);
                put("/update/:name", UserController.handlePut);
            });

            path("/virtualmachines", () -> {
                get("", VirtualMachineController.handleGetAll);
                get("/:name", VirtualMachineController.handleGetSingle);
                post("/add", VirtualMachineController.handlePost);
                put("/update/:name", VirtualMachineController.handlePut);
            });
        });
    }
}