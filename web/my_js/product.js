

async  function getProduct() {


    const response = await fetch("LoadSingleProduct", {
        method: "POST",
        body: JSON.stringify(new URLSearchParams(window.location.search).get("id")),
        headers: {"Content-Type": "application/json"}
    });

    if (response.ok) {
        const json = await response.json();

        console.log(json);

        if (json !== null) {


            document.getElementById("im-1").src = "product-images/" + json.product.id + "/image1.png";
            document.getElementById("im-2").src = "product-images/" + json.product.id + "/image2.png";
            document.getElementById("im-3").src = "product-images/" + json.product.id + "/image3.png";

            document.getElementById("t-1").src = "product-images/" + json.product.id + "/image1.png";
            document.getElementById("t-2").src = "product-images/" + json.product.id + "/image2.png";
            document.getElementById("t-3").src = "product-images/" + json.product.id + "/image3.png";

            document.getElementById("title-detail").innerHTML = json.product.titile;
            document.getElementById("brands").innerHTML = json.product.barndName;
            document.getElementById("price-product").innerHTML = "RS " + json.product.price.toFixed(2);
            document.getElementById("Description").innerHTML = json.product.description;

            const shipping = document.getElementById("shipping");
            if (json.product.shipping == 0) {
                shipping.innerHTML = "Free Shipping";
            } else {
                shipping.innerHTML = "RS " + json.product.shipping.toFixed(2);
            }

            const  availability = document.getElementById("Availability");

            if (json.product.qty == 0) {
                availability.innerHTML = 0;
                document.getElementById("Add-to-cart").remove();
                document.getElementById("Add-to-cart-text").innerHTML = "Out Of Stock";
            } else {
                document.getElementById("add-btn").onclick = () => {
                    AddToCart(json.product.id);
//                    alert("ok btn");
                };
                availability.innerHTML = json.product.qty + " Items In Stock";

            }

        }
    }

}