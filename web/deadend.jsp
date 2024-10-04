<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dead End</title>
    </head>
    <body>
        <h1>A página que você tentou acessar não existe.</h1>
        <% String url = "asdGHsMnDSLsdkp"; %>
        <h3>Too bad... Anyway, 
            <a href="<%=url%>?type=good">
                I'm feeling lucky.
            </a>
        </h3>
        <h5>Nevermind... I'm feeling down
            <a href="<%=url%>?type=bad">
                ...
            </a>
        </h5>
    </body>
</html>
