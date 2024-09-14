async  function UserVerfiy() {

    console.log("ok");
    const code = document.getElementById("code");
    if (!code.value.trim()) {

        erroeSwal("Error", "Enter your verification code!", "error");
        return;
    }

    const dto = {
        code: code.value
    };
    console.log(JSON.stringify(dto));
    const response = await fetch("User_Verfiy", {
        method: "POST",
        body: JSON.stringify(dto),
        headers: {"Content-Type": "application/json"}
    });
    if (response.ok) {
        const json = await response.json();
        console.log(json.content);
        if (json.status) {
            console.log("Verfiy successful");
            window.location = "index.html";
        } else {

            erroeSwal("Error", json.content, "error");
        }
    } else {
        console.log("Error:", response.statusText);
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


