function togglePasswordVisibility() {
	const passwordField = document.getElementById('password');
	const toggleIcon = document.getElementById('togglePassword');

	if (passwordField.type === 'password') {
		passwordField.type = 'text';
		toggleIcon.classList.remove('fa-eye');
		toggleIcon.classList.add('fa-eye-slash');
	} else {
		passwordField.type = 'password';
		toggleIcon.classList.remove('fa-eye-slash');
		toggleIcon.classList.add('fa-eye');
	}
}

function showModal() {
    const modal = document.getElementById("messageModal");
	if (modal)
    	modal.style.display = "flex";
}

function closeModal() {
    const modal = document.getElementById("messageModal");
    modal.style.display = "none";
}

function formatPhone(input) {
    let phone = input.value.replace(/\D/g, '');
    
    if (phone.length <= 10) {
        phone = phone.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    } else {
        phone = phone.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    }
    
    input.value = phone;
    
    if (input.value.length > 15) {
        input.value = input.value.substring(0, 15); 
    }
}

function formatYear() {
	var yearInput = document.getElementById("ano-revista");
    var currentYear = new Date().getFullYear();
    var yearValue = yearInput.value;

    if (yearValue > currentYear)
        yearInput.value = currentYear;

    if (yearValue < 0)
        yearInput.value = 0;
}

document.addEventListener("DOMContentLoaded", () => {
	
	showModal();
	
	let yearInput = document.getElementById("ano-revista");
	if (yearInput)
		yearInput.addEventListener("input", formatYear);
});

const modal = document.getElementById("myModal");

const btn = document.querySelector(".floating-button");

const span = document.getElementsByClassName("close")[0];

btn.onclick = function() {
    modal.style.display = "block";
}

span.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}