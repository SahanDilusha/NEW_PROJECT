var categoryId = 0;
var subcategoryId = 0;
var brandId = 0;

async  function getFilterData() {

    const response = await fetch("FilterData", {
        headers: {"Content-Type": "application/json"}
    });

    if (response.ok) {

        const json = await response.json();

        console.log(json);

        const category = document.getElementById("category");
        const barnds = document.getElementById("Barnds");

        document.getElementById("c-all").onclick = () => {
            categoryId = 0;
            subcategoryId = 0;
            Filter();
        };

        document.getElementById("b-all").onclick = () => {
            brandId = 0;
            Filter();
        };

        json.main.forEach((items) => {

            let element = document.createElement("li");
            element.textContent = items.name;

            element.onclick = () => {

                categoryId = items.id;
                Filter();
            };

            category.appendChild(element);

            json.sub.forEach((item) => {

                if (item.mainCategory.id == items.id) {

                    let element2 = document.createElement("li");
                    element2.textContent = item.name;
                    element2.style = "color:red";

                    element2.onclick = () => {

                        subcategoryId = items.id;
                        Filter();
                    };

                    category.appendChild(element2);

                }

            });

        });

        json.barnd.forEach((item) => {

            let element2 = document.createElement("li");
            element2.textContent = item.name;

            element2.onclick = () => {

                brandId = item.id;
                Filter();
            };

            barnds.appendChild(element2);

        });




    }


}

const body = document.getElementById("item-body");
const PItem = document.getElementById("p-item");



async  function Filter() {

    body.innerHTML = "";

    const dto = {
        category: categoryId,
        subcategory: subcategoryId,
        brand: brandId,
        price_range_start: document.getElementById("amount-min").value,
        price_range_end: document.getElementById("amount-max").value

    };

    const response = await fetch("GetProduct", {
        method: "POST",
        body: JSON.stringify(dto),
        headers: {"Content-Type": "application/json"}
    });

    if (response.ok) {

        const json = await response.json();

        console.log(json);

        json.forEach((item) => {

            let clone = PItem.cloneNode(true);

            clone.querySelector("#ppri").textContent = "RS " + item.price;
            clone.querySelector("#pt").textContent = item.titile;
            clone.querySelector("#pb").textContent = item.barnd.name;

            clone.querySelector("#pim1").src = "product-images/" + item.id + "/image1.png";
            clone.querySelector("#pim2").src = "product-images/" + item.id + "/image2.png";


            clone.onclick = () => {
                window.location = "product-details.html?id=" + item.id;
            };


            body.appendChild(clone);

        });

    }

}