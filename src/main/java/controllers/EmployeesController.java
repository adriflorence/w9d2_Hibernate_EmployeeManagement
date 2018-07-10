package controllers;


import db.DBHelper;
import db.Seeds;
import models.Department;
import models.Employee;
import models.Engineer;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.staticFileLocation;

public class EmployeesController {

    public EmployeesController() {
        this.setupEndPoints();
    }

    private void setupEndPoints(){

        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

    get("/employees", (req, res) -> {
        HashMap<String, Object> model = new HashMap<>();
        List<Employee> employees = DBHelper.getAll(Employee.class);
        model.put("template", "templates/employees/index.vtl");
        model.put("employees", employees);
        return new ModelAndView(model, "templates/layout.vtl");
    }, new VelocityTemplateEngine());


    }
}
