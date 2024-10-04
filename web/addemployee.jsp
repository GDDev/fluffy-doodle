<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Funcionário</title>
    </head>
    <body>
        <header>
            <a href="./index.html"><h1>SHOP</h1></a>
        </header>
        
        <h1>Registrar Funcionário</h1>
        
        <form action="GerenciarFuncionarios" method="post" enctype="multipart/form-data">
            <label for="emp_name">Nome:</label>
            <input type="text" id="emp_name" name="emp_name" required/>
            <label>Foto</label>
            <input type="hidden" name="MAX_FILE_SIZE" value="99999999"/>
            <input type="file" id="emp_image" name="emp_image" required/>
            
            <button type="submit" name="btn" value="ADDEMPLOYEE">Registrar</button>
        </form>
        <a href="?btn=FINDALL"><button type="submit">Cancelar</button></a>
    </body>
</html>
