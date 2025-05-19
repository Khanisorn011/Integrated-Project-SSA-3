<template>
  <div class="bg-white min-h-screen font-sans">
    <!-- Header -->
    <Header></Header>

    <div class="bg-white border-b border-gray-200 pt-8 pb-6 px-6 flex justify-between items-center">
      <div class="flex items-center space-x-4">
        <router-link to="/sale-items/list" class="text-blue-500 hover:text-blue-700 transition-colors">Sale Item List</router-link>
      </div>
      <button class="itbms-add-button bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded transition-colors">
        <router-link to="/brands/add">
          Add Brand
        </router-link>
      </button>
    </div>

    <main class="container mx-auto py-6 px-4">
      <!-- Alerts -->
      <div class="max-w-4xl mx-auto mb-6">
        <div v-if="added" class="bg-green-50 border-l-4 border-green-500 text-green-700 p-4 rounded-lg shadow-md">
          <div class="flex items-center">
            <span class="itbms-message text-green-500 font-bold mr-2">✓</span>
            <strong>Success:</strong> <span class="ml-2 itbms-message">The brand has been added.</span>
          </div>
        </div>
        <div v-if="deleted" class=" bg-red-50 border-l-4 border-red-500 text-red-700 p-4 rounded-lg shadow-md">
          <div class="flex items-center">
            <span class="text-red-500 font-bold mr-2">✕</span>
            <strong>Deleted:</strong> <span class="ml-2 itbms-message">The brand has been deleted.</span>
          </div>
        </div>
        <div v-if="updated" class="bg-orange-50 border-l-4 border-green-500 text-green-700 p-4 rounded-lg shadow-md">
          <div class="flex items-center">
            <span class="text-green-500 font-bold mr-2">✓</span>
            <strong>Updated:</strong> <span class="ml-2 itbms-message">The brand has been updated.</span>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-if="brands.length === 0" class="flex flex-col items-center justify-center text-center text-gray-500 py-24">
        <div class="text-6xl text-gray-400 mb-4">🏷️</div>
        <h3 class="text-2xl font-semibold mb-2">No Brands Found</h3>
        <p class="text-gray-400 max-w-md">Start by adding your first brand using the button above.</p>
      </div>

      <!-- Brands Table -->
      <div v-else class="max-w-7xl mx-auto bg-white border border-gray-200 rounded-md shadow-sm">
        <div class="overflow-x-auto">
          <table class="min-w-full bg-white">
            <thead>
              <tr class="bg-gray-50 border-b border-gray-200">
                <th class="py-3 px-4 text-left">ID</th>
                <th class="py-3 px-4 text-left">Name</th>
                <th class="py-3 pl-6 text-left">Action</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="brand in brands" :key="brand.id" class="itbms-row border-b border-gray-200">
                <td class="py-3 px-6 itbms-id">{{ brand.id || '-' }}</td>
                <td class="py-3 px-6 itbms-name">{{ brand.name }}</td>
                <td class="py-3 pl-6">
                  <div class="inline-flex">
                    <router-link :to="`/brands/${brand.id}/edit`"  class="itbms-edit-button bg-gray-200 text-gray-700 px-3 py-1 rounded-sm mr-2 hover:bg-gray-300 transition-colors">
                      Edit
                    </router-link>
                    <button @click="displayModal(brand.id)" class="itbms-delete-button bg-gray-200 text-gray-700 px-3 py-1 rounded-sm hover:bg-gray-300 transition-colors">
                      Delete
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </main>

    <!-- Delete modal confirm-->
        <transition name="fade">
      <div
        v-if="showConfirmModal"
        class="fixed inset-0 bg-black bg-opacity-60 z-50 flex items-center justify-center"
      >
        <div class="bg-white p-6 rounded-xl shadow-lg max-w-md text-center">
          <h2 class="text-xl font-bold text-gray-800 mb-4">Confirm Deletion</h2>
          <p class="itbms-message text-gray-700 mb-6">
            Do you want to delete Nothing brand?
          </p>
          <div class="flex justify-center gap-4">
            <button
              @click="confirmDeleteProduct"
              class="itbms-confirm-button px-5 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition"
            >
              Confirm
            </button>
            <button
              @click="showConfirmModal = false"
              class="itbms-cancel-button px-5 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition"
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    </transition>

        <!-- brand have item modal -->
        <transition name="fade">
      <div
        v-if="showHaveItemInBrandModal"
        class="fixed inset-0 bg-black bg-opacity-60 z-50 flex items-center justify-center"
      >
        <div class="bg-white p-6 rounded-xl shadow-lg max-w-96 text-center">
          <h2 class="text-xl font-bold text-gray-800 mb-4">Delete {{ chooseBrand.name }} is not allow.</h2>
          <p class="itbms-message text-gray-700 mb-6">
            There are sale items with does not send request to BE and returns to the brand list page.
          </p>
          <div class="flex justify-center gap-4">

            <button
              @click="showHaveItemInBrandModal = false"
              class="itbms-cancel-button px-5 py-2 bg-gray-200 text-gray-800 rounded-lg hover:bg-gray-300 transition"
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    </transition>

    <Footer></Footer>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { fetchBrands , deleteBrandById, fetchBrandById } from "../libs/fetchBrand.js"
import Footer from "../components/Footer.vue"
import Header from "../components/Header.vue"

// brands
const brands = ref([])

// router
const route = useRoute()
const added = computed(() => route.query.added === 'true')
const deleted = ref(false)
const updated = computed(() => route.query.updated === 'true')

// fetch brands
onMounted(async () => {
  try {
    brands.value = await fetchBrands()
    console.log(brands.value)
  } catch (err) {
    console.error("Failed to load brands:", err)
  }
})

//delete brand confirm modal
const showConfirmModal = ref(false)

//have item in brand modal
const showHaveItemInBrandModal = ref(false)

const brandIdToDelete = ref(null)
const chooseBrand = ref(null)
const displayModal = async (brandId) => {
  brandIdToDelete.value = brandId
  chooseBrand.value = await fetchBrandById(brandIdToDelete.value)
  console.log(chooseBrand.value.noOfSaleItems);
  
  if (chooseBrand.value.noOfSaleItems > 0) {
     showHaveItemInBrandModal.value = !showHaveItemInBrandModal.value
  }else{
     showConfirmModal.value = !showConfirmModal.value
  }

}



const confirmDeleteProduct = async () => {
  try{
  await deleteBrandById(brandIdToDelete.value)
  brands.value = await fetchBrands()
  showConfirmModal.value = !showConfirmModal.value
  deleted.value = true
  } catch (error) {
  console.log(error);
  }
}

</script>
