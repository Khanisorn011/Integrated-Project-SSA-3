<template>
  <Header />

  <!-- Breadcrumb -->
  <nav class="text-L text-gray-700 px-6 pt-6 max-w-6xl mx-auto">
    <router-link to="/sale-items" class="text-blue-600 hover:underline">Home</router-link>
    <span class="mx-2">›</span>
    <span v-if="product" class="font-semibold text-white">
      {{ product.brandName }} {{ product.model }} {{ product.ramGb }}/{{
        product.storageGb
      }}{{ product.storageGbUnit }}
      {{ product.color }}
    </span>
  </nav>

  <!-- Loading -->
  <div v-if="loading" class="flex justify-center items-center py-16 text-gray-500 text-lg">
    Loading product details...
  </div>

  <!-- Product Detail -->
  <div v-else-if="product" class="bg-white text-black px-6 py-10 max-w-6xl mx-auto">
    <div class="flex flex-col md:flex-row gap-10">
      <!-- Product Images -->
      <div class="md:w-1/2 w-full">
        <img :src="product.image" :alt="`${product.brandName} ${product.model}`"
          class="w-full rounded-2xl shadow-md object-cover aspect-square" />
        <div class="flex gap-3 mt-4 overflow-x-auto">
          <img v-for="(img, index) in product.images" :key="index" :src="img" :alt="`Image ${index + 1}`"
            class="w-20 h-20 object-cover rounded-lg border hover:ring-2 hover:ring-blue-400 transition-all duration-200 cursor-pointer" />
        </div>
      </div>

      <!-- Product Info -->
      <div class="md:w-1/2 w-full space-y-4 text-base itbms-row">
        <h1 class="text-2xl font-semibold mb-2">
          {{ product.brandName }} {{ product.model }}
        </h1>

        <p class="text-3xl font-bold text-blue-600 itbms-price">
          {{ product.price.toLocaleString() }}
          <span class="itbms-price-unit">{{ product.priceUnit }} </span><span class="itbms-price-unit">Baht </span>
        </p>
        <p class="text-gray-600 itbms-description">
          {{ product.description || '-' }}
        </p>

        <div class="grid grid-cols-2 gap-y-3 gap-x-4 mt-6 text-sm">
          <p><strong>Brand:</strong> {{ product.brandName || '-' }}</p>
          <p><strong>Model:</strong> {{ product.model || '-' }}</p>
          <p><strong>RAM:</strong>
            <span :class="{ 'text-gray-400': !product.ramGb }">
              {{ formatField(product.ramGb, 'GB') }}
            </span>
          </p>
          <p><strong>Screen Size:</strong>
            <span :class="{ 'text-gray-400': !product.screenSizeInch }">
              {{ formatField(product.screenSizeInch, 'inch') }}
            </span>
          </p>
          <p><strong>Storage:</strong>
            <span :class="{ 'text-gray-400': !product.storageGb }">
              {{ formatField(product.storageGb, product.storageGbUnit || 'GB') }}
            </span>
          </p>
          <p><strong>Color:</strong> {{ product.color || '-' }}</p>
          <p><strong>Quantity:</strong>
            <span :class="{ 'text-gray-400': !product.quantity }">
              {{ formatField(product.quantity, 'units') }}
            </span>
          </p>
        </div>
      </div>
    </div>
  </div>

  <!-- Not Found fallback (not used, fallback now uses modal) -->
  <div v-else class="text-center text-red-500 py-16 text-lg">
    Product not found.
  </div>

  <Footer />

  <!-- 404 Modal -->
  <transition name="fade">
    <div v-if="showErrorModal" class="fixed inset-0 bg-black bg-opacity-60 z-50 flex items-center justify-center">
      <div class="bg-white p-6 rounded-xl shadow-lg max-w-md text-center">
        <h2 class="text-xl font-bold text-red-600 mb-2">Item Not Found</h2>
        <p class="text-gray-700 mb-4">
          The requested sale item does not exist or has been removed.
        </p>
        <button
          @click="goBack"
          class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition">
          Go Back
        </button>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useProductStore } from "@/stores/ProductStore";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";

const route = useRoute();
const router = useRouter();
const store = useProductStore();

const product = ref(null);
const loading = ref(true);
const showErrorModal = ref(false);

// Utility to format fields with unit or fallback
function formatField(value, unit = "") {
  if (!value) return "-";
  return `${value} ${unit}`;
}

function goBack() {
  router.push('/sale-items'); // ✅ เปลี่ยนจาก router.back() → push ไปหน้า list โดยตรง
}

onMounted(async () => {
  loading.value = true
  store.clearError() // 🟢 ล้าง error ก่อนโหลด
  try {
    product.value = await store.fetchProductById(route.params.id)
  } catch (error) {
    if (error.status === 404) {
      showErrorModal.value = true
    }
  } finally {
    loading.value = false
  }
})

watch(
  () => route.fullPath, // ตรวจสอบการเปลี่ยนแปลงของ URL
  async () => {
    store.clearError() // ล้าง error ก่อน
    await store.fetchProducts() // โหลดข้อมูลใหม่ทุกครั้งที่เข้าหน้านี้
  }
)



</script>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>
