async  function UserLogin() {

    const email = document.getElementById("email-login");
    const password = document.getElementById("password-login");

    if (!email.value.trim()) {
        erroeSwal("Error", "Email is required.", "error");
        return;
    } else if (!validateEmail(email.value.trim())) {
        erroeSwal("Error", "Invalid email format.", "error");
        return;
    } else if (!password.value.trim()) {
        erroeSwal("Error", "Password is required.", "error");
        return;
    } else if (!validatePassword(password.value.trim())) {
        erroeSwal("Error", "Password must be between 8-45 characters long, and include at least one digit, one uppercase letter, one lowercase letter, and one special character.", "error");
        return;
    }



    const user_dto = {
        email: email.value,
        password: password.value
    };
    console.log(JSON.stringify(user_dto));
    try {
        const response = await fetch("User_Login", {
            method: "POST",
            body: JSON.stringify(user_dto),
            headers: {"Content-Type": "application/json"}
        });
        if (response.ok) {
            const json = await response.json();
            console.log(json.content);
            if (json.status) {
                console.log("Login successful");
                if (json.content === "1") {
                    window.location = "new-user-varfiy.html";
                } else {
                    window.location = "index.html";
                }

            } else {
                erroeSwal("Error", json.content, "error");

            }
        } else {
            console.log("Error:", response.statusText);
        }

    } catch (error) {
        console.log("Fetch error:", error);
    }

    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(String(email).toLowerCase());
    }

    function validatePassword(password) {
        const re = /^(?=^.{8,45}$)(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9]).*$/;
        return re.test(password);
    }

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