var edit = document.getElementById("editBtn");
var del = document.getElementById("deleteBtn");
var inputs = document.querySelectorAll('input[class="editInput"]');
var form = document.getElementById("empForm");
var btnDiv = document.getElementById("btnDiv");

var cancel = document.createElement("button");
cancel.type = "button";
cancel.value = "Cancel";
cancel.textContent = "Cancelar";
cancel.addEventListener("click", disableEdition);

var save = document.createElement("button");
save.type = "submit";
save.value = "Save";
save.textContent = "Salvar";

var inputEvent = new Event('blur');
const salaryInput = document.getElementById("salary");
salaryInput.addEventListener('blur', function (e) {
   validateSalary(e); 
});

function editButtons(){
    /* edit.value = isEditing ? "Save" : "Edit";;
    edit.textContent = isEditing ? "Salvar" : "Editar";
    edit.type = "submit"; */
    btnDiv.removeChild(edit);
    btnDiv.appendChild(save);
    
    btnDiv.removeChild(del);
    btnDiv.appendChild(cancel);
    form.action = "edit";
}
function defaultButtons(){
    /*edit.value = isEditing ? "Edit" : "Save";
    edit.textContent = isEditing ? "Editar" : "Salvar";
    edit.type = "button";*/
    btnDiv.removeChild(save);
    btnDiv.appendChild(edit);
    
    btnDiv.removeChild(cancel);
    btnDiv.appendChild(del);
    form.action = "delete";
}

function enableEdition(){
    
    inputs.forEach(
        function(input){
            input.disabled = false;
        }
    );
    editButtons();
}

function disableEdition(){
    inputs.forEach(
        function(input){
            input.disabled = true;
        }
    );
    defaultButtons();
}

function removeNewPhone(id){
    const div = document.getElementById(id);
    if (div){
        div.remove();
    }
}

function addNewPhone(){
    var container = document.getElementById("phones");
    var phoneCount = container.childElementCount;
    phoneCount++;

    const newPhoneDiv = document.createElement("div");
    newPhoneDiv.setAttribute("id", `phone-div${phoneCount}`);
    container.appendChild(newPhoneDiv);
    
    const newPhoneLabel = document.createElement("label");
    newPhoneLabel.setAttribute("for", `phone${phoneCount}`);
    newPhoneLabel.textContent = `Telefone ${phoneCount}:`;
    newPhoneDiv.appendChild(newPhoneLabel);
    
    const newPhoneInput = document.createElement("input");
    newPhoneInput.setAttribute("type", "tel");
    newPhoneInput.setAttribute("id", `phone${phoneCount}`);
    newPhoneInput.setAttribute("name", "phones[]");
    newPhoneInput.setAttribute("required", "required");
    newPhoneDiv.appendChild(newPhoneInput);
    
    const removeBtn = document.createElement("button");
    removeBtn.type = "button";
    removeBtn.value = "Remove";
    removeBtn.textContent = "Remover";
    removeBtn.onclick = function() {
        removeNewPhone(newPhoneDiv.id);
    };
    
    newPhoneDiv.appendChild(removeBtn);
}

function removeNewEmail(id){
    const div = document.getElementById(id);
    if (div){
        div.remove();
    }
}

function addNewEmail(){
    var container = document.getElementById("emails");
    var emailCount = container.childElementCount;
    emailCount++;

    const newEmailDiv = document.createElement("div");
    newEmailDiv.id = `email-div${emailCount}`;
    container.appendChild(newEmailDiv);
    
    const newEmailLabel = document.createElement("label");
    newEmailLabel.htmlFor = `email${emailCount}`;
    newEmailLabel.textContent = `E-mail ${emailCount}:`;
    newEmailDiv.appendChild(newEmailLabel);
    
    const newEmailInput = document.createElement("input");
    newEmailInput.type = "email";
    newEmailInput.id = `phone${emailCount}`;
    newEmailInput.name = "phones[]";
    newEmailInput.required = "required";
    newEmailDiv.appendChild(newEmailInput);
    
    const removeBtn = document.createElement("button");
    removeBtn.type = "button";
    removeBtn.value = "Remove";
    removeBtn.textContent = "Remover";
    removeBtn.onclick = function() {
        removeNewEmail(newEmailDiv.id);
    };
    
    newEmailDiv.appendChild(removeBtn);
}

function formatSalary(salary){
    var floatSalary = parseFloat(salary);
    const f = new Intl.NumberFormat("pt-br", {
        currency: "BRL",
        style: "currency",
        minimumFractionDigits: 2,
        maximumFractionDigits: 3
    });
    
    return !isNaN(floatSalary) ? f.format(floatSalary) : "";
}

function validateSalary(e){
    var value = e.target.value.replace(/[^\d,]/g, '');
    value = value.replace(',', '.');
    value = formatSalary(value);
    
    e.target.value = value;
}