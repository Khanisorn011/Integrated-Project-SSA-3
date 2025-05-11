<template>
  <div class="sale-items">
    <div class="bg-gradient-to-br from-zinc-900 via-zinc-800 to-gray-900 text-white min-h-screen font-[Poppins]">

      <!-- Header -->
      <Header />

      <!-- Section Title -->
      <section class="py-12 px-7 text-center">
        <h2 class="text-5xl font-bold mb-4 bg-clip-text text-transparent bg-gradient-to-r from-blue-400 to-purple-500">รายการโทรศัพท์</h2>
        <p class="text-gray-300 text-lg max-w-2xl mx-auto">เลือกชมมือถือ พร้อมดีลพิเศษสำหรับคุณ</p>
      </section>

      <!-- Alerts -->
      <div class="px-6 max-w-4xl mx-auto">
        <div v-if="added" class="bg-green-50 border-l-4 border-green-500 text-green-700 p-4 rounded-lg mb-6 shadow-md transform transition-all duration-300 hover:scale-102">
          <div class="flex items-center">
            <span class="text-green-500 font-bold mr-2">✓</span>
            <strong>Success:</strong> <span class="itbms-message ml-2">The sale item has been successfully added.</span>
          </div>
        </div>
        <div v-if="deleted" class="bg-red-50 border-l-4 border-red-500 text-red-700 p-4 rounded-lg mb-6 shadow-md transform transition-all duration-300 hover:scale-102">
          <div class="flex items-center">
            <span class="text-red-500 font-bold mr-2">✕</span>
            <strong>Deleted:</strong> <span class="itbms-message ml-2">The sale item has been deleted.</span>
          </div>
        </div>
      </div>

      <!-- Controls Bar -->
      <div class="max-w-7xl mx-auto px-6 mb-8">
        <div class="bg-gray-800/50 backdrop-blur-sm rounded-xl p-4 flex flex-col sm:flex-row justify-between items-center gap-4 shadow-lg border border-gray-700/50">
          <div class="flex items-center gap-4 w-full sm:w-auto">
            <router-link to="/sale-items/add" class="bg-gradient-to-r from-blue-600 to-blue-700 text-white px-5 py-2.5 rounded-lg hover:from-blue-700 hover:to-blue-800 transition-all duration-300 shadow-md flex items-center gap-2 whitespace-nowrap">
              <span class="font-bold text-lg">+</span>
              Add Sale Item
            </router-link>
            <div class="relative w-full sm:w-auto">
              <select v-model="selectedBrand" class="pl-4 pr-10 py-2.5 rounded-lg bg-gray-700 text-gray-200 focus:outline-none focus:ring-2 focus:ring-blue-500 border border-gray-600 appearance-none w-full">
                <option value="all">All Brands</option>
                <option v-for="(brand, index) in brands" :key="brand.id || index" :value="brand.name">
                  {{ brand.name }}
                </option>
              </select>
              <div class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
                <span class="text-gray-400">▼</span>
              </div>
            </div>
          </div>

          <div class="flex gap-2 w-full sm:w-auto">
            <button @click="viewMode = 'gallery'" :class="[
              'flex-1 sm:flex-none px-4 py-2.5 rounded-lg transition-all duration-300 flex items-center justify-center gap-2',
              viewMode === 'gallery' 
                ? 'bg-blue-600 text-white shadow-md shadow-blue-500/20' 
                : 'bg-gray-700 text-gray-300 hover:bg-gray-600'
            ]">
              <span class="font-mono">■ ■</span>
              Gallery
            </button>
            <button @click="viewMode = 'list'" :class="[
              'flex-1 sm:flex-none px-4 py-2.5 rounded-lg transition-all duration-300 flex items-center justify-center gap-2',
              viewMode === 'list' 
                ? 'bg-blue-600 text-white shadow-md shadow-blue-500/20' 
                : 'bg-gray-700 text-gray-300 hover:bg-gray-600'
            ]">
              <span class="font-mono">≡</span>
              List
            </button>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-if="products.length === 0" class="flex flex-col items-center justify-center text-center text-gray-300 py-24">
        <div class="text-6xl text-gray-600 mb-4">📱</div>
        <h3 class="text-2xl font-semibold mb-2">No Sale Items Found</h3>
        <p class="text-gray-400 max-w-md">Start by adding your first sale item using the button above.</p>
      </div>

      <!-- Gallery View -->
      <main v-else-if="viewMode === 'gallery'" class="max-w-7xl mx-auto">
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6 px-6 pb-16">
          <div class="itbms-row transform transition-all duration-300 hover:-translate-y-2" v-for="(product, index) in filteredProducts" :key="product.id || index">
            <ProductCard :product="product" :imageUrl="imageArray[index % imageArray.length]?.url" />
          </div>
        </div>
      </main>

      <!-- List View -->
      <main v-else class="max-w-6xl mx-auto space-y-5 px-6 pb-16">
        <div v-for="(product, index) in filteredProducts" :key="product.id || index"
          class="flex flex-col sm:flex-row items-start sm:items-center gap-6 p-5 bg-white/5 backdrop-blur-md rounded-2xl shadow-lg border border-gray-700/50 hover:bg-white/10 transition-all duration-300">
          <!-- Image container with gradient overlay -->
          <div class="relative w-full sm:w-32 h-40 sm:h-32 rounded-xl overflow-hidden group">
            <img :src="imageArray[index % imageArray.length]?.url" alt="product image"
              class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-110" />
            <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
          </div>

          <!-- Info -->
          <div class="flex-1 space-y-3">
            <h3 class="text-xl font-semibold">
              {{ product.brandName || '-' }} {{ product.model || '-' }}
            </h3>

            <p class="text-2xl font-bold">
              <span class="bg-clip-text text-transparent bg-gradient-to-r from-yellow-400 to-amber-300">
                {{ product.price?.toLocaleString() || '-' }}
              </span>
              <span class="text-gray-400 text-sm ml-1">Baht</span>
            </p>

            <div class="grid grid-cols-1 sm:grid-cols-2 gap-x-4 gap-y-2 text-sm mt-2">
              <p class="flex items-center gap-2">
                <span class="font-mono text-blue-400 text-xs">▢</span>
                <span class="text-gray-300">RAM:</span>
                <span :class="{ 'text-gray-500': !product.ramGb, 'text-gray-200': product.ramGb }">
                  {{ product.ramGb ? `${product.ramGb} GB` : '-' }}
                </span>
              </p>
              <p class="flex items-center gap-2">
                <span class="font-mono text-purple-400 text-xs">□</span>
                <span class="text-gray-300">Storage:</span>
                <span :class="{ 'text-gray-500': !product.storageGb, 'text-gray-200': product.storageGb }">
                  {{ product.storageGb ? `${product.storageGb} ${product.storageGbUnit || 'GB'}` : '-' }}
                </span>
              </p>
              <p class="flex items-center gap-2">
                <span class="font-mono text-green-400 text-xs">◉</span>
                <span class="text-gray-300">Color:</span>
                <span :class="{ 'text-gray-500': !product.color, 'text-gray-200': product.color }">
                  {{ product.color || '-' }}
                </span>
                <span v-if="product.color" class="h-3 w-3 rounded-full" :style="{ backgroundColor: product.color }"></span>
              </p>
            </div>
          </div>

          <!-- Button -->
          <router-link :to="`/sale-items/${product.id}`"
            class="bg-blue-500/20 hover:bg-blue-500/30 text-blue-300 px-4 py-2 rounded-lg transition-all duration-300 whitespace-nowrap flex items-center gap-2">
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
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import Footer from '../components/Footer.vue'
import Header from '../components/Header.vue'
import ProductCard from '../components/ProductCard.vue'
import images from '../data/image.json'
import { fetchBrands } from "../libs/fetchBrand.js"
import { fetchProducts } from '../libs/fetchProduct.js'

const products = ref([])
const viewMode = ref('gallery')
const imageArray = images
const brands = ref([])
const selectedBrand = ref('all')
const route = useRoute()
const added = computed(() => route.query.added === 'true')
const deleted = computed(() => route.query.deleted === 'true')
const sortedProducts = computed(() =>
  [...products.value].sort((a, b) => new Date(a.createdTime) - new Date(b.createdTime))
)

onMounted(async () => {
  try {
    products.value = await fetchProducts()
    brands.value = await fetchBrands()
  } catch (err) {
    console.error("Failed to load products:", err)
  }
})

const filteredProducts = computed(() => {
  if (selectedBrand.value === 'all') {
    return sortedProducts.value
  }
  return sortedProducts.value.filter(p => p.brandName === selectedBrand.value)
})

</script>