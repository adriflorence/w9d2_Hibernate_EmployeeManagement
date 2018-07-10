package controllers;


import db.Seeds;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import static spark.Spark.get;

public class EmployeesController {

    //    ENTRY POINT
    public static void main(String[] args) {

        ManagersController managersController = new ManagersController();

        Seeds.seedData();

        get("/employees", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/employees/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


    }
}
