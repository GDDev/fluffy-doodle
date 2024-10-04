<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Employee"%>

<!DOCTYPE html>
<html lang="pt-br">
    <% Employee emp = (Employee) request.getAttribute("emp"); %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar <%=emp.getName()%></title>
    </head>
    <body>
        <header>
            <a href="./index.html"><h1>SHOP</h1></a>
        </header>
        
        <form action="GerenciarFuncionarios" method="POST">
            <input type="hidden" name="emp_id" value="<%=emp.getId()%>"/>
            <label for="emp_name">Nome:</label>
            <input type="text" id="emp_name" name="emp_name" value="<%=emp.getName()%>"/>
            
            <button type="submit" name="btn" value="UPDATEEMPLOYEE">Salvar</button>
        </form>
        <a href="?btn=FINDALL"><button type="submit">Cancelar</button></a>
    </body>
</html>
