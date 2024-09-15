const fname = document.getElementById("fname").value.trim();
const lname = document.getElementById("lname").value.trim();
const country = document.getElementById("country-list").value;
const billingAddress = document.getElementById("billing_address").value.trim();
const city = document.getElementById("city").value.trim();
const state = document.getElementById("state").value.trim();
const zipcode = document.getElementById("zipcode").value.trim();
const phone = document.getElementById("phone").value.trim();
const email = document.getElementById("email").value.trim();

function ChekcOut() {

    document.querySelectorAll('.error').forEach(errorElement => errorElement.textContent = '');

    let valid = true;

    // Validate first name
    if (fname === "") {
        document.getElementById("fnameError").textContent = "First name is required";
        valid = false;
    }

    // Validate last name
    if (lname === "") {
        document.getElementById("lnameError").textContent = "Last name is required";
        valid = false;
    }

    // Validate country
    if (country === "0") {
        document.getElementById("countryError").textContent = "Please select a country";
        valid = false;
    }

    // Validate billing address
    if (billingAddress === "") {
        document.getElementById("billingAddressError").textContent = "Billing address is required";
        valid = false;
    }

    // Validate city
    if (city === "") {
        document.getElementById("cityError").textContent = "City is required";
        valid = false;
    }

    // Validate state
    if (state === "") {
        document.getElementById("stateError").textContent = "State is required";
        valid = false;
    }

    // Validate zip code
    if (zipcode === "") {
        document.getElementById("zipcodeError").textContent = "Postcode/ZIP is required";
        valid = false;
    } else if (!/^\d{5}$/.test(zipcode)) {
        document.getElementById("zipcodeError").textContent = "Invalid ZIP code format";
        valid = false;
    }

    // Validate phone number
    if (phone === "") {
        document.getElementById("phoneError").textContent = "Phone number is required";
        valid = false;
    } else if (!/^\d{10}$/.test(phone)) {
        document.getElementById("phoneError").textContent = "Phone number must be 10 digits";
        valid = false;
    }

    // Validate email address
    if (email === "") {
        document.getElementById("emailError").textContent = "Email address is required";
        valid = false;
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        document.getElementById("emailError").textContent = "Invalid email format";
        valid = false;
    }


    if (valid) {
        const billingDetails = {
            firstName: fname,
            lastName: lname,
            country: country,
            address1: billingAddress,
            city: city,
            state: state,
            zipcode: zipcode,
            phone: phone,
            email: email
        };

        console.log("Form data is valid", billingDetails);
    } else {
        console.log("Form has validation errors.");
    }
}

async function getCheckoutData() {
    try {
        const response = await fetch('/CheckoutData');

        if (response.ok) {
            const data = await response.json();

            if (data.status) {

                const cart = data.cart;



                if (data.address_status) {
                    const address = data.address;
                    document.getElementById("fname").value = address.user_id.first_name;
                    document.getElementById("lname").value = address.user_id.last_name;
                    document.getElementById("country-list").value = address.country_id.name;
                    document.getElementById("billing_address").value = address.line_1;
                    document.getElementById("city").value = address.city;
                    document.getElementById("state").value = address.state;
                    document.getElementById("zipcode").value = address.post_code;
                    document.getElementById("phone").value = address.mobile;
                    document.getElementById("email").value = address.email;
                }
            } else {
                console.log("Error fetching data:", data.content);
            }
        } else {

            erroeSwal("Error", "Failed to save product.");
        }
    } catch (error) {
        console.error('Error:', error);
    }
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