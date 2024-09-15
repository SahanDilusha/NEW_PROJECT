const body = document.getElementById("t-body");
const productitem = document.getElementById("product-item");


async  function GetCart() {

    body.innerHTML = "";

    const response = await fetch("GetCart", {
        headers: {"Content-Type": "application/json"}
    });
    if (response.ok) {
        const json = await response.json();
        console.log(json.content);
        if (json.status) {

            console.log(json);

            let total = 0;
            let shiping = 0;
            let sihip = 0;

            json.content.forEach((item) => {

                let clone = productitem.cloneNode(true);


                clone.querySelector("#pName").innerHTML = item.title;

                clone.querySelector("#pName").href = "product-details.html?id=" + item.pid;

                clone.querySelector("#pImg").src = "product-images/" + item.pid + "/image1.png";
                clone.querySelector("#pPrice").innerHTML = "RS " + item.price;
                clone.querySelector("#pQty").innerHTML = item.qty;
                clone.querySelector("#pSubtotal").innerHTML = "RS " + (item.qty * item.price).toFixed(2);

                total += item.qty * item.price;
                sihip += item.shipping * item.qty;


                clone.querySelector("#rs-trash").onclick = () => {
                    removeCartItem(item.pid);
                    GetCart();
                };


                body.appendChild(clone);


            });

            document.getElementById("Cart-Subtotal").innerHTML = "RS " + total.toFixed(2);
            document.getElementById("sihip").innerHTML = "RS " + sihip.toFixed(2);
            document.getElementById("f-total").innerHTML = "RS " + (total + sihip).toFixed(2);




        }else{
            body.innerHTML = "";
            document.getElementById("sub-t").remove();
            
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