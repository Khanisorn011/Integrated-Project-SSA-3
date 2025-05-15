<template>
  <div class="bg-gradient-to-br from-zinc-900 via-zinc-800 to-gray-900 text-white min-h-screen font-[Poppins]">
    <Header />

    <!-- Section Title -->
    <section class="py-12 px-7 text-center">
      <h2 class="text-5xl font-bold mb-4 bg-clip-text text-transparent bg-gradient-to-r from-yellow-400 to-orange-500">
        รายการแบบ List
      </h2>
      <p class="text-gray-300 text-lg max-w-2xl mx-auto">
        ดูมือถือในรูปแบบรายการพร้อมรายละเอียดเบื้องต้น
      </p>
    </section>

    <!-- Controls Bar -->
    <div class="max-w-7xl mx-auto px-6 mb-8">
      <div class="bg-gray-800/50 backdrop-blur-sm rounded-xl p-4 flex flex-col sm:flex-row justify-between items-center gap-4 shadow-lg border border-gray-700/50">
        <div class="flex items-center gap-4 w-full sm:w-auto">
          <!-- Add Sale Item -->
          <router-link to="/sale-items/add" class="itbms-sale-item-add bg-gradient-to-r from-blue-600 to-blue-700 text-white px-5 py-2.5 rounded-lg hover:from-blue-700 hover:to-blue-800 transition-all duration-300 shadow-md flex items-center gap-2 whitespace-nowrap">
            Add Sale Item
          </router-link>

          <!-- Manage Brand -->
          <router-link to="/brands" class="itbms-manage-brand bg-gradient-to-r from-purple-600 to-purple-700 text-white px-5 py-2.5 rounded-lg hover:from-purple-700 hover:to-purple-800 transition-all duration-300 shadow-md flex items-center gap-2 whitespace-nowrap">
            Manage Brand
          </router-link>

          <!-- Select Brand -->
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

        <!-- View Toggle -->
        <div class="flex gap-2 w-full sm:w-auto">
          <button @click="goToGallery" class="flex-1 sm:flex-none px-4 py-2.5 rounded-lg transition-all duration-300 flex items-center justify-center gap-2 bg-gray-700 text-gray-300 hover:bg-gray-600">
            <span class="font-mono">■ ■</span>
            Gallery
          </button>
          <button disabled class="flex-1 sm:flex-none px-4 py-2.5 rounded-lg bg-blue-600 text-white shadow-md shadow-blue-500/20 flex items-center justify-center gap-2">
            <span class="font-mono">≡</span>
            List
          </button>
        </div>
      </div>
    </div>

    <!-- Empty State -->
    <div v-if="filteredProducts.length === 0" class="text-center text-gray-300 py-24">
      <h3 class="text-2xl font-semibold mb-2">ไม่พบรายการโทรศัพท์</h3>
      <p class="text-gray-400">เริ่มต้นด้วยการเพิ่มสินค้าด้านบน</p>
    </div>

    <!-- Sale Item Table View -->
    <main class="max-w-6xl mx-auto space-y-5 px-6 pb-16">
      <div class="overflow-x-auto">
        <table class="min-w-full table-auto text-sm text-left text-gray-300">
          <thead>
            <tr class="bg-gray-800/50 backdrop-blur-sm text-white">
              <th class="px-4 py-3 text-center">ID</th>
              <th class="px-4 py-3x text-center">Brand</th>
              <th class="px-4 py-3 ">Model</th>
              <th class="px-4 py-3 text-center">Ram</th>
              <th class="px-4 py-3 text-center">Storage</th>
              <th class="px-4 py-3 text-center">Color</th>
              <th class="px-4 py-3 text-center">Screen Size</th>
              <th class="px-4 py-3 text-center">Price</th>
              <th class="px-4 py-3 text-center">Quantity</th>
              <th class="px-4 py-3">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(product, index) in filteredProducts" :key="product.id || index" class="itbms-row bg-gray-800/30 border-b border-gray-700 hover:bg-gray-700/40 transition-all duration-300">
              <td class="px-4 py-2 text-center">{{ product.id }}</td>
              <td class="px-4 py-2 itbms-brand text-center">{{ product.brandName }}</td>
              <td class="px-4 py-2 itbms-model ">{{ product.model }}</td>
              <td class="px-4 py-2 itbms-ramGb text-center">{{ product.ramGb }} GB</td>
              <td class="px-4 py-2 itbms-storageGb text-center">{{ product.storageGb }} GB</td>
              <td class="px-4 py-2 itbms-color text-center">{{ product.color || '-' }}</td>
              <td class="itbms-screenSizeInch text-center">{{ product.screenSizeInch ? Number(product.screenSizeInch).toFixed(1) : '-' }}</td>
              <td class="px-4 py-2 itbms-price text-center">{{ product.price?.toLocaleString() }} ฿</td>
              <td class="px-4 py-2 itbms-quantity text-center">{{ product.quantity != null ? product.quantity : '-' }}</td>
              <td class="px-4 py-2">
                <div class="flex gap-2 ">
                  <button @click="editProduct(product.id)" class="itbms-edit-button bg-blue-500/20 hover:bg-blue-500/30 text-blue-300 px-3 py-1.5 rounded-md transition-all duration-300">
                    E
                  </button>
                  <button @click="deleteProductHandler(product.id)" class="itbms-delete-button bg-red-500/20 hover:bg-red-500/30 text-red-300 px-3 py-1.5 rounded-md transition-all duration-300">
                    D
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>

    <Footer />

    <!-- Confirm Modal -->
    <transition name="fade">
      <div v-if="showConfirmModal" class="fixed inset-0 bg-black bg-opacity-60 z-50 flex items-center justify-center">
        <div class="bg-white p-6 rounded-xl shadow-lg max-w-md text-center text-black">
          <h2 class="text-xl font-bold text-gray-800 mb-4">Confirm Deletion</h2>
          <p class="itbms-message text-gray-700 mb-6">Do you want to delete this sale item?</p>
          <div class="flex justify-center gap-4">
            <button @click="confirmDeleteProduct" class="itbms-confirm-button px-5 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition">Confirm</button>
            <button @click="showConfirmModal = false" class="itbms-cancel-button px-5 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition">Cancel</button>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { fetchProductById, deleteProduct } from "../libs/fetchProduct.js";
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { fetchBrands } from '../libs/fetchBrand'
import { fetchProducts } from '../libs/fetchProduct'


const products = ref([])
const brands = ref([])
const selectedBrand = ref('all')
const router = useRouter()

onMounted(async () => {
  try {
    const basicList = await fetchProducts();
    if (!Array.isArray(basicList)) {
      console.error("Error: fetched products is not an array");
      products.value = [];
      return;
    }

    const result = [];
    for (const item of basicList) {
      try {
        const detailed = await fetchProductById(item.id);
        result.push(detailed);
      } catch (error) {
        console.warn(`โหลดข้อมูลไม่ได้สำหรับ id ${item.id}`, error);
      }
    }

    products.value = result;
  } catch (error) {
    console.error("Error fetching products:", error);
    products.value = [];
  }

  try {
    brands.value = await fetchBrands();
    if (!Array.isArray(brands.value)) {
      console.error("Error: fetched brands is not an array");
      brands.value = [];
    }
  } catch (error) {
    console.error("Error fetching brands:", error);
    brands.value = [];
  }
});

const editProduct = (id) => {
  router.push({ name: "EditSaleItemDetail", params: { id: id } });
}

const showConfirmModal = ref(false)
const productToDeleteId = ref(null)

const confirmDeleteProduct = async () => {
  try {
    const res = await deleteProduct(productToDeleteId.value)
    if (res.ok) {
      showConfirmModal.value = false
      products.value = products.value.filter(p => p.id !== productToDeleteId.value)
    } else {
      console.error("Delete failed:", res.status)
      showConfirmModal.value = false
    }
  } catch (err) {
    console.error("Error during delete:", err)
  }
}

function deleteProductHandler(id) {
  productToDeleteId.value = id
  showConfirmModal.value = true
}

function goToGallery() {
  router.push('/sale-items')
}

const sortedProducts = computed(() =>
  [...products.value].sort((a, b) => new Date(a.createdTime) - new Date(b.createdTime))
)

const filteredProducts = computed(() => {
  if (selectedBrand.value === 'all') return sortedProducts.value
  return sortedProducts.value.filter(p => p.brandName === selectedBrand.value)
})
</script>
