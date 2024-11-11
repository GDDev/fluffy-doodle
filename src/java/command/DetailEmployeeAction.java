package command;

import dao.EmployeeDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employee;

public class DetailEmployeeAction implements ICommand {
    
    final EmployeeDAO empDAO = new EmployeeDAO();
    Employee emp = new Employee();
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
        if (!request.getParameter("emp_id").isBlank() && !request.getParameter("emp_id").isEmpty()){
            int id = Integer.parseInt(request.getParameter("emp_id"));
            if (emp == null) emp = new Employee();
            emp.setId(id);
            emp = empDAO.findEmployeeById(emp);

            request.setAttribute("emp", emp);
            return "employeedetails.jsp";
        } else{
            // TODO change back to Dispatcher
            return "all";
        }
    }
}
