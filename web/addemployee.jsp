<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar Funcionário</title>
        
        <link rel="stylesheet" href="./css/style.css"/>
    </head>
    <body>
        <jsp:include page="./components/header.jsp" />
        
        <h1>Registrar Funcionário</h1>
        
        <form action="new" method="post" enctype="multipart/form-data">
            <div class="step show">
                <h2>Informações Pessoais</h2>
                <label for="emp_image">Foto</label>
                <input type="hidden" name="MAX_FILE_SIZE" value="99999999"/>
                <input type="file" id="emp_image" name="emp_image" required/>
                <label for="first_name">Nome:</label>
                <input type="text" id="first_name" name="first_name" required/>
                <label for="last_name">Sobrenome:</label>
                <input type="text" id="last_name" name="last_name" required/>
                <label for="birth_date">Data de Nascimento:</label>
                <input type="date" id="birth_date" name="birth_date" required/>
                <button type="button" onclick="nextStep()">Próximo</button>
            </div>
            <div class="step hide">
                <h2>Informações de Contato</h2>
                
                <div class="phone-container" id="phones">
                    <div>
                        <label for="phone1">Telefone:</label>
                        <input type="tel" id="phone1" name="phones[]" required/>
                    </div>
                </div>
                <button type="button" id="addPhoneBtn" onclick="addNewPhone()">Adicionar telefone</button>
                
                <div class="email-container" id="emails">
                    <div>
                        <label for="email1">E-mail:</label>
                        <input type="email" id="email1" name="emails[]" required/>
                    </div>
                </div>
                <button type="button" id="addEmailBtn" onclick="addNewEmail()">Adicionar e-mail</button>
                <button type="button" onclick="previousStep()">Anterior</button>
                <button type="button" onclick="nextStep()">Próximo</button> 
            </div>
            <div class="step hide">
                <h2>Informações de Contratação</h2>
                <label for="job_title">Cargo:</label>
                <input type="text" id="job_title" name="job_title" required/>
                <label for="hire_date">Data da Contratação:</label>
                <input type="date" id="hire_date" name="hire_date"/>
                <label for="salary">Salário:</label>
                <input type="text" inputmode="numeric" id="salary" name="salary" placeholder="R$ 0.000,00" required/>
                <label for="clock_in">Horário de Entrada:</label>
                <input type="time" id="clock_in" name="clock_in" required/>
                <label for="clock_out">Horário de Saída:</label>
                <input type="time" id="clock_out" name="clock_out"/>
                <button type="button" onclick="previousStep()">Anterior</button>
                <button type="button" onclick="nextStep()">Próximo</button>
            </div>
            
            <div class="step hide">
                <div>
                    <h2>Dias de Expediente</h2>
                    <span>Dias de Trabalho:</span><br>
                    <input type="checkbox" id="monday" name="work_days[]" value="Segunda-feira"/>
                    <label for="monday">Monday</label><br>

                    <input type="checkbox" id="tuesday" name="work_days[]" value="Terca-feira"/>
                    <label for="tuesday">Tuesday</label><br>

                    <input type="checkbox" id="wednesday" name="work_days[]" value="Quarta-feira"/>
                    <label for="wednesday">Wednesday</label><br>

                    <input type="checkbox" id="thursday" name="work_days[]" value="Quinta-feira"/>
                    <label for="thursday">Thursday</label><br>

                    <input type="checkbox" id="friday" name="work_days[]" value="Sexta-feira"/>
                    <label for="friday">Friday</label><br>

                    <input type="checkbox" id="saturday" name="work_days[]" value="Sabado"/>
                    <label for="saturday">Saturday</label><br>

                    <input type="checkbox" id="sunday" name="work_days[]" value="Domingo"/>
                    <label for="sunday">Sunday</label><br>
                    <button type="button" onclick="previousStep()">Anterior</button>
                    <button type="submit" name="addBtn" value="add">Cadastrar</button>
                </div>
            </div>
        </form>
        <a href="all"><button type="reset">Cancelar</button></a>
        
        <script src="https://kit.fontawesome.com/5f236bbd73.js" crossorigin="anonymous"></script>
        <script src="./js/script.js"></script>
        <script src="./js/employeeRegistration.js"></script>
    </body>
</html>
