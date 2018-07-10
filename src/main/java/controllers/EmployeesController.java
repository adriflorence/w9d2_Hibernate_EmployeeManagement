package controllers;


import db.Seeds;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class EmployeesController {

    public EmployeesController() {
        this.setupEndPoints();
    }

    private void setupEndPoints(){

    get("/employees", (req, res) -> {
        HashMap<String, Object> model = new HashMap<>();
        model.put("template", "templates/employees/index.vtl");
        return new ModelAndView(model, "templates/layout.vtl");
    }, new VelocityTemplateEngine());


    }
}
