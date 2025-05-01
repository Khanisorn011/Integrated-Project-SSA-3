import { defineStore } from 'pinia'

export const useProductStore = defineStore('product', {
  state: () => ({
    products: [],
    loading: false,
    error: null
  }),

  actions: {
    async fetchProducts() {
      if (this.products.length > 0) return

      this.loading = true
      this.error = null
      try {
        const res = await fetch('http://localhost:8080/itb-mshop/v1/sales-items')
        if (!res.ok) throw new Error('โหลดสินค้าล้มเหลว')
        const data = await res.json()
        this.products = data
      } catch (err) {
        this.error = err.message
        throw err // ให้ component จัดการต่อ
      } finally {
        this.loading = false
      }
    },

    async fetchProductById(id) {
      this.loading = true
      this.error = null
      try {
        const res = await fetch(`http://localhost:8080/itb-mshop/v1/sales-items/${id}`)
        if (res.status === 404) {
          throw { status: 404, message: 'ไม่พบสินค้าที่ร้องขอ' }
        }
        if (!res.ok) throw new Error('โหลดสินค้ารายตัวล้มเหลว')
        const data = await res.json()
        return data
      } catch (err) {
        this.error = err.message || 'เกิดข้อผิดพลาด'
        throw err
      } finally {
        this.loading = false
      }
    },

    // ✅ เพิ่ม action นี้เข้าไป
    clearError() {
      this.error = null
    }
  }
})
