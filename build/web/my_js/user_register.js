async function UserRegister() {

    const first_name = document.getElementById("first-name-login");
    const last_name = document.getElementById("last-name-login");
    const email = document.getElementById("email-login");
    const password = document.getElementById("password-login");
    const agree = document.getElementById("agree");
    if (!first_name.value.trim()) {

        erroeSwal("Error", "First name is required.", "error");
    } else if (!last_name.value.trim()) {
        erroeSwal("Error", "Last name is required.", "error");

    } else if (!email.value.trim()) {
        erroeSwal("Error", "Invalid email format.", "error");
    } else if (!validateEmail(email.value.trim())) {
        erroeSwal("Error", "Email is required.", "error");
    } else if (!password.value.trim()) {
        erroeSwal("Error", "Password is required.", "error");
    } else if (!validatePassword(password.value.trim())) {
        erroeSwal("Error", "Password must be between 8-45 characters long, and include at least one digit, one uppercase letter, one lowercase letter, and one special character.", "error");
    } else if (!agree.checked) {
        erroeSwal("Error", "Please agree to the Terms & Conditions.", "error");
    } else {

        const user_dto = {
            first_name: first_name.value,
            last_name: last_name.value,
            email: email.value,
            password: password.value
        };
        console.log(JSON.stringify(user_dto));

        const response = await fetch("User_Register", {
            method: "POST",
            body: JSON.stringify(user_dto),
            headers: {"Content-Type": "application/json"}
        });

        if (response.ok) {
            const json = await response.json();
            console.log(json.content);
            if (json.status) {
                console.log("Registration successful");
                window.location = "new-user-varfiy.html";
            } else {
                erroeSwal("Error", json.content, "error");


            }
        } else {
            console.log("Error:", response.statusText);
        }

    }


}

function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(String(email).toLowerCase());
}

function validatePassword(password) {
    const re = /^(?=^.{8,45}$)(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9]).*$/;
    return re.test(password);
}


function erroeSwal(title, text, icon) {
    Swal.fire({
        title: title,
        text: text,
        icon: icon,
        showConfirmButton: false,
        timer: 1500
    });
}