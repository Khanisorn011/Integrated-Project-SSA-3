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
      <div class="flex justify-between items-center px-6 mb-6 ">
        <div class="flex justify-between items-center px-6 mb-6 flex-wrap gap-4">
          <div class="flex items-center gap-4 flex-wrap">
            <button @click="showForm = true" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
              Add Sale Item
            </button>
            <div v-if="showForm" class="mt-4 bg-gray-100 p-6 rounded shadow">
              <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
                <!-- Modal Content -->
                <div class="bg-white text-black rounded-lg shadow-xl w-full max-w-3xl p-8 relative">
                  <h2 class="text-2xl font-semibold mb-6">Add Sale Item</h2>

                  <form @submit.prevent="handleSubmit" class="space-y-4">
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                      <select v-model="form.brandId" class="input bg-white text-black" required>
                        <option value="" disabled>Select a brand</option>
                        <option v-for="(brand, index) in brands" :key="brand.id || index" :value="brand.id">
                          {{ brand.name }}
                        </option>
                      </select>
                      <input v-model="form.model" type="text" placeholder="Model" class="input" required />
                      <input v-model="form.price" type="number" step="0.01" min="0" placeholder="Price" class="input" required />
                      <input v-model="form.description" type="text" placeholder="Description" class="input" required />
                      <input v-model="form.quantity" type="number" min="0" placeholder="Quantity" class="input" required />
                      <input v-model="form.ram" type="number" min="0" max="32" placeholder="RAM (Optional)" class="input" />
                      <input v-model="form.screenSize" type="number" step="0.01" min="0" max="10" placeholder="Screen Size (Optional)" class="input" />
                      <input v-model="form.storage" type="number" min="0" max="512" placeholder="Storage (Optional)" class="input" />
                      <input v-model="form.color" type="text" placeholder="Color (Optional)" class="input" />
                    </div>

                    <div class="flex justify-end gap-4 mt-4">
                      <button type="submit"
                        class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 disabled:opacity-50"
                        :disabled="!isFormValid" >
                        Save
                      </button>
                      <button @click="cancelForm" type="button"
                        class="bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-500">
                        Cancel
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
          <select v-model="selectedBrand" class="px-4 py-2 rounded-lg bg-gray-700 text-gray-300 focus:outline-none">
            <option value="all">All Brands</option>
            <option v-for="(brand, index) in brands" :key="brand.id || index" :value="brand.name">
              {{ brand.name }}
            </option>
          </select>
        </div>

        <div class="flex gap-3">
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
      </div>

      <!-- Empty State -->
      <div v-if="products.length === 0" class="text-center text-gray-300 text-xl py-10">
        no sale item
      </div>

      <div v-if="showSuccessModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
  <div class="bg-white p-6 rounded-lg shadow-lg text-center max-w-sm w-full">
    <h3 class="text-xl font-bold text-green-600 mb-4">Success!</h3>
    <p class="text-gray-700 mb-6">The sale item has been added successfully.</p>
    <button @click="showSuccessModal = false"
      class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
      OK
    </button>
  </div>
</div>

      <!-- Gallery View -->
      <main v-if="viewMode === 'gallery'"
        class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6 px-6 pb-16">
        <ProductCard class="itbms-row" v-for="(product, index) in filteredProducts" :key="product.id || index"
          :product="product" :imageUrl="imageArray[index % imageArray.length]?.url" />
      </main>

      <!-- List View -->
      <main v-else class="space-y-6 px-6 pb-16">
        <div v-for="(product, index) in filteredProducts" :key="product.id || index"
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
import { onMounted, computed, ref, watch } from 'vue'
import { fetchProducts, postProduct } from '../libs/fetchProduct.js'
import { fetchBrands } from "../libs/fetchBrand.js"
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import ProductCard from '../components/ProductCard.vue'
import images from '../data/image.json'

const products = ref([])
const brands = ref([])
const viewMode = ref('gallery')
const imageArray = images
const selectedBrand = ref('all')
const showForm = ref(false)
const message = ref('')
const form = ref({
  brandId: '',
  brand: '',
  model: '',
  price: '',
  description: '',
  ram: '',
  screenSize: '',
  storage: '',
  color: '',
  quantity: '',
})


const showSuccessModal = ref(false)
const cancelForm = () => {
  showForm.value = false
  resetForm()
}

const resetForm = () => {
  form.value = {
    brandId: '',
    brand: '',
    model: '',
    price: '',
    description: '',
    ram: '',
    screenSize: '',
    storage: '',
    color: '',
    quantity: '',
  }
  message.value = ''
}

const sortedProducts = computed(() =>
  [...products.value].sort((a, b) => new Date(a.createdTime) - new Date(b.createdTime))
)

const isFormValid = computed(() => {
  const price = Number(form.value.price)
  const quantity = Number(form.value.quantity)

  return (
    form.value.brandId &&
    form.value.model.trim() &&
    form.value.description.trim() &&
    !isNaN(price) && price >= 0 &&
    !isNaN(quantity) && quantity >= 0
  )
})

const handleSubmit = async () => {
  const selectedBrand = brands.value.find(b => b.id === form.value.brandId)
  const payload = {
    model: form.value.model.trim(),
    description: form.value.description.trim(),
    price: Number(form.value.price),
    quantity: Number(form.value.quantity),
    color: form.value.color.trim() || undefined,
    ramGb: form.value.ram ? Number(form.value.ram) : undefined,
    screenSizeInch: form.value.screenSize ? Number(form.value.screenSize) : undefined,
    storageGb: form.value.storage ? Number(form.value.storage) : undefined,
    brand: {
      id: selectedBrand?.id,
      name: selectedBrand?.name,
    },
  }

  try {
    const response = await postProduct(payload)
    if (response.status === 201) {
      message.value = 'The sale item has been successfully added'
      showForm.value = false
      products.value = await fetchProducts()
      showSuccessModal.value = true
      resetForm()
    } else {
      console.error('Failed to add sale item. Status:', response.status)
    }
  } catch (error) {
    console.error('Error during fetch:', error)
  }
  
}

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

watch(() => form.value.brandId, (selectedId) => {
  const selected = brands.value.find(b => b.id === selectedId)
  form.value.brand = selected ? selected.name : ''
})

</script>
