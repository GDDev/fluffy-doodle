package command;

import dao.EmployeeDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employee;

public class DeleteEmployeeAction implements ICommand {
    
    final EmployeeDAO empDAO = new EmployeeDAO();
    Employee emp = new Employee();
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int id = Integer.parseInt(request.getParameter("emp_id"));
        emp.setId(id);
        emp = empDAO.findEmployeeById(emp);
        empDAO.removeEmployee(emp);

        return "all";
    }
}
