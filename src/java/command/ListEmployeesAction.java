package command;

import dao.EmployeeDAO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employee;

public class ListEmployeesAction implements ICommand {
    
    final EmployeeDAO empDAO = new EmployeeDAO();
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String order = request.getParameter("sort");
        if (order == null || order.trim().equals("")){
            order = "id";
        }
        request.setAttribute("order", order);
        List<Employee> empList = empDAO.findAllEmployees(order);

        request.setAttribute("empList", empList);
        return "allemployees.jsp";
    }
}
