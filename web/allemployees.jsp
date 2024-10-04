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
        
        <script src="https://kit.fontawesome.com/5f236bbd73.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <header>
            <a href="./index.html"><h1>SHOP</h1></a>
        </header>
        
        <h1>Todos os Funcionários</h1>
        
        <form action="GerenciarFuncionarios">
            <span>Registrar Funcionário</span>
            <button type="submit" value="NEWEMPLOYEE" name="btn" style="font-size:14px">
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
                        <th>Editar</th>
                        <th>Deletar</th>
                    </tr> 
                </thead>
                <tbody>    
                    <% for(Employee emp : empList){ %>
                    <tr>
                        <td><img src="<%=emp.getImage()%>" alt="alt" height="20px" width="20px"/></td>
                        <td><%=emp.getId()%></td>
                        <td><%=emp.getName()%></td>
                        <form action="GerenciarFuncionarios" method="GET">
                            <td><button type="submit" name="btn" value="EDITEMPLOYEE"><i class="fa-solid fa-pencil"></i></button></td>
                            <td><button type="submit" name="btn" value="DELETEEMPLOYEE"><i class="fa-solid fa-trash"></i></button></td>
                            <input type="hidden" name="emp_id" value="<%=emp.getId()%>"/>
                        </form>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <% } %>
        </div>
    </body>
</html>
