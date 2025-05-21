<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />

    <!-- Product Detail -->
    <div v-if="product"
      class="bg-white text-gray-800 px-8 py-12 mx-auto max-w-5xl rounded-3xl shadow-2xl mt-10 space-y-8">
      <!-- Breadcrumb -->
      <nav class="flex items-center space-x-2 text-sm text-gray-500">
        <router-link to="/sale-items" class="itbms-home-button font-medium text-blue-600 hover:underline">
          Home
        </router-link>
        <span>›</span>
        <router-link :to="`/sale-items/${route.params.id}`" class="itbms-back-button font-semibold text-gray-900">
          {{ form.brandName }} {{ form.model }} {{ form.ramGb }}GB/
          {{ form.storageGb }}GB {{ form.color }}
        </router-link>
      </nav>

      <div class="flex flex-col lg:flex-row gap-12">
        <!-- Left: Images -->
        <div class="lg:w-1/2 w-full">
          <img :src="getImageUrl" :alt="form.brandName + ' ' + form.model"
            class="w-full rounded-2xl shadow-lg object-cover aspect-square" />
          <div class="flex gap-4 mt-4 overflow-x-auto">
            <img v-for="(img, i) in product.images" :key="i" :src="img" :alt="`Image ${i + 1}`"
              class="w-20 h-20 object-cover rounded-lg border border-gray-200 hover:ring-2 hover:ring-blue-300 transition cursor-pointer" />
          </div>
        </div>

        <!-- Right: Form -->
        <form @submit.prevent="saveProduct" class="lg:w-1/2 w-full space-y-6">
          <!-- Brand Name -->
          <div class="space-y-1">
            <label for="brandName" class="block text-sm font-medium text-gray-700">Brand Name</label>
            <select id="brandName" v-model="form.brandName"
              class="itbms-brand w-full px-4 py-2 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-300 bg-white"
              :ref="el => inputRefs[0] = el" @keydown.enter.prevent="handleEnter(0)" @blur="trimField('brandName')">
              <option v-for="brand in brands" :key="brand.id" :value="brand.name">
                {{ brand.name }}
              </option>
            </select>
          </div>

          <!-- Model -->
          <div class="space-y-1">
            <label for="model" class="block text-sm font-medium text-gray-700">Model</label>
            <input id="model" v-model="form.model" type="text" placeholder="Model"
              class="itbms-model w-full border border-gray-300 rounded-xl p-3 focus:outline-none focus:ring-2 focus:ring-blue-300"
              :ref="el => inputRefs[1] = el" @keydown.enter.prevent="handleEnter(1)" @blur="trimField('model')" />
          </div>

          <!-- Price -->
          <div class="space-y-1">
            <label for="price" class="block text-sm font-medium text-gray-700">Price (Baht)</label>
            <div class="flex items-center space-x-2">
              <input id="price" v-model.number="form.price" type="number" placeholder="Price"
                class="itbms-price flex-1 border border-gray-300 rounded-xl p-3 text-2xl font-bold focus:outline-none focus:ring-2 focus:ring-yellow-300"
                :ref="el => inputRefs[2] = el" @keydown.enter.prevent="handleEnter(2)" />
              <span class="text-lg font-semibold">Baht</span>
            </div>
          </div>

          <!-- Description -->
          <div class="space-y-1">
            <label for="description" class="block text-sm font-medium text-gray-700">Description</label>
            <textarea id="description" v-model="form.description" placeholder="Description" rows="4"
              class="itbms-description w-full border border-gray-300 rounded-xl p-3 focus:outline-none focus:ring-2 focus:ring-gray-300"
              :ref="el => inputRefs[3] = el" @keydown.enter.prevent="handleEnter(3)" @blur="trimField('description')"></textarea>
          </div>

          <!-- Other Fields -->
          <div class="grid grid-cols-2 gap-6">
            <div>
              <label class="block text-sm font-medium mb-1">RAM (GB)</label>
              <input v-model.number="form.ramGb" type="number"
                class="itbms-ramGb w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
                :ref="el => inputRefs[4] = el" @keydown.enter.prevent="handleEnter(4)" />
            </div>
            <div>
              <label class="block text-sm font-medium mb-1">Screen Size (inch)</label>
              <input v-model.number="form.screenSizeInch" type="number" step="0.1"
                class="itbms-screenSizeInch w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
                :ref="el => inputRefs[5] = el" @keydown.enter.prevent="handleEnter(5)" />
            </div>
            <div>
              <label class="block text-sm font-medium mb-1">Storage (GB)</label>
              <input v-model.number="form.storageGb" type="number"
                class="itbms-storageGb w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
                :ref="el => inputRefs[6] = el" @keydown.enter.prevent="handleEnter(6)" />
            </div>
            <div>
              <label class="block text-sm font-medium mb-1">Color</label>
              <input v-model="form.color" type="text"
                class="itbms-color w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
                :ref="el => inputRefs[7] = el" @keydown.enter.prevent="handleEnter(7)" @blur="trimField('color')" />
            </div>
            <div class="col-span-2">
              <label class="block text-sm font-medium mb-1">Available Quantity</label>
              <input v-model.number="form.quantity" type="number"
                class="itbms-quantity w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
                :ref="el => inputRefs[8] = el" @keydown.enter.prevent="handleEnter(8)" />
            </div>
          </div>

          <!-- Buttons -->
          <div class="flex gap-4 pt-4">
            <button type="submit"
              class="itbms-save-button flex-1 py-3 rounded-xl bg-blue-600 text-white font-medium hover:bg-blue-700 transition"
              :disabled="!isFormModified"
              :class="!isFormModified ? 'disabled:opacity-50' : ''"
              >
              Save
            </button>
            <button type="button" @click="cancelEdit"
              class="itbms-cancel-button flex-1 py-3 rounded-xl border border-red-400 text-red-600 font-medium hover:bg-red-50 transition">
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
      <div v-if="showErrorModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center">
        <div class="bg-white p-6 rounded-2xl shadow-lg max-w-sm text-center">
          <h2 class="text-xl font-bold text-red-600 mb-2">Item Not Found</h2>
          <p class="mb-4 text-gray-700">The requested sale item does not exist.</p>
          <p class="text-sm text-gray-500 mb-6">Redirecting in {{ secondsLeft }} seconds...</p>
          <button @click="goSaleItemList" class="px-6 py-2 rounded-xl bg-yellow-500 text-white font-medium hover:bg-yellow-600 transition">OK</button>
        </div>
      </div>
    </transition>

    <Footer />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { fetchProductById, editProduct } from "../libs/fetchProduct.js";
import { fetchBrands } from '../libs/fetchBrand.js'

import Header from "../components/Header.vue";
import Footer from "../components/Footer.vue";
import { useStateStore } from "../stores/stateStore.js";

const route = useRoute();
const router = useRouter();
const stateStore = useStateStore();
const { getImageUrl } = stateStore;

const product = ref(null);
const brands = ref([]);
const inputRefs = ref([]);
const showErrorModal = ref(false);
const secondsLeft = ref(3);
let timeoutRef = null;
let countdownInterval = null;

// form state
const form = reactive({
  brandName: "",
  model: "",
  price: 0,
  description: "",
  ramGb: 0,
  screenSizeInch: 0,
  storageGb: 0,
  color: "",
  quantity: null,
});

// original snapshot for change detection
const originData = ref({});
const isFormModified = computed(() => JSON.stringify(form) !== JSON.stringify(originData.value));

const trimField = (key) => {
  if (typeof form[key] === 'string') form[key] = form[key].trim();
};

const handleEnter = (index) => {
  const next = inputRefs.value[index + 1];
  if (next) next.focus();
  else saveProduct();
};

onMounted(async () => {
  try {
    const data = await fetchProductById(route.params.id);
    product.value = data;
    Object.assign(form, {
      brandName: data.brandName,
      model: data.model,
      price: data.price,
      description: data.description,
      ramGb: data.ramGb,
      screenSizeInch: data.screenSizeInch,
      storageGb: data.storageGb,
      color: data.color,
      quantity: data.quantity,
    });
    originData.value = { ...form };

    const bList = await fetchBrands();
    brands.value = bList.sort((a, b) => a.name.localeCompare(b.name));
  } catch (err) {
    showErrorModal.value = true;
    timeoutRef = setTimeout(() => router.push("/sale-items"), 3000);
    countdownInterval = setInterval(() => { if (secondsLeft.value > 0) secondsLeft.value--; }, 1000);
  }
});

const saveProduct = async () => {
  try {
    const matched = brands.value.find(b => b.name === form.brandName);
    const payload = {
      brand: { id: matched.id, name: matched.name },
      model: form.model,
      description: form.description,
      price: Number(form.price),
      ramGb: form.ramGb,
      screenSizeInch: Number(form.screenSizeInch),
      storageGb: form.storageGb,
      color: form.color,
      quantity: form.quantity,
    };
    console.log(payload);
    const a = await editProduct(route.params.id, payload);
    console.log(a);

    router.push({ path: `/sale-items/${route.params.id}`, query: { updated: 'true' } });
  } catch (err) {
    if (err.response?.status === 404) showErrorModal.value = true;
    else console.error(err);
  }
};


// cancel edit
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
  clearTimeout(timeoutRef); clearInterval(countdownInterval);
  router.push("/sale-items");
};
</script>

<style scoped>
.fade-enter-active, .fade-leave-active { transition: opacity 0.3s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }
</style>