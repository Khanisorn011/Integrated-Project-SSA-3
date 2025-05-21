<template>
  <div class="bg-gradient-to-br from-zinc-900 via-zinc-800 to-gray-900 text-white min-h-screen font-[Poppins]">
    <Header />

    <!-- Section Title -->
    <section class="py-12 px-7 text-center">
      <h2 class="text-5xl font-bold mb-4 bg-clip-text text-transparent bg-gradient-to-r from-cyan-400 to-blue-500">
        รายการมือถือทั้งหมด
      </h2>
      <p class="text-gray-300 text-lg max-w-2xl mx-auto">
        ดูสินค้าทั้งหมดที่มีในระบบในรูปแบบแกลเลอรี
      </p>
    </section>

    <!-- Alerts -->
    <!-- <div class="px-6 max-w-4xl mx-auto">
      <div v-if="added"
        class="bg-green-50 border-l-4 border-green-500 text-green-700 p-4 rounded-lg mb-6 shadow-md transform transition-all duration-300 hover:scale-102">
        <div class="flex items-center">
          <span class="text-green-500 font-bold mr-2">✓</span>
          <strong>Success:</strong> <span class="itbms-message ml-2">The sale item has been successfully added.</span>
        </div>
      </div>
      <div v-if="deleted"
        class="bg-red-50 border-l-4 border-red-500 text-red-700 p-4 rounded-lg mb-6 shadow-md transform transition-all duration-300 hover:scale-102">
        <div class="flex items-center">
          <span class="text-red-500 font-bold mr-2">✕</span>
          <strong>Deleted:</strong> <span class="itbms-message ml-2">The sale item has been deleted.</span>
        </div>
      </div>
    </div> -->
    
      <Alert
        v-if="added"
        :message="'The sale item has been successfully added.'"
        :state="'created'"
      >
      </Alert>

      <Alert
        v-if="deleted"
        :message="'The sale item has been deleted.'"
        :state="'created'"
      >
      </Alert>

    

    <!-- ✅ Controls Bar -->
    <div class="max-w-7xl mx-auto px-6 mb-8">
      <div
        class="bg-gray-800/50 backdrop-blur-sm rounded-xl p-4 flex flex-col sm:flex-row justify-between items-center gap-4 shadow-lg border border-gray-700/50">
        <div class="flex items-center gap-4 w-full sm:w-auto">
          <router-link to="/sale-items/add"
            class="itbms-sale-item-add bg-gradient-to-r from-blue-600 to-blue-700 text-white px-5 py-2.5 rounded-lg hover:from-blue-700 hover:to-blue-800 transition-all duration-300 shadow-md flex items-center gap-2 whitespace-nowrap">
            Add Sale Item
          </router-link>
          <div class="relative w-full sm:w-auto">
            <select v-model="selectedBrand"
              class="pl-4 pr-10 py-2.5 rounded-lg bg-gray-700 text-gray-200 focus:outline-none focus:ring-2 focus:ring-blue-500 border border-gray-600 appearance-none w-full">
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
          <button disabled
            class="flex-1 sm:flex-none px-4 py-2.5 rounded-lg bg-blue-600 text-white shadow-md shadow-blue-500/20 flex items-center justify-center gap-2">
            <span class="font-mono">■ ■</span>
            Gallery
          </button>
          <button @click="goToList" :class="[
            'flex-1 sm:flex-none px-4 py-2.5 rounded-lg transition-all duration-300 flex items-center justify-center gap-2',
            'bg-gray-700 text-gray-300 hover:bg-gray-600',
          ]">
            <span class="font-mono">≡</span>
            List
          </button>
        </div>
      </div>
    </div>

    <!-- Empty State -->
    <div v-if="filteredProducts.length === 0" class="text-center text-gray-300 py-24">
      <i class="itbms-row **:text-2xl font-semibold mb-2">no sale item</i>
    </div>

    <!-- Gallery -->
    <main
      class="max-w-6xl mx-auto grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6 px-6 pb-16">
      <div v-for="(product, index) in filteredProducts" :key="product.id"
        class="bg-white/5 p-4 rounded-xl border border-gray-700/50 hover:bg-white/10 transition-all duration-300 flex flex-col">
        <ProductCard class="itbms-row" :product="product" :imageUrl="imageArray[index % imageArray.length]?.url">
        </ProductCard>
      </div>
    </main>

    <Footer />
  </div>
</template>

<script setup>
import ProductCard from "../components/ProductCard.vue";
import { ref, computed, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import Header from "../components/Header.vue";
import Footer from "../components/Footer.vue";
import { fetchBrands } from "../libs/fetchBrand";
import { fetchProducts } from "../libs/fetchProduct";
import images from "../data/image.json";
import Alert from "../components/Alert.vue";

const products = ref([]);
const brands = ref([]);
const selectedBrand = ref("all");
const imageArray = images;
const router = useRouter();

onMounted(async () => {
  try {
    products.value = await fetchProducts();
    brands.value = await fetchBrands();
  } catch (error) {
    console.log(error);
  }
});


//router
const route = useRoute()
const added = computed(() => route.query.added === 'true')
const deleted = computed(() => route.query.deleted === 'true')

const sortedProducts = computed(() =>
  [...products.value].sort(
    (a, b) => new Date(a.createdTime) - new Date(b.createdTime)
  )
);

const filteredProducts = computed(() => {
  if (selectedBrand.value === "all") return sortedProducts.value;
  return sortedProducts.value.filter(
    (p) => p.brandName === selectedBrand.value
  );
});

function goToList() {
  router.push("/sale-items/list");
}
</script>
