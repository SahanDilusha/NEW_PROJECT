async  function getFilterData() {
    
    const response = await fetch("RemoveCartItem", {
        headers: {"Content-Type": "application/json"}
    });

    if (response.ok) {
        
        const json = await response.json();
        
        console.log(json);
        
    }
    
    
}