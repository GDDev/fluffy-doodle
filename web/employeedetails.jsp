<%@page import="java.text.ParseException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Employee"%>

<!DOCTYPE html>
<html lang="pt-br">
    <% Employee emp = (Employee) request.getAttribute("emp"); %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%if(emp != null){%><%=emp.getName()%><%}else{%>Não Encontrado<%}%></title>
    </head>
    <body>
        <header>
            <a href="./index.html"><h1>SHOP</h1></a>
        </header>
        
        <% if(emp != null){ %>
        
        <img src="<%=emp.getImage()%>" alt="<%=emp.getName().strip()%> photo"/>
        
        <form action="GerenciarFuncionarios" method="GET">
            <input type="hidden" name="emp_id" value="<%=emp.getId()%>"/>
            <label>Nome:</label>
            <input type="text" value="<%=emp.getName()%>" disabled/>
            
            <button type="submit" name="btn" value="EDITEMPLOYEE">Editar</button>
            <button type="submit" name="btn" value="DELETEEMPLOYEE">Deletar</button>
        </form>
        <%}else{%>
        <h3>Funcionário não encontrado.</h3>
        <%}%>
    </body>
</html>
