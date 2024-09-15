async  function getFilterData() {
    
    const response = await fetch("FilterData", {
        headers: {"Content-Type": "application/json"}
    });

    if (response.ok) {
        
        const json = await response.json();
        
        console.log(json);
        
        
        
    }
    
    
}