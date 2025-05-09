<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />

    <!-- Product Detail -->
    <div
      v-if="product"
      class="bg-white text-gray-800 px-8 py-12 mx-auto max-w-5xl rounded-3xl shadow-2xl mt-10 space-y-8"
    >
      <!-- Breadcrumb -->
      <nav class="flex items-center space-x-2 text-sm text-gray-500">
        <router-link
          to="/sale-items"
          class="font-medium text-blue-600 hover:underline"
        >
          Home
        </router-link>
        <span>›</span>
        <span class="font-semibold text-gray-900">
          {{ form.brandName }} {{ form.model }} {{ form.ramGb }}GB/
          {{ form.storageGb }}GB {{ form.color }}
        </span>
      </nav>

      <div class="flex flex-col lg:flex-row gap-12">
        <!-- Left: Images -->
        <div class="lg:w-1/2 w-full">
          <img
            :src="getImageUrl"
            :alt="`${form.brandName} ${form.model}`"
            class="w-full rounded-2xl shadow-lg object-cover aspect-square"
          />
          <div class="flex gap-4 mt-4 overflow-x-auto">
            <img
              v-for="(img, i) in product.images"
              :key="i"
              :src="img"
              :alt="`Image ${i + 1}`"
              class="w-20 h-20 object-cover rounded-lg border border-gray-200 hover:ring-2 hover:ring-blue-300 transition cursor-pointer"
            />
          </div>
        </div>

        <!-- Right: Form -->
        <form @submit.prevent="saveProduct" class="lg:w-1/2 w-full space-y-6">
          <!-- Brand Name -->
          <div class="space-y-1">
            <label for="brandName" class="block text-sm font-medium text-gray-700">
              Brand Name
            </label>
            <select
              id="brandName"
              v-model="form.brandName"
              class="w-full px-4 py-2 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-300 bg-white"
            >
              <option value="" disabled>เลือกแบรนด์</option>
              <option
                v-for="brand in brands"
                :key="brand.id"
                :value="brand.name"
              >
                {{ brand.name }}
              </option>
            </select>
          </div>

          <!-- Model -->
          <div class="space-y-1">
            <label for="model" class="block text-sm font-medium text-gray-700">
              Model
            </label>
            <input
              id="model"
              v-model="form.model"
              type="text"
              placeholder="Model"
              class="w-full border border-gray-300 rounded-xl p-3 focus:outline-none focus:ring-2 focus:ring-blue-300"
            />
          </div>

          <!-- Price -->
          <div class="space-y-1">
            <label for="price" class="block text-sm font-medium text-gray-700">
              Price (Baht)
            </label>
            <div class="flex items-center space-x-2">
              <input
                id="price"
                v-model.number="form.price"
                type="number"
                placeholder="Price"
                class="flex-1 border border-gray-300 rounded-xl p-3 text-2xl font-bold focus:outline-none focus:ring-2 focus:ring-yellow-300"
              />
              <span class="text-lg font-semibold">Baht</span>
            </div>
          </div>

          <!-- Description -->
          <div class="space-y-1">
            <label for="description" class="block text-sm font-medium text-gray-700">
              Description
            </label>
            <textarea
              id="description"
              v-model="form.description"
              placeholder="Description"
              rows="4"
              class="w-full border border-gray-300 rounded-xl p-3 focus:outline-none focus:ring-2 focus:ring-gray-300"
            ></textarea>
          </div>

          <!-- Other Fields -->
          <div class="grid grid-cols-2 gap-6">
            <div>
              <label class="block text-sm font-medium mb-1">RAM (GB)</label>
              <input
                v-model.number="form.ramGb"
                type="number"
                class="w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
              />
            </div>
            <div>
              <label class="block text-sm font-medium mb-1">Screen Size (inch)</label>
              <input
                v-model.number="form.screenSizeInch"
                type="number"
                step="0.1"
                class="w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
              />
            </div>
            <div>
              <label class="block text-sm font-medium mb-1">Storage (GB)</label>
              <input
                v-model.number="form.storageGb"
                type="number"
                class="w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
              />
            </div>
            <div>
              <label class="block text-sm font-medium mb-1">Color</label>
              <input
                v-model="form.color"
                type="text"
                class="w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
              />
            </div>
            <div class="col-span-2">
              <label class="block text-sm font-medium mb-1">Available Quantity</label>
              <input
                v-model.number="form.quantity"
                type="number"
                class="w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
              />
            </div>
          </div>

          <!-- Buttons -->
          <div class="flex gap-4 pt-4">
            <button
              type="submit"
              class="flex-1 py-3 rounded-xl bg-blue-600 text-white font-medium hover:bg-blue-700 transition"
            >
              Save
            </button>
            <button
              type="button"
              @click="cancelEdit"
              class="flex-1 py-3 rounded-xl border border-red-400 text-red-600 font-medium hover:bg-red-50 transition"
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Fallback -->
    <div v-else class="text-center text-red-500 py-16 text-lg">
      Product not found.
    </div>

    <!-- Error Modal -->
    <transition name="fade">
      <div
        v-if="showErrorModal"
        class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center"
      >
        <div class="bg-white p-6 rounded-2xl shadow-lg max-w-sm text-center">
          <h2 class="text-xl font-bold text-red-600 mb-2">Item Not Found</h2>
          <p class="mb-4 text-gray-700">
            The requested sale item does not exist.
          </p>
          <p class="text-sm text-gray-500 mb-6">
            Redirecting in {{ secondsLeft }} seconds...
          </p>
          <button
            @click="goSaleItemList"
            class="px-6 py-2 rounded-xl bg-yellow-500 text-white font-medium hover:bg-yellow-600 transition"
          >
            OK
          </button>
        </div>
      </div>
    </transition>

    <Footer />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import {
  fetchProductById,
  editProduct
} from "../libs/fetchProduct.js";
import {fetchBrands} from '../libs/fetchBrand.js'

import Header from "../components/Header.vue";
import Footer from "../components/Footer.vue";
import { useStateStore } from "../stores/stateStore.js";

const route = useRoute();
const router = useRouter();
const product = ref(null);
const showErrorModal = ref(false);
const secondsLeft = ref(3);
let timeoutRef = null;
let countdownInterval = null;

const stateStore = useStateStore();
const { getImageUrl } = stateStore;

// Brands list
const brands = ref([]);

// Form state
const form = reactive({
  brandName: "",
  model: "",
  price: 0,
  description: "",
  ramGb: 0,
  screenSizeInch: 0,
  storageGb: 0,
  color: "",
  quantity: 0,
});

// Sync form when product loads
watch(product, (newProduct) => {
  if (newProduct) {
    Object.assign(form, {
      brandName: newProduct.brandName || "",
      model: newProduct.model || "",
      price: newProduct.price || 0,
      description: newProduct.description || "",
      ramGb: newProduct.ramGb || 0,
      screenSizeInch: newProduct.screenSizeInch || 0,
      storageGb: newProduct.storageGb || 0,
      color: newProduct.color || "",
      quantity: newProduct.quantity || 0,
    });
  }
});

// Fetch data on mount
onMounted(async () => {
  try {
    product.value = await fetchProductById(route.params.id);
    brands.value = await fetchBrands();
  } catch (err) {
    showErrorModal.value = true;
    timeoutRef = setTimeout(() => {
      router.push("/sale-items");
    }, 3000);
  }
});

// Save form
const saveProduct = async () => {
  try {
    const matched = brands.value.find(b => b.name === form.brandName);
    const payload = {
      brand: { id: matched?.id, name: matched?.name },
      model: form.model.trim(),
      description: form.description.trim(),
      price: Number(form.price),
      ramGb: form.ramGb ? Number(form.ramGb) : undefined,
      screenSizeInch: form.screenSizeInch ? Number(form.screenSizeInch) : undefined,
      storageGb: form.storageGb ? Number(form.storageGb) : undefined,
      color: form.color.trim() || undefined,
      quantity: Number(form.quantity),
    };
    await editProduct(route.params.id, payload);
    console.log("Payload to save:", payload);
    router.go(-1);
  } catch (err) {
    console.error(err);
  }
};

// Cancel edit
const cancelEdit = () => {
  router.go(-1);
};

// Watch for error modal to start countdown
watch(showErrorModal, (v) => {
  if (v) {
    countdownInterval = setInterval(() => {
      if (secondsLeft.value > 0) {
        secondsLeft.value--;
      }
    }, 1000);
  }
});

const goSaleItemList = () => {
  clearTimeout(timeoutRef);
  clearInterval(countdownInterval);
  router.push("/sale-items");
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
