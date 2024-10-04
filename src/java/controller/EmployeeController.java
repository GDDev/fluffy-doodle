package controller;

import dao.EmployeeDAO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Employee;

@MultipartConfig
public class EmployeeController extends HttpServlet {
        
    final EmployeeDAO empDAO = new EmployeeDAO();
    Employee emp = new Employee();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String btnPressed = request.getParameter("btn");
            
            if (btnPressed != null) {
                switch (btnPressed) {
                    case "FINDALL" -> {
                        try {
                            String order = request.getParameter("sort");
                            request.setAttribute("order", order);
                            List<Employee> empList = empDAO.findAllEmployees(order);

                            request.setAttribute("empList", empList);
                            request.getRequestDispatcher("allemployees.jsp").forward(request, response);
                        } catch(SQLException e){
                            System.out.println("Erro ao buscar funcionários.");
                            request.getRequestDispatcher("index.html").forward(request, response);
                        }
                    }
                    case "FINDBYID" -> {
                        try {
                            if (!request.getParameter("emp_id").isBlank() && !request.getParameter("emp_id").isEmpty()){
                                int id = Integer.parseInt(request.getParameter("emp_id"));
                                emp.setId(id);
                                emp = empDAO.findEmployeeById(emp);
                                                                
                                request.setAttribute("emp", emp);
                                request.getRequestDispatcher("employeedetails.jsp").forward(request, response);
                            } else{
                                request.getRequestDispatcher("index.html").forward(request, response);
                            }
                        } catch(SQLException e){
                            request.getRequestDispatcher("index.html").forward(request, response);
                        }
                    }
                    case "NEWEMPLOYEE" -> request.getRequestDispatcher("addemployee.jsp").forward(request, response);
                    case "ADDEMPLOYEE" -> {
                        this.HandleEmployeeRegistration(request, response);
                    }
                    case "EDITEMPLOYEE" -> {
                        try {
                            int id = Integer.parseInt(request.getParameter("emp_id"));
                            emp.setId(id);
                            emp = empDAO.findEmployeeById(emp);
                            request.setAttribute("emp", emp);
                            request.getRequestDispatcher("editemployee.jsp").forward(request, response);
                        } catch(SQLException e){
                            System.out.println("ERROR");
                        }
                    }
                    case "UPDATEEMPLOYEE" -> {
                        try{
                            int id = Integer.parseInt(request.getParameter("emp_id"));
                            String name = request.getParameter("emp_name");
                            if(!name.isBlank() && !name.isEmpty()){
                                emp = new Employee(name, null);// TODO fix
                                emp.setId(id);

                                empDAO.updateEmployee(emp);
                                request.setAttribute("emp", emp);
                                request.getRequestDispatcher("employeedetails.jsp").forward(request, response);
                            } else{
                                out.println("O nome não pode ficar vazio.");
                            }
                        } catch(SQLException e){
                            System.out.println("ERROR");
                        }
                    }
                    case "DELETEEMPLOYEE" -> {
                        try{
                            int id = Integer.parseInt(request.getParameter("emp_id"));
                            emp.setId(id);
                            emp = empDAO.findEmployeeById(emp);
                            empDAO.deleteEmployee(emp);
                            response.setHeader("Refresh", "0; ?btn=FINDALL");
                        } catch(SQLException e){
                            System.out.println("ERROR");
                        }
                    }
                    default -> {
                        request.getRequestDispatcher("deadend.jsp").forward(request, response);
                    }
                }
            }
            request.getRequestDispatcher("deadend.jsp").forward(request, response);
        }
    }
    
    public void HandleEmployeeRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String name = request.getParameter("emp_name");

            Part filePart = request.getPart("emp_image");
            String fileName = filePart.getSubmittedFileName();
            String filePath = getServletContext().getRealPath("img") + File.separator + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
                BufferedImage bi = ImageIO.read(fileContent);

                String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

                File outputfile = new File(filePath);
                ImageIO.write(bi, ext, outputfile);

                emp = new Employee(name, ".\\img"+File.separator+fileName);
                empDAO.registerEmployee(emp);

            } catch (SQLException | ClassNotFoundException e) {
                response.getWriter().println("Error saving file to database: " + e.getMessage());
            }
            response.setHeader("Refresh", "0; ?btn=FINDALL");
        } catch(IOException e){
            System.out.println("ERROR");
        }
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
        processRequest(request, response);
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
