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

async function deleteBrandById(id){
    try{
        const res = await fetch(`http://localhost:8080/itb-mshop/v1/brands/${id}`,{
            method : "DELETE"
        });
    }catch (err){
        throw err
    }
}


export { fetchBrands , fetchBrandById , deleteBrandById }