<%-- 
    Document   : index
    Created on : 4 de out. de 2024, 16:58:42
    Author     : GOD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <title>Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href="./css/style.css"/>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    </head>
    <body>
        <div>
            <jsp:include page="./components/header.jsp" />
            <main>
                <h1>BEM-VINDO</h1>                
                <form action="all" method="get">                  
                    <button type="submit" value="retrieveAll" name="allBtn">Ver Todos</button>
                </form>
            </main>
        </div>
        
        <script src="https://kit.fontawesome.com/5f236bbd73.js" crossorigin="anonymous"></script>
        <script src="./js/script.js"></script>
    </body>
</html>

