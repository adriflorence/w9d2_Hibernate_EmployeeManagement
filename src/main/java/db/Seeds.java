package db;


import models.Department;
import models.Employee;
import models.Engineer;
import models.Manager;

public class Seeds {
    public static void seedData() {
        DBHelper.deleteAll(Engineer.class);
        DBHelper.deleteAll(Employee.class);
        DBHelper.deleteAll(Manager.class);
        DBHelper.deleteAll(Department.class);

        Department department1 = new Department("HR");
        DBHelper.save(department1);
        Department department2 = new Department("IT");
        DBHelper.save(department2);
        Manager manager1 = new Manager("Pete", "Whittle", 40000, department2, 100000 );
        DBHelper.save(manager1);
        Manager manager2 = new Manager("Derek", "Leach", 100, department2, 100000 );
        DBHelper.save(manager2);
        Manager manager3 = new Manager("Cleyra", "Uzcátegui", 5000, department2, 5000 );
        DBHelper.save(manager3);
        Engineer engineer1 = new Engineer("Tara", "McK", 32000, department1);
        DBHelper.save(engineer1);
        Engineer engineer2 = new Engineer("Jack", "Murning", 30000, department1);
        DBHelper.save(engineer2);
        Engineer engineer3 = new Engineer("Steven", "Bonner", 27500, department1);
        DBHelper.save(engineer3);
        Engineer engineer4 = new Engineer("Richard", "PK", 29000, department1);
        DBHelper.save(engineer4);
        Engineer engineer5 = new Engineer("Campbell", "Millar", 28000, department1);
        DBHelper.save(engineer5);
    }
}
