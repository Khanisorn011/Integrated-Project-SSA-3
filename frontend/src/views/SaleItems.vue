<template>
  <div class="sale-items">
    <div class="bg-gradient-to-br from-zinc-900 to-black text-white min-h-screen font-[Poppins]">
      
      <!-- Header -->
      <Header />

      <!-- Section Title -->
      <section class="py-10 px-7">
        <h2 class="text-3xl font-bold mb-2">Phone</h2>
      </section>

      <!-- ถ้าไม่มีสินค้า -->
      <div
        v-if="!loading && products.length === 0"
        class="text-center text-gray-300 text-xl py-10"
      >
        No Sale Item
      </div>

      <!-- Product Grid -->
      <main
        v-else
        class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6 px-6 pb-16"
      >
        <ProductCard
          v-for="(product, index) in sortedProducts"
          :key="product.id || index"
          :product="product"
        />
      </main>

      <!-- Loading -->
      <div v-if="loading" class="text-center mt-4 text-white">
        กำลังโหลดสินค้า...
      </div>

      <!-- Error -->
      <div v-else-if="error" class="text-center mt-4 text-red-400">
        เกิดข้อผิดพลาด: {{ error }}
      </div>

      <!-- Footer -->
      <Footer />

    </div>
  </div>
</template>


<script setup>
import { onMounted, computed } from 'vue'
import { useProductStore } from '@/stores/ProductStore'
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'
import ProductCard from '@/components/ProductCard.vue'

const store = useProductStore()
const { products, loading, error } = store

const sortedProducts = computed(() =>
  [...products].sort((a, b) => new Date(a.createdTime) - new Date(b.createdTime))
)

onMounted(async () => {
  store.clearError() // ✅ ล้าง error ก่อน

  if (products.length === 0) {
    try {
      await store.fetchProducts() // ✅ ต้อง await
    } catch (err) {
      console.error("โหลดสินค้าไม่สำเร็จ:", err)
    }
  }
})
</script>
