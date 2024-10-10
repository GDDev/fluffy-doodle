package controller;

import dao.EmployeeDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employee;

public class DetailEmployeeServlet extends HttpServlet {

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
        try {
            if (!request.getParameter("emp_id").isBlank() && !request.getParameter("emp_id").isEmpty()){
                int id = Integer.parseInt(request.getParameter("emp_id"));
                if (emp == null) emp = new Employee();
                emp.setId(id);
                emp = empDAO.findEmployeeById(emp);

                request.setAttribute("emp", emp);
                request.getRequestDispatcher("employeedetails.jsp").forward(request, response);
            } else{
                response.sendRedirect("all");
            }
        } catch(SQLException e){
            response.sendRedirect("all");
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
