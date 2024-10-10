package controller;

import dao.EmployeeDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employee;

public class EditEmployeeServlet extends HttpServlet {

    final EmployeeDAO empDAO = new EmployeeDAO();
    Employee emp = new Employee();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
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
                emp = new Employee(firstName, lastName, birthDate, jobTitle, hireDate, 
                salary, clockIn, clockOut, emp.getImage());
                emp.setId(id);

                empDAO.updateEmployee(emp);
                request.setAttribute("emp", empDAO.findEmployeeById(emp));                
            } else{
                System.out.println("Falha ao atualizar funcionário.");
            }
        } catch(SQLException | NumberFormatException e){
            System.out.println(e.getMessage());
        }
        finally{
            request.getRequestDispatcher("detail").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
