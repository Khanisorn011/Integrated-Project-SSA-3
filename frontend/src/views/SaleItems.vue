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
        v-if="products.length === 0"
        class="text-center text-gray-300 text-xl py-10"
      >
        No Sale Item
      </div>

      <!-- Product Grid -->
      <main
        v-else
        class="Itbms-row grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6 px-6 pb-16"
      >
        <ProductCard
          v-for="(product, index) in sortedProducts"
          :key="product.id || index"
          :product="product"
          :imageUrl="imageArray[index % imageArray.length]?.url"
        />
      </main>

      <!-- Footer -->
      <Footer />

    </div>
  </div>
</template>


<script setup>
import { onMounted, computed , ref } from 'vue'
import {fetchProducts , fetchProductById} from '../libs/fetchProduct.js'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import ProductCard from '../components/ProductCard.vue'
import images from '../data/image.json'

const products = ref([])
const imageArray = images


const sortedProducts = computed(() =>
  [...products.value].sort((a, b) => new Date(a.createdTime) - new Date(b.createdTime))
)

onMounted(async () => {
  console.log(imageArray);
    try {
      products.value = await fetchProducts()
    } catch (err) {
      throw new Error("Failed to Load")
    }
  }
)
</script>
