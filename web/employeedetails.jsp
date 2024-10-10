<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.ParseException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Employee"%>
<%@page import="model.WorkingDay"%>
<%@page import="model.Phone"%>
<%@page import="model.Email"%>

<!DOCTYPE html>
<html lang="pt-br">
    <% Employee emp = (Employee) request.getAttribute("emp"); %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%if(emp != null){%><%=emp.getLastName()%><%}else{%>Não Encontrado<%}%></title>
        
        <link rel="stylesheet" href="./css/style.css"/>
    </head>
    <body>
        <jsp:include page="./components/header.jsp" />
        
        <% if(emp != null){ %>
        <div class="detail-employee-container">
            <main>
                <form id="empForm" action="delete" method="post">
                    <div class="top-container">
                        <div class="personal-flex-container">
                            <div class="profile-pic-container">
                                <img src="<%=emp.getImage()%>" alt="<%=emp.getFirstName().strip()%> photo"/>
                            </div>
                            <div class="personal-input-container">                         
                                <input type="hidden" name="emp_id" value="<%=emp.getId()%>"/>
                                <div class="input-container">
                                    <label for="first_name">Nome:</label>
                                    <input id="first_name" name="first_name" class="editInput" type="text" value="<%=emp.getFirstName()%>" disabled/>
                                </div>
                                <div class="input-container">
                                    <label for="last_name">Sobrenome:</label>
                                    <input id="last_name" name="last_name" class="editInput" type="text" value="<%=emp.getLastName()%>" disabled/>
                                </div>
                                <div class="input-container">
                                    <label for="birth_date">Data de nascimento:</label>
                                    <input id="birth_date" name="birth_date" class="editInput" type="date" value="<%=emp.getBirthDate()%>" disabled/>
                                </div>
                            </div>
                        </div>
                        <div class="job-info-container">
                            <div class="input-container">
                                <label for="job_title">Cargo:</label>
                                <input id="job_title" name="job_title" class="editInput" type="text" value="<%=emp.getJobTitle()%>" disabled/>
                            </div>
                            <div class="input-container">
                                <label for="hire_date">Contratado em:</label>
                                <input id="hire_date" name="hire_date" class="editInput" type="date" value="<%=emp.getHireDate()%>" disabled/>
                            </div>
                            <div class="input-container">
                                <label for="salary">Salário:</label>
                                <input id="salary" name="salary" inputmode="numeric" class="editInput" value="<%=emp.getSalary()%>" type="text" disabled/>
                            </div>
                            <div class="input-container">
                                <label for="clock_in">Horário de entrada:</label>
                                <input id="clock_in" name="clock_in" class="editInput" type="time" value="<%=emp.getClockIn()%>" disabled/>
                            </div>
                            <div class="input-container">
                                <label for="clock_out">Horário de saída:</label>
                                <input id="clock_out" name="clock_out" class="editInput" type="time" value="<%=emp.getClockOut()%>" disabled/>
                            </div>
                        </div>
                    </div>
                    <div class="middle-container">
                        <div class="middle-left-container">
                            <div class="days-info-container">
                                <% 
                                    for (WorkingDay wd : emp.getWorkingDays()){
                                        String dayName = wd.getWeekDay().getEnumName();
                                        String inputValue = wd.getWeekDay().toString().toLowerCase();
                                %>
                                <input type="checkbox" id="<%=inputValue%>" name="work_days[]" value="<%=dayName%>" checked disabled/>
                                <label for="<%=inputValue%>"><%=dayName%></label><br>                        
                                <% } %>
                            </div>
                        </div>
                        <div class="middle-middle-container">                         
                            <% 
                                int phoneI = 0;
                                for (Phone phone : emp.getPhones()){
                                    phoneI++;
                                    String number = phone.getNumber();
                            %>
                            <div class="phone-container" id="phones">
                                <label for="phone<%=phoneI%>">Telefone <%=phoneI%>:</label><br>
                                <input type="tel" id="phone<%=phoneI%>" name="phones[]" value="<%=number%>" disabled/>
                            </div>
                            <% } %>
                        </div>
                        <div class="middle-right-container">
                            <div class="email-container" id ="emails">
                                <%
                                    int emailI = 0;
                                    for (Email email : emp.getEmails()){
                                        emailI++;
                                        String emailStr = email.getEmail();
                                %>
                                <label for="email<%=emailI%>">E-mail <%=emailI%>:</label><br/>
                                <input type="email" id="email<%=emailI%>" name="emails[]" value="<%=emailStr%>" disabled/>
                                <% } %>
                            </div>
                        </div>
                    </div>
                    <div id="btnDiv" class="bottom-container">
                        <button type="button" id="editBtn" value="Edit" onclick="enableEdition()">Editar</button>
                        <button type="submit" id="deleteBtn" value="Delete">Deletar</button>
                    </div>
                </form>
            </main>
        </div>
        <%}else{%>
        <h3>Funcionário não encontrado.</h3>
        <%}%>
        
        <script src="https://kit.fontawesome.com/5f236bbd73.js" crossorigin="anonymous"></script>
        <script src="./js/script.js"></script>
    </body>
</html>
