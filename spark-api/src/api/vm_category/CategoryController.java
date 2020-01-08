package api.vm_category;

import api.user.UserMapper;
import application.App;
import domain.user.User;
import domain.vm_category.CategoryService;
import domain.vm_category.CategoryStorage;
import domain.vm_category.VMCategory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

import static api.authentication.LoginController.ensureUserHasPermission;
import static spark.Spark.*;

public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryStorage storage) {
        this.service = new CategoryRESTService(storage);
        setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/categories", () -> {
                get("", handleGetAll);
                get("/:name", handleGetSingle);
                post("/add", handlePost);
                put("/update/:name", handlePut);
                delete("/delete/:name", handleDelete);
            });
        });
    }

    private Route handleGetAll = (Request request, Response response) -> {
        response.type("application/json");
        User currentUser = request.attribute("loggedIn");

        switch(currentUser.getRole()) {
            case SUPER_ADMIN:
                return App.g.toJson(CategoryMapper.toCategoryDTOList(service.getAll()));
            default:
                response.status(500);
                return "Something went wrong!";
        }
    };

    private Route handleGetSingle = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");

        response.type("application/json");
        response.status(200);
        return App.g.toJson(CategoryMapper.toCategoryDTO(service.getSingle(name)));
    };

    private Route handlePost = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.SUPER_ADMIN);

        VMCategory category = App.g.fromJson(request.body(), VMCategory.class);
        service.post(category);

        response.type("application/json");
        response.status(200);
        return "OK";
    };

    private Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        VMCategory category = App.g.fromJson(request.body(), VMCategory.class);
        String name = request.params(":email");

        service.put(name, category);
        response.type("application/json");
        response.status(200);
        return "OK";
    };

    private Route handleDelete = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        VMCategory category = App.g.fromJson(request.body(), VMCategory.class);
        String name = request.params(":name");

        service.delete(name);
        response.type("application/json");
        response.status(200);
        return "OK";
    };
}
