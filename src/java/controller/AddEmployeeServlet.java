package controller;

import dao.EmployeeDAO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Email;
import model.Employee;
import model.Phone;
import model.WorkingDay;

@MultipartConfig
public class AddEmployeeServlet extends HttpServlet {

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
        response.sendRedirect("addemployee.jsp");
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
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            Date birthDate;
            birthDate = Date.valueOf(request.getParameter("birth_date"));

            String[] phonesStr = request.getParameterValues("phones[]");
            List<Phone> phones = new ArrayList<>();
            if(phonesStr != null){
                for (String phoneStr : phonesStr){ phones.add(new Phone(phoneStr)); }
            }
            
            String[] emailsStr = request.getParameterValues("emails[]");
            List<Email> emails = new ArrayList<>();
            if (emailsStr != null){
                for (String emailStr : emailsStr){ emails.add(new Email(emailStr)); }
            }
            
            String jobTitle = request.getParameter("job_title");
            Date hireDate = Date.valueOf(request.getParameter("hire_date"));
            
            String salaryStr = request.getParameter("salary");
            salaryStr = salaryStr.replaceAll("[^\\d\\,]", "").replace(",", ".");
            
            double salary = Double.parseDouble(salaryStr);
            LocalTime clockIn = LocalTime.parse(request.getParameter("clock_in"));
            LocalTime clockOut = LocalTime.parse(request.getParameter("clock_out"));
            
            String[] workingDaysStr = request.getParameterValues("work_days[]");
            List<WorkingDay> workingDays = new ArrayList<>();
            if (workingDaysStr != null){
                for (String dayStr : workingDaysStr) {
                    for (WorkingDay.WeekDay day : WorkingDay.WeekDay.values()){
                        if (dayStr.equals(day.getEnumName())){
                            workingDays.add(new WorkingDay(day));
                        }
                    }
                }
            }
            if(!firstName.isBlank() && !firstName.isEmpty() &&
                !lastName.isBlank() && !lastName.isEmpty() &&
                birthDate.before(Date.valueOf(LocalDate.now().minusYears(18))) &&
                !jobTitle.isBlank() && !jobTitle.isEmpty() &&
                salary > 0.0){
            
                Part filePart = request.getPart("emp_image");
                String fileName = filePart.getSubmittedFileName();
                String filePath = getServletContext().getRealPath("img") + File.separator + fileName;
                try (InputStream fileContent = filePart.getInputStream()) {
                    BufferedImage bi = ImageIO.read(fileContent);

                    String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

                    File outputfile = new File(filePath);
                    ImageIO.write(bi, ext, outputfile);

                    String imageStr = ".\\img"+File.separator+fileName;

                    emp = new Employee(firstName, lastName, birthDate, jobTitle, hireDate, 
                    salary, clockIn, clockOut, imageStr);
                    emp.setPhones(phones);
                    emp.setEmails(emails);
                    emp.setWorkingDays(workingDays);
                    empDAO.registerEmployee(emp);

                } catch (SQLException | ClassNotFoundException e) {
                    response.getWriter().println("Error saving file to database: " + e.getMessage());
                }
                response.sendRedirect("all");
            } else{
                System.out.println("Falha ao adicionar funcionário.");
            }
        } catch(IOException e){
            System.out.println("ERROR");
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
