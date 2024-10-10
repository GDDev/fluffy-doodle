<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="searchbar">
    <form action="detail" method="post">
        <input type="text" id="emp_id" name="emp_id" placeholder="Pesquisar funcionário por ID" required/>
        <button type="submit" value="findEmp" name="btn"><i class="fa-solid fa-magnifying-glass"></i></button>
    </form>
</div>