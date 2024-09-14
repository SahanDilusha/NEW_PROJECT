async  function AddToCart(id) {

    const qty = document.getElementById("qty-cart");

    const dto = {
        qty: qty.innerHTML,
        pid: id
    };
    console.log(JSON.stringify(dto));
    try {
        const response = await fetch("AddToCart", {
            method: "POST",
            body: JSON.stringify(dto),
            headers: {"Content-Type": "application/json"}
        });
        if (response.ok) {
            const json = await response.json();
            console.log(json.content);
            if (json.status) {
                erroeSwal("Success", json.content, "success");

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