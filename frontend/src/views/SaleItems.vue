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

    <Alert v-if="added" :message="'The sale item has been successfully added.'" :state="'created'" />
    <Alert v-if="deleted" :message="'The sale item has been deleted.'" :state="'created'" />

    <!-- Controls Bar -->

    <div class="max-w-7xl mx-auto px-6 mb-8">
      <div
        class="bg-gray-800/50 backdrop-blur-sm rounded-xl p-4 flex flex-col sm:flex-row justify-between items-center gap-4 shadow-lg border border-gray-700/50">
        <div class="flex items-center gap-4 w-full sm:w-auto">
          <router-link to="/sale-items/add"
            class="itbms-sale-item-add bg-gradient-to-r from-blue-600 to-blue-700 text-white px-5 py-2.5 rounded-lg hover:from-blue-700 hover:to-blue-800 transition-all duration-300 shadow-md flex items-center gap-2 whitespace-nowrap">
            Add Sale Item
          </router-link>
          <div class="w-full sm:w-auto relative" id="itbms-brand-filter">
            <!-- Filter Brand Multi -->
            <div class="flex flex-col sm:flex-row sm:items-center gap-3 w-full" id="itbms-brand-filter">

              <div class="relative w-full sm:w-72">
                <div
                  class="w-full min-h-[42px] flex items-center justify-between px-3 py-2 text-sm bg-white border border-gray-300 rounded-xl shadow-sm cursor-pointer focus:outline-none focus:ring-2 focus:ring-blue-500 transition text-gray-700">

                  <div class="flex flex-wrap items-center gap-1 overflow-hidden">
                    <template v-if="selectedBrands.length === 0">
                      <span class="text-gray-400">Filter by brand(s)</span>
                    </template>
                    <template v-else>
                      <span v-for="brand in selectedBrands" :key="brand"
                        class="bg-gray-200 text-gray-700 text-xs px-2 py-1 rounded-md flex items-center gap-1">
                        {{ brand }}
                        <button @click="removeBrand(brand)" class="text-gray-500 hover:text-red-600">✕</button>
                      </span>
                    </template>
                  </div>
                  <button @click="toggleDropdown">
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 ml-2 flex-shrink-0 text-gray-700"
                      viewBox="0 0 24 24" fill="currentColor">
                      <path
                        d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2a1 1 0 01-.293.707L15 13.414V19a1 1 0 01-1.447.894l-4-2A1 1 0 019 17v-3.586L3.293 6.707A1 1 0 013 6V4z" />
                    </svg>
                  </button>
                </div>

                <!-- Dropdown Content -->
                <div v-if="showDropdown"
                  class="absolute z-50 mt-2 w-full bg-white text-black border border-gray-200 rounded-xl shadow-lg max-h-60 overflow-y-auto">
                  <label v-for="(brand, index) in sortedBrands" :key="index"
                    class="flex items-center px-4 py-2 text-sm cursor-pointer hover:bg-gray-100">
                    <input type="checkbox" :value="brand.name" v-model="selectedBrands"
                      class="mr-2 rounded focus:ring-blue-500 text-black" />
                    {{ brand.name }}
                  </label>
                </div>
              </div>

              <!-- Clear Button -->
              <button @click="clearAllBrands"
                class="self-start sm:self-auto px-4 py-2 text-sm font-medium bg-gray-100 text-gray-700 border border-gray-300 rounded-xl hover:bg-red-500 hover:text-white transition-all">
                Clear
              </button>
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
    <div v-if="saleItems.length === 0" class="text-center text-gray-300 py-24">
      <i class="itbms-row text-2xl font-semibold mb-2">no sale item</i>
    </div>

    <!-- Gallery -->
    <main class="max-w-6xl mx-auto px-6 pb-16 flex flex-col gap-4">
      <!-- Sort Buttons-->
      <div v-if="saleItems.length !== 0" class="flex justify-end gap-2 mt-4">
        <button @click="sortOrder = 'default'"
          :class="['p-2 rounded-md', sortOrder === 'default' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700']">
          <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
          </svg>
        </button>
        <button @click="sortOrder = 'asc'"
          :class="['p-2 rounded-md', sortOrder === 'asc' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700']">
          <img src="@/assets/icons/view-sort-ascending-svgrepo-com.svg" alt="Sort Ascending" class="w-5 h-5" />
        </button>
        <button @click="sortOrder = 'desc'"
          :class="['p-2 rounded-md', sortOrder === 'desc' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700']">
          <img src="@/assets/icons/view-sort-descending-svgrepo-com.svg" alt="Sort Descending" class="w-5 h-5" />
        </button>
      </div>
    </main>

    <!-- Product Grid -->
    <main
      class="max-w-6xl mx-auto grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6 px-6 pb-16">
      <div v-for="(product, index) in saleItems" :key="product.id"
        class="bg-white/5 p-4 rounded-xl border border-gray-700/50 hover:bg-white/10 transition-all duration-300 flex flex-col">
        <ProductCard class="itbms-row" :product="product" :imageUrl="imageArray[index % imageArray.length]?.url" />
      </div>
    </main>


    <div class="max-w-7xl mx-auto px-6 pb-12">
      <PageBar :currentPage="currentPage" :totalPages="pageResponse.totalPages" :pageSize="pageSize"
        @update:currentPage="(val) => currentPage = val" @update:pageSize="(val) => pageSize = val" />
    </div>

    <Footer />
  </div>
</template>

<script setup>

import { computed, onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import Alert from "../components/Alert.vue";
import Footer from "../components/Footer.vue";
import Header from "../components/Header.vue";
import ProductCard from "../components/ProductCard.vue";
import images from "../data/image.json";
import { fetchBrands } from "../libs/fetchBrand";
import { fetchSaleItemByCondition } from "../libs/fetchSaleItem";
import PageBar from "../components/PageBar.vue";


const brands = ref([]);
const selectedBrands = ref([]);
const imageArray = images;
const router = useRouter();
const showDropdown = ref(false);

const saleItems = ref([]);
const currentPage = ref(0);
const pageSize = ref(10);
const pageResponse = ref({ totalPages: 0 });

const sortOrder = ref(sessionStorage.getItem("sortOrder") || "default");

watch(sortOrder, (newVal) => {
  sessionStorage.setItem("sortOrder", newVal);
});

const sortDirection = computed(() => {
  if (sortOrder.value === "asc") return "ASC";
  if (sortOrder.value === "desc") return "DESC";
  return null;
});

const payload = ref({
  filterBrands: [],
  page: currentPage.value,
  size: pageSize.value,
  sortField: "brand.name",
  sortDirection: sortDirection.value,
});

onMounted(async () => {
  try {
    brands.value = await fetchBrands();

    const savedBrands = sessionStorage.getItem("selectedBrands");
    if (savedBrands) {
      selectedBrands.value = JSON.parse(savedBrands);

    }
  } catch (error) {
    console.log(error);
  }
});

watch(selectedBrands, (newVal) => {
  sessionStorage.setItem("selectedBrands", JSON.stringify(newVal));
})

watch([selectedBrands, currentPage, pageSize, sortOrder], async () => {
  payload.value = {
    ...payload.value,
    filterBrands: selectedBrands.value,
    page: currentPage.value,
    size: pageSize.value,
    sortDirection: sortDirection.value,
  };
  const response = await fetchSaleItemByCondition(payload.value);
  saleItems.value = response.content || [];
  pageResponse.value.totalPages = response.totalPages || 0;
}, { immediate: true });

//router
const route = useRoute()
const added = computed(() => route.query.added === 'true')
const deleted = computed(() => route.query.deleted === 'true')

const sortedBrands = computed(() => {
  return [...brands.value].sort((a, b) => a.name.localeCompare(b.name));
})



function toggleDropdown() {
  showDropdown.value = !showDropdown.value;
}

function clearAllBrands() {
  selectedBrands.value = [];
}

function removeBrand(brand) {
  selectedBrands.value = selectedBrands.value.filter((b) => b !== brand);
}

function goToList() {
  router.push("/sale-items/list");
}

async function fetchSaleItems() {
  const response = await fetchSaleItemByCondition(payload.value);
  pageResponse.value = {
    totalPages: response.totalPages || 0,
  };
  saleItems.value = response.content || [];
}

watch([selectedBrands, currentPage, pageSize], fetchSaleItems, { immediate: true });

</script>