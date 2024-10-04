<%@page import="extra.GoodQuotes"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>You Found Good Quotes</title>
        
        <link rel="stylesheet" href="./css/extra.css"/>
    </head>
    <body>
        <div class="container">
            <div class="good-quotes-container">
                <% 
                    GoodQuotes goodQuotes = new GoodQuotes();
                    String randomQuote = goodQuotes.getRandomQuote();
                %>
                <h1>Here, take a good quote. On the house :)</h1>          
                <h3><%=randomQuote%></h3>
            </div>
        </div>
    </body>
</html>
