<%@page import="java.io.File"%>
<%@page import="java.awt.Image"%>
<%@page import="java.awt.image.RenderedImage"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.Employee"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Todos os Funcionários</title>
        
        <link rel="stylesheet" href="./css/style.css"/>
    </head>
    <body>
        <jsp:include page="./components/header.jsp" />
                
        <h1>Listagem de Funcionários</h1>
        
        <form action="new">
            <span>Registrar Funcionário</span>
            <button type="submit" value="newEmp" name="newBtn" style="font-size:14px">
                <i class='fas fa-plus-circle'></i>
            </button>
        </form>
        
        <div class="table-container">
            <%List<Employee> empList = (List<Employee>) request.getAttribute("empList");%>
            <% if(empList.isEmpty()){ %>
            <h3>Nenhum funcionário cadastrado</h3>
            <% } else{ %>
            <form action="?btn=FINDALL" method="POST">
                <label for="sort"><span>Ordenar por</span></label>
                <select id="sort" name="sort">
                    <option name="sort_opt" value="emp_name">Nome</option>
                    <option name="sort_opt" value="id">Registro</option>
                </select>
                
                <button type="submit">Ordenar</button>
            </form>
            <table>
                <thead>
                    <tr>
                        <th>FOTO</th>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Cargo</th>
                        <th>Editar</th>
                        <th>Deletar</th>
                    </tr> 
                </thead>
                <tbody>    
                    <% 
                        int rowIndex = 0;
                        for(Employee emp : empList){ 
                    %>
                    <tr id="row<%= rowIndex%>">
                        <td><img src="<%=emp.getImage()%>" alt="alt" height="20px" width="20px"/></td>
                        <td><%=emp.getId()%></td>
                        <% String name = emp.getFirstName() + " " + emp.getLastName(); %>
                        <td><%=name%></td>
                        <td><%=emp.getJobTitle()%></td>
                        <form action="detail" method="post">
                            <td><button type="submit" name="btn" value="detail"><i class="fa-solid fa-pencil"></i></button></td>
                            <input type="hidden" name="emp_id" value="<%=emp.getId()%>"/>
                        </form>
                        <form action="delete" method="post">
                            <td><button type="submit"><i class="fa-solid fa-trash"></i></button></td>
                            <input type="hidden" name="emp_id" value="<%=emp.getId()%>"/>
                        </form>
                    </tr>
                    <% 
                        rowIndex++;
                        } 
                    %>
                </tbody>
            </table>
            <% } %>
        </div>
        
        <script src="https://kit.fontawesome.com/5f236bbd73.js" crossorigin="anonymous"></script>
        <script src="./js/script.js"></script>
    </body>
</html>
