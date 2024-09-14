var subCategoryObject;

async function LoadFeatures() {
    const main_category = document.getElementById("main_category");
    const condition_op = document.getElementById("condition_op");
    const brand = document.getElementById("brand");

        const response = await fetch("LoadFeatures", {
            method: "POST",
            headers: {"Content-Type": "application/json"}
        });

        if (response.ok) {
            const json = await response.json();

            if (json !== null) {
                console.log(json);

                const main = json.main_category;
                const condition = json.condition;
                const brands = json.barnd;
                subCategoryObject = json.sub_category; 

                if (main.length > 0) {
                    main.forEach(category => {
                        const option = document.createElement("option");
                        option.value = category.id;
                        option.textContent = category.name;
                        main_category.appendChild(option);
                    });
                } else {
                    console.log("No categories found.");
                }

                if (brands.length > 0) {
                    brands.forEach(b => {
                        const option = document.createElement("option");
                        option.value = b.id;
                        option.textContent = b.name;
                        brand.appendChild(option);
                    });
                } else {
                    console.log("No segment found.");
                }
                
                if (condition.length > 0) {
                    condition.forEach(segment => {
                        const option = document.createElement("option");
                        option.value = segment.id;
                        option.textContent = segment.name;
                        condition_op.appendChild(option);
                    });
                } else {
                    console.log("No barnd found.");
                }
                
            } else {
                console.log("json null");
            }
        } else {
            console.log(response.statusText);
        }

}

function LoadSubCategory() {
    const main_category = document.getElementById("main_category");
    const sub_category = document.getElementById("sub_category");

    sub_category.length = 1;

    const selectedMainCategoryId = main_category.value;

    if (selectedMainCategoryId && subCategoryObject.length > 0) {
        const filteredSubCategories = subCategoryObject.filter(sub => sub.mainCategory.id == selectedMainCategoryId);

        if (filteredSubCategories.length > 0) {
            filteredSubCategories.forEach(sub => {
                const option = document.createElement("option");
                option.value = sub.id;
                option.textContent = sub.name;
                sub_category.appendChild(option);
            });
        } else {
            console.log("No subcategories found for this main category.");
        }
    }
}



