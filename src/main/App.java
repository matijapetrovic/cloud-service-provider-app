package main;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;

import controllers.IndexController;
import controllers.LoginController;
import services.UserService;
	
public class App {
	public static UserService userService;
	
    public static void main(String[] args) throws IOException {
    	userService = new UserService();
    	
        port(8080);
        
        staticFiles.externalLocation(new File("./WebContent").getCanonicalPath()); 
        	
        get("/", IndexController.serveIndexPage);
        get("/login", LoginController.serveLoginPage);
        post("/login", LoginController.handleLoginPost);
    }
}