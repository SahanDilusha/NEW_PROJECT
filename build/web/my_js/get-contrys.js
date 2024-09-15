async function getContrys() {

        const response = await fetch("GetCounrtys", {
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {

            const countries = await response.json();
            
           console.log(countries);
           

            const countrySelect = document.getElementById("country-list");

            countries.forEach(country => {
                const option = document.createElement("option");
                option.value = country.id;
                option.textContent = country.name;
                countrySelect.appendChild(option);
            });
        } else {
            console.log("Error fetching countries:", response.statusText);
        }
  
}


