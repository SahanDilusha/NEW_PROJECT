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
            Filter(0);
        };

        document.getElementById("b-all").onclick = () => {
            brandId = 0;
            Filter(0);
        };

        json.main.forEach((items) => {

            let element = document.createElement("li");
            element.textContent = items.name;

            element.onclick = () => {

                categoryId = items.id;
                Filter(0);
            };

            category.appendChild(element);

            json.sub.forEach((item) => {

                if (item.mainCategory.id == items.id) {

                    let element2 = document.createElement("li");
                    element2.textContent = item.name;
                    element2.style = "color:red";

                    element2.onclick = () => {

                        subcategoryId = items.id;
                        Filter(0);
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
                Filter(0);
            };

            barnds.appendChild(element2);

        });

    }


}

const body = document.getElementById("item-body");
const PItem = document.getElementById("p-item");

async  function Filter(first) {
    const dto = {
        category: categoryId,
        subcategory: subcategoryId,
        brand: brandId,
        price_range_start: document.getElementById("amount-min").value,
        price_range_end: document.getElementById("amount-max").value,
        firstResult: first,
        sort_text: document.getElementById("sort_text").value,
        text: document.getElementById("s-text").value
    };

    document.getElementById("sort_text").onchange = () => {
        Filter(first);
    };

    console.log(dto);

    const response = await fetch("GetProduct", {
        method: "POST",
        body: JSON.stringify(dto),
        headers: {"Content-Type": "application/json"}
    });

    if (response.ok) {

        const json = await response.json();

        console.log(json);

        updateProductView(json);

    }

}

const paginationMain = document.getElementById("pagination");
const pgbtn = document.getElementById("pg-btn");

var currentPage = 0;

function updateProductView(json) {

    body.innerHTML = "";
    paginationMain.innerHTML = "";

    if (json.allProductCount == 0) {

        const at = document.createElement("h1");
        at.textContent = "Product not found!";
        body.appendChild(at);
        return;
    }

    const productPerPage = 6;
    const totalPages = Math.ceil(json.allProductCount / productPerPage);

    json.productlist.forEach((item) => {
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

    // Add Previous button
    if (currentPage > 0) {
        let prevBtn = pgbtn.cloneNode(true);
        prevBtn.innerHTML = "Prev";
        prevBtn.addEventListener("click", () => {
            currentPage--;
            Filter(currentPage * productPerPage);
        });
        paginationMain.appendChild(prevBtn);
    }

    // Add page buttons
    for (let i = 0; i < totalPages; i++) {
        let btnClone = pgbtn.cloneNode(true);
        btnClone.querySelector("#pga").textContent = i + 1;

        btnClone.addEventListener("click", ((pageNumber) => {
            return () => {
                currentPage = pageNumber;
                Filter(pageNumber * productPerPage);
            };
        })(i));

        if (i === currentPage) {
            btnClone.classList.add("active");
        } else {
            btnClone.classList.remove("active");
        }

        paginationMain.appendChild(btnClone);
    }

    // Add Next button
    if (currentPage < totalPages - 1) {
        let nextBtn = pgbtn.cloneNode(true);
        nextBtn.innerHTML = "Next";
        nextBtn.addEventListener("click", () => {
            currentPage++;
            Filter(currentPage * productPerPage);
        });
        paginationMain.appendChild(nextBtn);
    }
}
