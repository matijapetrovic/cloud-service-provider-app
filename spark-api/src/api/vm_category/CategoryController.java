package api.vm_category;

import application.App;
import domain.user.User;
import domain.vm_category.CategoryService;
import domain.vm_category.CategoryStorage;
import domain.vm_category.VMCategory;
import spark.Request;
import spark.Response;
import spark.Route;


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

    // TODO MAYBE: dodati param names pa pustati sve ako je true, ako ne ne pustati
    private Route handleGetAll = (Request request, Response response) -> {
        response.type("application/json");
        return App.g.toJson(CategoryMapper.toCategoryDTOList(service.getAll()));
    };

    private Route handleGetSingle = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");

        response.status(200);
        return App.g.toJson(CategoryMapper.toCategoryDTO(service.getSingle(name)));
    };

    private Route handlePost = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.SUPER_ADMIN);

        VMCategory category = App.g.fromJson(request.body(), VMCategory.class);
        service.post(category);

        response.status(200);
        return "OK";
    };

    private Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        CategoryDTO category = App.g.fromJson(request.body(), CategoryDTO.class);
        String name = request.params(":email");

        service.put(name, CategoryMapper.fromCategoryDTO(category));
        response.status(200);
        return "OK";
    };

    private Route handleDelete = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        VMCategory category = App.g.fromJson(request.body(), VMCategory.class);
        String name = request.params(":name");

        service.delete(name);
        response.status(200);
        return "OK";
    };
}
