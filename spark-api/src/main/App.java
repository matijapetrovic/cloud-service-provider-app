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
import models.Organization;
import models.VirtualMachine;
import services.OrganizationService;
import services.Service;
import services.UserService;
import services.VMService;

public class App {
    public static final Gson g = new Gson();
    public static final Logger logger = Logger.getAnonymousLogger();

    public static UserService userService = new UserService();
    public static OrganizationService organizationService = new OrganizationService();
    public static VMService vmService = new VMService();

    public static void main(String[] args) throws IOException {
        port(8080);
        staticFiles.externalLocation(new File("./../vue-app").getCanonicalPath());
        
        path("/api", () -> {
            before("/*", (q, a) -> logger.log(Level.INFO, "Received API call: "+ q.requestMethod() + " " + q.uri()));

            path("/login", () -> {
                get("", LoginController.serveLoginPage);
                post("", LoginController.handleLoginPost);
        });

            path("/organizations", () -> {
                get("", OrganizationController.handleGetAll);
                post("/add", OrganizationController.handlePost);
                put("/update/:name", OrganizationController.handlePut);
            });

            path ("/users", () -> {
                get("", UserController.serveUserPage);
            });

            path("/vms", () -> {
               get("", VirtualMachineController.handleGetAll);
               get(":name", VirtualMachineController.handleGetSingle);
               post("/add", VirtualMachineController.handlePost);
               put("/update/:name", VirtualMachineController.handlePut);
            });
        });
    }
}