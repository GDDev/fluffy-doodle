package command;

import dao.EmployeeDAO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Email;
import model.EmailBuilder;
import model.Employee;
import model.EmployeeBuilder;
import model.Phone;
import model.PhoneBuilder;
import model.WorkingDay;
import model.WorkingDayBuilder;

public class AddEmployeeAction implements ICommand{
    final EmployeeDAO empDAO = new EmployeeDAO();
    Employee emp = new Employee();
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        Date birthDate;
        birthDate = Date.valueOf(request.getParameter("birth_date"));

        String[] phonesStr = request.getParameterValues("phones[]");
        List<Phone> phones = new ArrayList<>();
        if(phonesStr != null){
            for (String phoneStr : phonesStr){ phones.add(
                    new PhoneBuilder()
                    .withNumber(phoneStr)
                    .build()
            ); }
        }

        String[] emailsStr = request.getParameterValues("emails[]");
        List<Email> emails = new ArrayList<>();
        if (emailsStr != null){
            for (String emailStr : emailsStr){ emails.add(
                    new EmailBuilder()
                    .withEmail(emailStr)
                    .build()
            ); }
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
                //for (WorkingDay.WeekDay day : WorkingDay.WeekDay.values()){
                //    if (dayStr.equals(day.getEnumName())){
                //        workingDays.add(new WorkingDay(day));
                //    }
                //}
                workingDays.add(
                    new WorkingDayBuilder()
                    .addDay(dayStr)
                    .build()
                );
            }
        }
        if(!firstName.isBlank() && !firstName.isEmpty() &&
            !lastName.isBlank() && !lastName.isEmpty() &&
            birthDate.before(Date.valueOf(LocalDate.now().minusYears(18))) &&
            !jobTitle.isBlank() && !jobTitle.isEmpty() &&
            salary > 0.0){

            Part filePart = request.getPart("emp_image");
            String fileName = filePart.getSubmittedFileName();
            String filePath = request.getAttribute("imgPath") + File.separator + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
                BufferedImage bi = ImageIO.read(fileContent);

                String ext = fileName.substring(fileName.lastIndexOf('.') + 1);

                File outputfile = new File(filePath);
                ImageIO.write(bi, ext, outputfile);

                String imageStr = ".\\img"+File.separator+fileName;

                emp = new EmployeeBuilder()
                            .withName(firstName, lastName)
                            .withBirthDate(birthDate)
                            .withJobTitle(jobTitle)
                            .hiredAt(hireDate)
                            .withSalary(salary)
                            .clockInAt(clockIn)
                            .clockOutAt(clockOut)
                            .photoAtPath(imageStr)
                            .build();
                emp.setPhones(phones);
                emp.setEmails(emails);
                emp.setWorkingDays(workingDays);
                empDAO.registerEmployee(emp);

            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Error saving file to database: " + e.getMessage());
            }

            return "all"; // May need to put the page name instead idk
        } else{
            System.out.println("Falha ao adicionar funcionário.");
        }
        return "new"; // May need to put the page name instead idk
    }
    
}
