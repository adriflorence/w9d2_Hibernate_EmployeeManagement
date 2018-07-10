package controllers;

import db.DBHelper;
import models.Department;
import models.Engineer;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.List;

public class EngineersController {

    public EngineersController() {
        this.setupEndPoints();
    }

    private void setupEndPoints(){

        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

    get("/engineers", (req, res) -> {
        HashMap<String, Object> model = new HashMap<>();
        List<Engineer> engineers = DBHelper.getAll(Engineer.class);
        model.put("template", "templates/engineers/index.vtl");
        model.put("engineers", engineers);
        return new ModelAndView(model, "templates/layout.vtl");
    }, velocityTemplateEngine);


    get("/engineers/new", (req, res) -> {
        List<Engineer> engineers = DBHelper.getAll(Engineer.class);
        HashMap<String, Object> model = new HashMap<>();
        model.put("engineers", engineers);
        model.put("template", "templates/engineers/create.vtl");
        return new ModelAndView(model, "templates/layout.vtl");
    }, velocityTemplateEngine);


    // CREATE & SAVE NEW ENGINEER
    post("/engineers",  (req, res) -> {
        String firstName = req.queryParams("firstName");
        String lastName = req.queryParams("lastName");
        int salary = Integer.parseInt(req.queryParams("salary"));
        int department_id = Integer.parseInt(req.queryParams("department"));
        Department department = DBHelper.find(department_id, Department.class);

        Engineer newEngineer = new Engineer(firstName, lastName, salary, department);
        DBHelper.save(newEngineer);
        res.redirect("/engineers");
        return null;
    }, velocityTemplateEngine);

    // FETCH ENGINEER TO EDIT
    get("/engineers/:id", (req, res) -> {  // fetch manager to update
        int engineer_id = Integer.parseInt(req.params(":id"));
        Engineer engineerToUpdate = DBHelper.find(engineer_id, Engineer.class);

        List<Department> departments = DBHelper.getAll(Department.class);
        HashMap<String, Object> model = new HashMap<>();
        model.put("departments", departments);
        model.put("engineer", engineerToUpdate);
        model.put("template", "templates/engineers/update.vtl");
        return new ModelAndView(model, "templates/layout.vtl");
    }, velocityTemplateEngine);

    // UPDATE ENGINEER
    post("/engineers/:id", (req, res) -> { // update manager

        List<Engineer> engineers = DBHelper.getAll(Engineer.class);

        int engineer_id = Integer.parseInt(req.params(":id"));
        String newFirstName = req.queryParams("firstName");
        String newLastName = req.queryParams("lastName");
        int newSalary = Integer.parseInt(req.queryParams("salary"));
        int department_id = Integer.parseInt(req.queryParams("department"));

        Department department = DBHelper.find(department_id, Department.class);
        Engineer engineer = DBHelper.find(engineer_id, Engineer.class);
        engineer.setFirstName(newFirstName);
        engineer.setLastName(newLastName);
        engineer.setSalary(newSalary);
        engineer.setDepartment(department);

        DBHelper.update(engineer);
        res.redirect("/engineers");
        return null;
    });

    // DELETE ENGINEER
    post("/engineers/:id/delete", (req, res) -> {
        int id = Integer.parseInt(req.params(":id"));
        Engineer engineerToDelete = DBHelper.find(id, Engineer.class);
        DBHelper.delete(engineerToDelete);
        res.redirect("/engineers");
        return null;
    });
    }
}
