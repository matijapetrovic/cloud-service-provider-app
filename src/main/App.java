package main;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

import controllers.IndexController;
import controllers.LoginController;
import services.UserService;

public class App {
	public static UserService userService;
	
    public static void main(String[] args) {
        port(8080);
        
        staticFiles.location("/WebContent");
        
        get("/", IndexController.serveIndexPage);
        get("/login", LoginController.serveLoginPage);
    }
}