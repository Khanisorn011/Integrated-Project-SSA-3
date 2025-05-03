<template>
  <div class="sale-items">
    <div class="bg-gradient-to-br from-zinc-900 to-black text-white min-h-screen font-[Poppins]">

      <!-- Header -->
      <Header />

      <!-- Section Title -->
      <section class="py-10 px-7 text-center">
        <h2 class="text-4xl font-bold mb-4">รายการโทรศัพท์</h2>
        <p class="text-gray-400 text-lg">เลือกชมมือถือ พร้อมดีลพิเศษสำหรับคุณ</p>
      </section>

      <!-- View Mode Buttons -->
      <div class="flex justify-end gap-3 px-6 mb-6">
        <button @click="viewMode = 'gallery'" :class="[
          'px-4 py-2 rounded-lg transition',
          viewMode === 'gallery' ? 'bg-blue-600 text-white' : 'bg-gray-700 text-gray-300'
        ]">
          Gallery View
        </button>
        <button @click="viewMode = 'list'" :class="[
          'px-4 py-2 rounded-lg transition',
          viewMode === 'list' ? 'bg-blue-600 text-white' : 'bg-gray-700 text-gray-300'
        ]">
          List View
        </button>
      </div>

      <!-- Empty State -->
      <div v-if="products.length === 0" class="text-center text-gray-300 text-xl py-10">
        no sale item
      </div>

      <!-- Gallery View -->
      <main v-if="viewMode === 'gallery'"
        class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6 px-6 pb-16">
        <ProductCard v-for="(product, index) in sortedProducts" :key="product.id || index" :product="product"
          :imageUrl="imageArray[index % imageArray.length]?.url" />
      </main>

      <!-- List View -->
      <main v-else class="space-y-6 px-6 pb-16">
        <div v-for="(product, index) in sortedProducts" :key="product.id || index"
          class="flex items-center gap-6 p-5 bg-white rounded-2xl shadow-lg border border-gray-200">
          <!-- Image -->
          <img :src="imageArray[index % imageArray.length]?.url" alt="product image"
            class="w-32 h-32 object-cover rounded-xl" />

          <!-- Info -->
          <div class="flex-1 space-y-1 text-black">
            <h3 class="text-xl font-semibold">
              {{ product.brandName || '-' }} {{ product.model || '-' }}
            </h3>

            <p class="text-yellow-400 font-bold text-lg">
              {{ product.price?.toLocaleString() || '-' }} Baht
            </p>

            <div class="grid grid-cols-2 gap-x-4 gap-y-1 text-sm mt-2">
              <p>
                <strong>RAM:</strong>
                <span :class="{ 'text-gray-500': !product.ramGb }">
                  {{ product.ramGb ? `${product.ramGb} GB` : '-' }}
                </span>
              </p>
              <p>
                <strong>Storage:</strong>
                <span :class="{ 'text-gray-500': !product.storageGb }">
                  {{ product.storageGb ? `${product.storageGb} ${product.storageGbUnit || 'GB'}` : '-' }}
                </span>
              </p>
              <p>
                <strong>Color:</strong>
                <span :class="{ 'text-gray-500': !product.color }">
                  {{ product.color || '-' }}
                </span>
              </p>
            </div>
          </div>

          <!-- Button -->
          <router-link :to="`/sale-items/${product.id}`"
            class="text-blue-400 hover:underline text-sm whitespace-nowrap">
            View Detail
          </router-link>
        </div>
      </main>


      <!-- Footer -->
      <Footer />

    </div>
  </div>
</template>

<script setup>
import { onMounted, computed, ref } from 'vue'
import { fetchProducts } from '../libs/fetchProduct.js'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import ProductCard from '../components/ProductCard.vue'
import images from '../data/image.json'

const products = ref([])
const viewMode = ref('gallery')
const imageArray = images

const sortedProducts = computed(() =>
  [...products.value].sort((a, b) => new Date(a.createdTime) - new Date(b.createdTime))
)

onMounted(async () => {
  try {
    products.value = await fetchProducts()
  } catch (err) {
    console.error("Failed to load products:", err)
  }
})
</script>
