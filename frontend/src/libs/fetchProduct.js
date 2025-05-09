async function fetchProducts() {
    try {
      const res = await fetch('http://localhost:8080/itb-mshop/v1/sale-items')
      const data = await res.json()
      return data
    } catch (err) {
      throw err 
    }
  }

  async function fetchProductById(id) {
    try {
      const res = await fetch(`http://localhost:8080/itb-mshop/v1/sale-items/${id}`)
      const data = await res.json()
      return data
    } catch (err) {     
      throw err
    }
  }

  async function postProduct(payload) {
    try {
      const response = await fetch('http://localhost:8080/itb-mshop/v1/sale-items', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload)
      })
      return response
    } catch (error) {
      console.error('Error during fetch:', error);
    }
  }

  async function editProduct(id,payload) {
    try{
      const response = await fetch(`http://localhost:8080/itb-mshop/v1/sale-items/${id}`,{
        method: 'PUT',
        headers: {
          'Content-Type' : 'application/json'
        },
        body: JSON.stringify(payload)
      })
      return response
    } catch (error){
      console.error('Error during fetch:', error);
    }
  }

  export {fetchProducts,fetchProductById,postProduct,editProduct}