<template>
  <div>
    <Header />

    <!-- Product Detail -->
    <div v-if="product" class="bg-white text-black px-6 py-10 mx-auto max-w-6xl rounded-3xl shadow-xl mt-6 space-y-6">
      <nav class="text-xl text-gray-700 px-1 pt-6 max-w-6xl mx-auto">
        <router-link to="/sale-items" class="font-medium text-blue-600 hover:underline">
          Home <span> ></span>
        </router-link>
        <span v-if="product" class="ml-2 text-black font-medium">
          {{ product.brandName }} {{ product.model }} {{ product.ramGb }}/
          {{ product.storageGb }}{{ product.storageGbUnit }} {{ product.color }}
        </span>
      </nav>
      <div class="flex flex-col md:flex-row gap-10">
        <!-- Product Images -->
        <div class="md:w-1/2 w-full">
          <img :src="getImageUrl" :alt="`${product.brandName} ${product.model}`"
            class="w-full rounded-2xl shadow-md object-cover aspect-square" />
          <div class="flex gap-3 mt-4 overflow-x-auto">
            <img v-for="(img, index) in product.images" :key="index" :src="img" :alt="`Image ${index + 1}`"
              class="w-20 h-20 object-cover rounded-lg border hover:ring-2 hover:ring-blue-400 transition-all duration-200 cursor-pointer" />
          </div>
        </div>

        <!-- Product Info -->
        <div class="md:w-1/2 w-full space-y-4 text-base Itbms-row">
          <h1 class="text-2xl font-semibold mb-2">
            {{ product.brandName }} {{ product.model }}
          </h1>

          <p class="text-3xl font-extrabold text-yellow-400">
            {{ product.price.toLocaleString() }} <span class="font-extrabold">Baht</span>
          </p>

          <p class="text-gray-600 Itbms-description">
            {{ product.description || "-" }}
          </p>

          <div class="grid grid-cols-2 gap-y-3 gap-x-4 mt-6 text-sm">
            <!-- Brand -->
            <p>
              <strong>Brand: </strong>
              <span class="Itbms-brand" :class="{ 'text-gray-400': !product.brandName?.trim() }">
                {{ product.brandName || "-" }}
              </span>
            </p>

            <!-- Model -->
            <p>
              <strong>Model: </strong>
              <span class="Itbms-model" :class="{ 'text-gray-400': !product.model?.trim() }">
                {{ product.model || "-" }}
              </span>
            </p>

            <!-- RAM -->
            <p>
              <strong>RAM: </strong>
              <span class="Itbms-ramGb" :class="{ 'text-gray-400': !product.ramGb }">
                {{ product.ramGb || "-" }}
              </span>
              <span class="Itbms-ramGb-unit" v-if="product.ramGb">GB</span>
            </p>

            <!-- Screen Size -->
            <p>
              <strong>Screen Size: </strong>
              <span class="Itbms-screenSizeInch" :class="{ 'text-gray-400': !product.screenSizeInch }">
                {{ product.screenSizeInch || "-" }}
              </span>
              <span class="Itbms-screenSizeInch-unit" v-if="product.screenSizeInch"> inch</span>
            </p>

            <!-- Storage -->
            <p>
              <strong>Storage: </strong>
              <span class="Itbms-storageGb" :class="{ 'text-gray-400': !product.storageGb }">
                {{ product.storageGb || "-" }}
              </span>
              <span class="Itbms-storageGb-unit">
                {{ product.storageGb ? (product.storageGbUnit || "GB") : "" }}
              </span>
            </p>

            <!-- Color -->
            <p>
              <strong>Color: </strong>
              <span class="Itbms-color" :class="{ 'text-gray-400': !product.color?.trim() }">
                {{ product.color || "-" }}
              </span>
            </p>

            <!-- Quantity -->
            <p>
              <strong>Available Quantity: </strong>
              <span class="Itbms-quantity" :class="{ 'text-gray-400': !product.quantity }">
                {{ product.quantity || "-" }}
              </span>
              <span class="Itbms-quantity-unit"> units</span>
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- Not Found fallback (not used, fallback now uses modal) -->
    <div v-else class="text-center text-red-500 py-16 text-lg">
      Product not found.
    </div>

    <!-- 404 Modal -->
    <transition name="fade">
      <div v-if="showErrorModal" class="fixed inset-0 bg-black bg-opacity-60 z-50 flex items-center justify-center">
        <div class="bg-white p-6 rounded-xl shadow-lg max-w-md text-center">
          <h2 class="text-xl font-bold text-red-600 mb-2">Item Not Found</h2>
          <p class="Itbms-message text-gray-700 mb-4">
            The requested sale item does not exist.
          </p>
          <button @click="goBack"
            class="Itbms-button px-5 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition">
            ok
          </button>
        </div>
      </div>
    </transition>

    <Footer />
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { fetchProductById } from "../libs/fetchProduct.js";
import Header from "../components/Header.vue";
import Footer from "../components/Footer.vue";
import { useImageStore } from '../stores/imageStore.js'

const route = useRoute();
const router = useRouter();

const product = ref(null);
const showErrorModal = ref(false);
const imageStore = useImageStore()
const { getImageUrl } = imageStore


function goBack() {
  router.push("/sale-items");
}

onMounted(async () => {
  console.log(getImageUrl);

  try {
    product.value = await fetchProductById(route.params.id);
  } catch (error) {
    showErrorModal.value = true;
  }
});
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
