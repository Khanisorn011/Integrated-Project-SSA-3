async function fetchBrands() {
    try {
        const res = await fetch('http://localhost:8080/itb-mshop/v1/brands')
        const data = await res.json()
        return data
    } catch (err) {
        throw err
    }
}

async function fetchBrandById(id) {
    try {
        const res = await fetch(`http://localhost:8080/itb-mshop/v1/brands/${id}`)
        const data = await res.json()
        return data
    } catch (err) {
        throw err
    }
}

async function editBrand(id , payload){
    try{
        const res = await fetch(`http://localhost:8080/itb-mshop/v1/brands/${id}`,{
            method : "PUT",
            headers : {
                "Content-Type" : "application/json"
            } ,
            body : JSON.stringify(payload)
        })
    }catch(err){
        throw err
    }
}

async function deleteBrandById(id){
    try{
        const res = await fetch(`http://localhost:8080/itb-mshop/v1/brands/${id}`,{
            method : "DELETE"
        });
    }catch (err){
        throw err
    }
}


export { fetchBrands , fetchBrandById , editBrand , deleteBrandById }