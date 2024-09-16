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
        
        json.barnd.forEach((item)=>{
            
            
            
        });




    }


}

function Filter() {

}