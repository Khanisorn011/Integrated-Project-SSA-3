async function fetchProducts() {
    try {
      const res = await fetch('http://localhost:8080/itb-mshop/v1/sales-items')
      if (!res.ok) throw new Error('โหลดสินค้าล้มเหลว')
      const data = await res.json()
      return data
    } catch (err) {
      throw err 
    }
  }

  async function fetchProductById(id) {
    try {
      const res = await fetch(`http://localhost:8080/itb-mshop/v1/sales-items/${id}`)
      if (res.status === 404) {
        throw { status: 404, message: 'ไม่พบสินค้าที่ร้องขอ' }
      }
      if (!res.ok) throw new Error('โหลดสินค้ารายตัวล้มเหลว')
      const data = await res.json()
      return data
    } catch (err) {     
      throw err
    }
  }

  export {fetchProducts,fetchProductById}