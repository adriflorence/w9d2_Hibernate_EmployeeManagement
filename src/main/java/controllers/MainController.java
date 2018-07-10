package controllers;


import db.Seeds;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class MainController {

    //    ENTRY POINT
    public static void main(String[] args) {

        Seeds.seedData();

        staticFileLocation("/public");

        EmployeesController employeesController = new EmployeesController();
        ManagersController managersController = new ManagersController();
        DepartmentsController departmentsController = new DepartmentsController();
        EngineersController engineersController = new EngineersController();

        get("/home", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/home.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


    }
}
