async function removeCartItem(id) {

    const response = await fetch("RemoveCartItem", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({pid: id})
    });

    if (response.ok) {
        const result = await response.json();
        if (result.status) {
            successSwal("Success", result.content, "success");
            GetCart();
        } else {
            erroeSwal("Error", result.content, "error");
        }
    } else {
        erroeSwal("Error", "Failed to remove item from cart", "error");
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

    function successSwal(title, text, icon) {
        Swal.fire({
            title: title,
            text: text,
            icon: icon,
            showConfirmButton: false,
            timer: 1500
        });
    }
}


