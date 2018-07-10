package controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.post;

import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;


public class ManagersController {

    public ManagersController() {
        this.setupEndPoints();
    }

    private void setupEndPoints(){

        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/managers", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("template", "templates/managers/index.vtl");
            model.put("managers", managers);
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        get("/managers/new", (req, res) -> {
            List<Department> departments = DBHelper.getAll(Department.class);
            HashMap<String, Object> model = new HashMap<>();
            model.put("departments", departments);
            model.put("template", "templates/managers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        // CREATE & SAVE NEW MANAGER
        post("/managers",  (req, res) -> {

            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            int department_id = Integer.parseInt(req.queryParams("department"));
            double budget = Double.parseDouble(req.queryParams("budget"));

            Department department = DBHelper.find(department_id, Department.class);

            Manager newManager = new Manager(firstName, lastName, salary, department, budget);
            DBHelper.save(newManager);
            res.redirect("/managers");
            return null;

        }, velocityTemplateEngine);

        // FETCH MANAGER TO EDIT
        get("/managers/:id", (req, res) -> {  // fetch manager to update
            int manager_id = Integer.parseInt(req.params("id"));
            Manager managerToUpdate = DBHelper.find(manager_id, Manager.class);

            List<Department> departments = DBHelper.getAll(Department.class);
            HashMap<String, Object> model = new HashMap<>();
            model.put("departments", departments);
            model.put("manager", managerToUpdate);
            model.put("template", "templates/managers/update.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);


        // UPDATE MANAGER
        post("/managers/:id", (req, res) -> { // update manager

            List<Manager> managers = DBHelper.getAll(Manager.class);

            int manager_id = Integer.parseInt(req.params(":id"));
            String newFirstName = req.queryParams("firstName");
            String newLastName = req.queryParams("lastName");
            int newSalary = Integer.parseInt(req.queryParams("salary"));
            int department_id = Integer.parseInt(req.queryParams("department"));
            double newBudget = Double.parseDouble(req.queryParams("budget"));

            Department department = DBHelper.find(department_id, Department.class);
            Manager manager = DBHelper.find(manager_id, Manager.class);
            manager.setFirstName(newFirstName);
            manager.setLastName(newLastName);
            manager.setSalary(newSalary);
            manager.setDepartment(department);
            manager.setBudget(newBudget);

            DBHelper.update(manager);
            res.redirect("/managers");
            return null;
        });

        // DELETE MANAGER
        post("/managers/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Manager managerToDelete = DBHelper.find(id, Manager.class);
            DBHelper.delete(managerToDelete);
            res.redirect("/managers");
            return null;
        });
    }


}
