package controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.List;

public class DepartmentsController {

    public DepartmentsController() {
        this.setupEndPoints();
    }

    private void setupEndPoints(){

    VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/departments", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("template", "templates/departments/index.vtl");
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);
    }

}
