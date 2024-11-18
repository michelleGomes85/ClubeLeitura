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

document.addEventListener("DOMContentLoaded", () => {
    showModal();
});