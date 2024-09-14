async  function GetCart() {

    const response = await fetch("GetCart", {
        headers: {"Content-Type": "application/json"}
    });
    if (response.ok) {
        const json = await response.json();
        console.log(json.content);
        if (json.status) {
           
            console.log(json);
            
            json.content.forEach((item)=>{
                
                console.log(item);
                
                
                
                
            });
            
            
        } else {
           
        }
    } else {
        console.log("Error:", response.statusText);
    }

}

function erroeSwal(title, text, icon) {
    Swal.fire({
        title: title,
        text: text,
        icon: icon,
        showConfirmButton: false,
        timer: 1500
    });
}