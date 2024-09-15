

// Payment completed. It can be a successful failure.
payhere.onCompleted = function onCompleted(orderId) {
    console.log("Payment completed. OrderID:" + orderId);

    window.location = "success.html";
};

// Payment window closed
payhere.onDismissed = function onDismissed() {
    // Note: Prompt user to pay again or show an error page
    console.log("Payment dismissed");
};

// Error occurred
payhere.onError = function onError(error) {
    // Note: show an error page
    console.log("Error:" + error);
};


//**********************pah here end***************************//


const fname = document.getElementById("fname");
const lname = document.getElementById("lname");
const country = document.getElementById("country-list");
const billingAddress = document.getElementById("billing_address");
const billingAddress2 = document.getElementById("billing_address2");

const city = document.getElementById("city");
const state = document.getElementById("state");
const zipcode = document.getElementById("zipcode");
const phone = document.getElementById("phone");
const email = document.getElementById("email");

async  function ChekcOut() {

    document.querySelectorAll('.error').forEach(errorElement => errorElement.textContent = '');

    let valid = true;

    // Validate first name
    if (fname.value.trim() === "") {
        document.getElementById("fnameError").textContent = "First name is required";
        valid = false;
    }

    // Validate last name
    if (lname.value.trim() === "") {
        document.getElementById("lnameError").textContent = "Last name is required";
        valid = false;
    }

    // Validate country
    if (country.value.trim() == "0") {
        document.getElementById("countryError").textContent = "Please select a country";
        valid = false;
    }

    // Validate billing address
    if (billingAddress.value.trim() == "") {
        document.getElementById("billingAddressError").textContent = "Billing address is required";
        valid = false;
    }

    // Validate city
    if (city.value.trim() == "") {
        document.getElementById("cityError").textContent = "City is required";
        valid = false;
    }

    // Validate state
    if (state.value.trim() == "") {
        document.getElementById("stateError").textContent = "State is required";
        valid = false;
    }

    // Validate zip code
    if (zipcode.value.trim() == "") {
        document.getElementById("zipcodeError").textContent = "Postcode/ZIP is required";
        valid = false;
    } else if (!/^\d{5}$/.test(zipcode.value.trim())) {
        document.getElementById("zipcodeError").textContent = "Invalid ZIP code format";
        valid = false;
    }

    // Validate phone number
    if (phone.value.trim() == "") {
        document.getElementById("phoneError").textContent = "Phone number is required";
        valid = false;
    } else if (!/^\d{10}$/.test(phone.value.trim())) {
        document.getElementById("phoneError").textContent = "Phone number must be 10 digits";
        valid = false;
    }

    // Validate email address
    if (email.value.trim() == "") {
        document.getElementById("emailError").textContent = "Email address is required";
        valid = false;
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value.trim())) {
        document.getElementById("emailError").textContent = "Invalid email format";
        valid = false;
    }

    if (valid) {
        const billingDetails = {
            firstName: fname.value.trim(),
            lastName: lname.value.trim(),
            country: country.value.trim(),
            address1: billingAddress.value.trim(),
            address2: billingAddress2.value.trim(),
            city: city.value.trim(),
            state: state.value.trim(),
            zipcode: zipcode.value.trim(),
            phone: phone.value.trim(),
            email: email.value.trim()
        };

        console.log("Form data is valid", billingDetails);


        const response = await fetch("AddInvioce", {
            method: "POST",
            body: JSON.stringify(billingDetails),
            headers: {"Content-Type": "application/json"}
        });

        if (response.ok) {
            const json = await response.json();
            console.log(json);

            if (json.success) {
                payhere.startPayment(json.payhereJson);
            } else {

                erroeSwal("Error", json.content, "error");
            }

        } else {


            erroeSwal("Error", "Try aging!", "error");
        }

    } else {
        console.log("Form has validation errors.");
    }
}

async function getCheckoutData() {

    const response = await fetch('CheckoutData');

    if (response.ok) {
        const data = await response.json();

        if (data.status) {

            const cart = data.cart;
            const orBody = document.getElementById('or-body');
            const templateRow = document.getElementById('template-row');
            const templateTable = document.getElementById('template-table');

            let subtotal = 0;
            let shipping = 0;

            cart.forEach(item => {

                const newRow = templateRow.cloneNode(true);

                newRow.querySelector('.product-thumbnail img').src = "product-images/" + item.pid + "/image1.png";
                newRow.querySelector('.product-name').textContent = item.title;
                newRow.querySelector('.product-qty').textContent = `x ${item.qty}`;
                newRow.querySelector('.product-total').textContent = `RS ${item.price * item.qty}`;

                subtotal += item.qty * item.price;
                shipping += item.qty * item.shipping;


                orBody.appendChild(newRow);
            });


            document.getElementById('or-product-subtotal').textContent = "RS " + subtotal.toFixed(2);
            document.getElementById('orp-Shipping').textContent = "RS " + shipping.toFixed(2);
            document.getElementById('or-total').textContent = "RS " + (subtotal + shipping).toFixed(2);

            if (data.address_status) {
                const address = data.address;
                fname.value = address.user_id.first_name;
                lname.value = address.user_id.last_name;
                country.value = address.country_id.id;
                billingAddress.value = address.line_1;
                city.value = address.city;
                state.value = address.state;
                zipcode.value = address.post_code;
                phone.value = address.mobile;
                email.value = address.email;
                billingAddress2.value = address.billing_address2;
            }
        } else {
            console.log("Error fetching data:", data.content);
            erroeSwal("Error fetching data", data.content, "error");
        }
    } else {

        erroeSwal("Error", "Try aging!", "error");
    }

}

function erroeSwal(title, text, type) {
    Swal.fire({
        title: title,
        text: text,
        icon: type,
        showConfirmButton: false,
        timer: 1500,
        background: "#000"
    });
}