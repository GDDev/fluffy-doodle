package command;

import dao.EmployeeDAO;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employee;
import model.EmployeeBuilder;

public class EditEmployeeAction implements ICommand {
    
    final EmployeeDAO empDAO = new EmployeeDAO();
    Employee emp = new Employee();
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int id = Integer.parseInt(request.getParameter("emp_id"));
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");

        Date birthDate = Date.valueOf(request.getParameter("birth_date"));

        String jobTitle = request.getParameter("job_title");
        Date hireDate = Date.valueOf(request.getParameter("hire_date")); 

        String salaryStr = request.getParameter("salary");
        salaryStr = salaryStr.replaceAll("[^\\d\\,]", "").replace(",", ".");

        double salary = Double.parseDouble(salaryStr); 
        LocalTime clockIn = LocalTime.parse(request.getParameter("clock_in"));
        LocalTime clockOut = LocalTime.parse(request.getParameter("clock_out")); 

        if(!firstName.isBlank() && !firstName.isEmpty() &&
            !lastName.isBlank() && !lastName.isEmpty() &&
            birthDate.before(Date.valueOf(LocalDate.now().minusYears(18))) &&
            !jobTitle.isBlank() && !jobTitle.isEmpty() &&
            salary > 0.0){
            emp = new EmployeeBuilder()
                            .withName(firstName, lastName)
                            .withBirthDate(birthDate)
                            .withJobTitle(jobTitle)
                            .hiredAt(hireDate)
                            .withSalary(salary)
                            .clockInAt(clockIn)
                            .clockOutAt(clockOut)
                            .photoAtPath(emp.getImage())
                            .build();
            emp.setId(id);

            empDAO.updateEmployee(emp);
            request.setAttribute("emp", empDAO.findEmployeeById(emp));                
        } else{
            System.out.println("Falha ao atualizar funcionário.");
        }
        
        return "detail";
    }
}
