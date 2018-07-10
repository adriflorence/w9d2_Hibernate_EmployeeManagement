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

        // ADD DETAILS TO NEW DEPARTMENT
        get("/departments/new", (req, res) -> {
            List<Department> departments = DBHelper.getAll(Department.class);
            HashMap<String, Object> model = new HashMap<>();
            model.put("departments", departments);
            model.put("template", "templates/departments/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        // SAVE NEW DEPARTMENT
        post("/departments",  (req, res) -> {

            String title = req.queryParams("title");

            Department newDepartment = new Department(title);
            DBHelper.save(newDepartment);
            res.redirect("/departments");
            return null;

        }, velocityTemplateEngine);

        // FETCH DEPARTMENT TO EDIT
        get("/departments/:id", (req, res) -> {
            int department_id = Integer.parseInt(req.params("id"));
            Department departmentToUpdate = DBHelper.find(department_id, Department.class);

            HashMap<String, Object> model = new HashMap<>();
            model.put("department", departmentToUpdate);
            model.put("template", "templates/departments/update.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        // UPDATE DEPARTMENT
        post("/departments/:id", (req, res) -> {
            int department_id = Integer.parseInt(req.params(":id"));
            Department department = DBHelper.find(department_id, Department.class);

            String title = req.queryParams("title");
            department.setTitle(title);
            DBHelper.update(department);
            res.redirect("/departments");
            return null;
        });

        // DELETE DEPARTMENT
        post("/departments/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Department departmentToDelete = DBHelper.find(id, Department.class);
            DBHelper.delete(departmentToDelete);
            res.redirect("/departments");
            return null;
        });
    }

}
