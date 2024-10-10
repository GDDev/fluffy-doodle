let currentStep = 0;
const steps = document.querySelectorAll('.step');

function showStep(stepIndex) {
    steps.forEach((step, index) => {
        if (index === stepIndex){
            step.classList.add("show");
            step.classList.remove("hide");
        }
        else{
            step.classList.add("hide");
            step.classList.remove("show");
        }
    });
}

function isStepFilled(){
    var inputs = steps[currentStep].querySelectorAll("input[required]");
    var isFilled = true;
    inputs.forEach(
        input => {
            const errorSpanId = `${input.id}-error`;
    
            if (!input.value.trim()){
                isFilled = false;
                input.classList.add("required");
                
                if (!document.getElementById(errorSpanId)) {
                    const errorSpan = document.createElement("span");
                    errorSpan.id = errorSpanId;
                    errorSpan.classList.add("required-message");
                    errorSpan.textContent = "*Campo obrigatório";
                    input.parentElement.insertBefore(errorSpan, input);
                }
            }
            else{
                input.classList.remove("required");
            }
        }
    );   
    return isFilled;
}

function nextStep() {
    if (isStepFilled()){
        if (currentStep < steps.length - 1) {
            currentStep++;
            showStep(currentStep);
        }
    } 
}

function previousStep() {
    if (currentStep > 0) {
        currentStep--;
        showStep(currentStep);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    showStep(currentStep);
});