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
import services.OrganizationService;
import services.UserService;
	
public class App {
    public static UserService userService;
    public static OrganizationService organizationService;
    
    public static final Gson g = new Gson();
    public static final Logger logger = Logger.getAnonymousLogger();
	
    public static void main(String[] args) throws IOException {
        userService = new UserService();
        organizationService = new OrganizationService();
    	
        port(8080);
        staticFiles.externalLocation(new File("./../vue-app").getCanonicalPath());
        
        path("/api", () -> {
            before("/*", (q, a) -> logger.log(Level.INFO, "Received API call: "+ q.requestMethod() + " " + q.uri()));
            path("/login", () -> {
                get("", LoginController.serveLoginPage);
                post("", LoginController.handleLoginPost);
            });
            path("/organizations", () -> {
                get("", OrganizationController.getAllOrganizations);
                post("/add", OrganizationController.postOrganization);
                put("/update/:name", OrganizationController.putOrganization);
            });
            path ("/users", () -> {
                get("", UserController.serveUserPage);
            });
        });
    }
}