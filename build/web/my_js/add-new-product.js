var nameLengthCheck = true;

function showShipping() {
    const inputDiv = document.getElementById("shipping-price");

    if (document.getElementById("optionsRadios2").checked) {
        inputDiv.classList.remove("d-none");
        document.getElementById("error-shipping").classList.remove("d-none");
    } else {
        inputDiv.classList.add("d-none");
        document.getElementById("error-shipping").classList.add("d-none");
    }
}

function ProductNameLength() {
    document.getElementById("product-name-length").innerHTML = document.getElementById("product-name").value.length;

    if (document.getElementById("product-name").value.length > 150) {
        document.getElementById("product-name-length-label").classList.add("text-danger");
        document.getElementById("product-name-length-label").classList.remove("text-secondary");
        document.getElementById("product-name").classList.add("border-danger");
    } else {
        document.getElementById("product-name-length-label").classList.add("text-secondary");
        document.getElementById("product-name-length-label").classList.remove("text-danger");
        if (nameLengthCheck) {
            document.getElementById("product-name").classList.remove("border-danger");
        }
    }

}

function AddNewProduct() {
    
    const name = document.getElementById("product-name");
    const description = document.getElementById("description");
    const qty = document.getElementById("product-qty");
    const price = document.getElementById("price");
    const shippingAdd = document.getElementById("optionsRadios2");
    const shipping = document.getElementById("shipping");
    const main_category = document.getElementById("main_category");
    const condition = document.getElementById("condition_op");
    const brand = document.getElementById("brand");
    const sub_category = document.getElementById("sub_category");
    const main_image = document.getElementById("main-image");
    const subimage1 = document.getElementById("sub-image1");
    const subimage2 = document.getElementById("sub-image2");

    const types = ['image/jpeg', 'image/png', 'image/jpg'];
    const positiveIntegerPattern = /^[1-9]\d*$/;
    const priceValue = parseFloat(price.value.replace(/[^0-9.-]+/g, ""));
    const shippingValue = parseFloat(shipping.value.replace(/[^0-9.-]+/g, ""));
    const qtyValue = qty.value;
    const maxSize = 2 * 1024 * 1024; 

    // Name validation
    if (name.value.trim() === "") {
        erroeSwal("Error", "Product name is required.");
        return;
    } else if (name.value.length > 150) {
        erroeSwal("Error", "Product name cannot exceed 150 characters.");
        return;
    }

    // Description validation
    if (description.value.trim() === "") {
        erroeSwal("Error", "Description is required.");
        return;
    }

    // Quantity validation
    if (!positiveIntegerPattern.test(qtyValue)) {
        erroeSwal("Error", "Quantity must be a positive integer.");
        return;
    }

    // Price validation
    if (isNaN(priceValue) || priceValue <= 0) {
        erroeSwal("Error", "Please enter a valid price.");
        return;
    }

    // Brand validation
    if (brand.value === "0") {
        erroeSwal("Error", "Please select a brand.");
        return;
    }

    // Main Category validation
    if (main_category.value === "0") {
        erroeSwal("Error", "Please select a main category.");
        return;
    }

    // Sub Category validation
    if (sub_category.value === "0") {
        erroeSwal("Error", "Please select a sub-category.");
        return;
    }

    // Condition validation
    if (condition.value === "0") {
        erroeSwal("Error", "Please select a condition.");
        return;
    }

    // Main Image validation
    if (main_image.files.length === 0) {
        erroeSwal("Error", "Main image is required.");
        return;
    } else if (!types.includes(main_image.files[0].type)) {
        erroeSwal("Error", "Main image must be a JPEG or PNG file.");
        return;
    } else if (main_image.files[0].size > maxSize) {
        erroeSwal("Error", "Main image must be smaller than 2MB.");
        return;
    }

    // Shipping validation
    if (shippingAdd.checked && (isNaN(shippingValue) || shippingValue <= 0)) {
        erroeSwal("Error", "Please enter a valid shipping price.");
        return;
    }


    Swal.fire({
        title: "Do you want to save the product?",
        showCancelButton: true,
        confirmButtonText: "Save",
        background: "#000",
        color: "#fff"
    }).then(async (result) => {

        if (result.isConfirmed) {

            const product_dto = {
                titile: name.value,
                description: description.value,
                qty: qty.value,
                barndId: brand.value,
                mainCategoryId: main_category.value,
                subCategoryId: sub_category.value,
                conditionId: condition.value,
                shipping: shippingAdd.checked ? shippingValue : 0.00,
                shippingAdd: shippingAdd.checked,
                price: priceValue
            };

            const data = new FormData();
            data.append("product", JSON.stringify(product_dto));
            data.append("main_image", main_image.files[0]);
            data.append("image_1", subimage1.files[0]);
            data.append("imain_2", subimage2.files[0]);

            const response = await fetch("AddNewProduct", {
                method: "POST",
                body: data
            });

            if (response.ok) {
                const json = await response.json();
                if (json && json.status) {
                    restFild();
                    Swal.fire({
                        icon: "success",
                        title: "Your request has been saved!",
                        showConfirmButton: false,
                        timer: 3000,
                        background: "#000",
                        color: "#fff"
                    });
                } else {
                    erroeSwal("Error", json.content || "Failed to save product.");
                }
            } else {
                erroeSwal("Error", response.statusText || "Server error occurred.");
            }
        }
    });

    function restFild() {
        name.value = "";
        description.value = "";
        qty.value = "";
        price.value = "";
        shipping.value = "";
        brand.value = "0";
        main_category.value = "0";
        sub_category.value = "0";
        main_image.value = null;
        document.getElementById("file-upload-info2").value = "";
        document.getElementById("file-upload-info1").value = "";
        document.getElementById("file-upload-info3").value = "";
    }

    function erroeSwal(title, text) {
        Swal.fire({
            title: title,
            text: text,
            icon: "error",
            showConfirmButton: false,
            timer: 1500,
            background: "#000"
        });
    }
}








