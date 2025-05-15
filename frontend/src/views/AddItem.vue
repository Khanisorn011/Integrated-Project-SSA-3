<template>
  <div class="bg-gradient-to-b from-gray-50 to-gray-100 min-h-screen pb-12">
    <Header />

    <!-- Add Product Form -->
    <div
      class="bg-white text-black px-8 py-10 mx-auto max-w-6xl rounded-2xl shadow-lg mt-8 mb-12"
    >
      <!-- Breadcrumb Navigation -->
      <nav class="flex items-center text-lg text-gray-600 mb-6">
        <router-link
          to="/sale-items"
          class="itbms-home-button font-medium text-blue-600 hover:text-blue-800 transition-colors"
        >
          Home
        </router-link>
        <span class="mx-2 text-gray-400 font-light"> > </span>
        <span class="text-gray-800 font-medium">Add New Product</span>
      </nav>

      <h1
        class="text-3xl font-bold text-gray-800 mb-8 border-b border-gray-200 pb-4"
      >
        Add New Product
      </h1>

      <form
        @submit.prevent="handleSubmit"
        class="flex flex-col md:flex-row gap-12"
      >
        <!-- Product Image Preview -->
        <div class="md:w-1/2 w-full">
          <div
            class="bg-gray-50 rounded-xl border border-gray-200 shadow-sm p-3 transition-all duration-300 hover:shadow-md"
          >
            <img
              :src="getImageUrl"
              class="w-full rounded-lg object-cover aspect-square"
            />
          </div>
          <div class="flex gap-3 mt-5 overflow-x-auto pb-2">
            <img
              v-for="(img, index) in form.images"
              :key="index"
              :src="img"
              class="w-20 h-20 object-cover rounded-lg border border-gray-200 hover:ring-2 hover:ring-blue-500 transition-all duration-200 cursor-pointer shadow-sm"
            />
          </div>
        </div>

        <!-- Product Info Form -->
        <div class="md:w-1/2 w-full text-base itbms-row">
          <div class="bg-blue-50 rounded-xl p-6 mb-6">
            <h2 class="text-xl font-semibold text-gray-800 mb-1">
              Product Information
            </h2>
            <p class="text-gray-500 text-sm">
              Fill in the details below to add a new product
            </p>
          </div>

          <div class="space-y-5">
            <div>
              <div class="flex justify-between mb-1">
                <label class="text-gray-700 font-medium">Brand</label>
                <span class="text-red-500 text-sm font-medium">Required*</span>
              </div>
              <select
                v-model="form.brandId"
                class="itbms-brand w-full border border-gray-300 p-3 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all duration-200"
                required
              >
                <option
                  v-for="(brand, index) in brands"
                  :key="brand.id || index"
                  :value="brand.id"
                >
                  {{ brand?.name }}
                </option>
              </select>
            </div>

            <div>
              <label class="text-gray-700 font-medium block mb-1"
                >Model <span class="text-red-500">*</span></label
              >
              <input
                ref="model"
                @blur="trim('model')"
                v-model="form.model"
                type="text"
                placeholder="Enter model name"
                class="itbms-model w-full border border-gray-300 p-3 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all duration-200"
              />
            </div>

            <!-- Price + Quantity -->
            <div class="grid grid-cols-2 gap-5">
              <div>
                <label class="text-gray-700 font-medium block mb-1"
                  >Price <span class="text-red-500">*</span></label
                >
                <div class="relative">
                  <input
                    ref="price"
                    v-model.number="form.price"
                    type="number"
                    placeholder="0"
                    class="itbms-price w-full border border-gray-300 p-3 pl-16 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all duration-200"
                  />
                  <div
                    class="absolute inset-y-0 left-0 flex items-center px-3 pointer-events-none bg-gray-100 rounded-l-lg border-r border-gray-300 text-gray-700 font-medium"
                  >
                    Baht
                  </div>
                </div>
              </div>        
            </div>

            <!-- Description -->
            <div>
              <label class="text-gray-700 font-medium block mb-1"
                >Description <span class="text-red-500">*</span></label
              >
              <textarea
                ref="description"
                v-model="form.description"
                placeholder="Enter product description"
                class="itbms-description w-full border border-gray-300 p-3 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all duration-200"
                rows="3"
                @blur="trim('description')"
                @keydown.enter.prevent="focusNext('ramGb')"
              ></textarea>
            </div>

            <!-- Optional Fields Section -->
            <div class="mt-8">
              <h3
                class="text-gray-700 font-medium pb-2 border-b border-gray-200 mb-4"
              >
                Additional Information (Optional)
              </h3>
              <div class="grid grid-cols-2 gap-5">
                <div>
                  <label class="text-gray-600 text-sm block mb-1">RAM</label>
                  <div class="relative">
                    <input
                      ref="ramGb"
                      v-model="form.ramGb"
                      type="number"
                      placeholder="0"
                      class="itbms-ramGb w-full border border-gray-300 p-3 pl-12 rounded-lg focus:ring-1 focus:ring-blue-400 focus:border-blue-400 outline-none transition-all duration-200"
                    />
                    <div
                      class="absolute inset-y-0 left-0 flex items-center px-3 pointer-events-none text-gray-500 font-medium"
                    >
                      GB
                    </div>
                  </div>
                </div>
                <div>
                  <label class="text-gray-600 text-sm block mb-1"
                    >Screen Size</label
                  >
                  <div class="relative">
                    <input
                      ref="screenSizeInch"
                      v-model="form.screenSizeInch"
                      type="number"
                      step="0.1"
                      placeholder="0.0"
                      class="itbms-screenSizeInch w-full border border-gray-300 p-3 pl-16 rounded-lg focus:ring-1 focus:ring-blue-400 focus:border-blue-400 outline-none transition-all duration-200"
                    />
                    <div
                      class="absolute inset-y-0 left-0 flex items-center px-2 pointer-events-none text-gray-500 font-medium"
                    >
                      inches
                    </div>
                  </div>
                </div>
                <div>
                  <label class="text-gray-600 text-sm block mb-1"
                    >Storage</label
                  >
                  <div class="relative">
                    <input
                      ref="storageGb"
                      v-model="form.storageGb"
                      type="number"
                      placeholder="0"
                      class="itbms-storageGb w-full border border-gray-300 p-3 pl-12 rounded-lg focus:ring-1 focus:ring-blue-400 focus:border-blue-400 outline-none transition-all duration-200"
                    />
                    <div
                      class="absolute inset-y-0 left-0 flex items-center px-3 pointer-events-none text-gray-500 font-medium"
                    >
                      GB
                    </div>
                  </div>
                </div>
                <div>
                  <label class="text-gray-600 text-sm block mb-1">Color</label>
                  <input
                    ref="color"
                    v-model="form.color"
                    type="text"
                    placeholder="Enter color"
                    class="itbms-color w-full border border-gray-300 p-3 rounded-lg focus:ring-1 focus:ring-blue-400 focus:border-blue-400 outline-none transition-all duration-200"
                  />
                </div>

                   <div>
                  <label class="text-gray-600 text-sm block mb-1">Quantity</label>
                  
                  <input
                    ref="quantity"
                    v-model="form.quantity"
                    type="number"
                    placeholder="1"
                    class="itbms-quantity w-full	border border-gray-300 p-3 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all duration-200"
                  />
                </div>
              </div>
            </div>

            <!-- Form Actions -->
            <div
              class="flex items-center gap-4 pt-6 mt-8 border-t border-gray-200"
            >
              <button
                ref="submitButton"
                type="submit"
                :disabled="!isFormValid || submitting"
                :class="[
                  'itbms-save-button px-6 py-3 rounded-lg font-medium transition-all duration-200',
                  isFormValid && !submitting
                    ? 'bg-blue-600 text-white hover:bg-blue-700 shadow-md hover:shadow-lg disable'
                    : 'bg-gray-100 text-gray-400 border border-gray-300 cursor-not-allowed',
                ]"
              >
                Save Product
              </button>
              <button
                type="button"
                @click="router.push('/sale-items')"
                class="itbms-cancel-button px-6 py-3 bg-white border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 hover:text-red-600 hover:border-red-200 transition-all duration-200"
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      </form>
    </div>

    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { useRouter } from "vue-router";
import Header from "../components/Header.vue";
import Footer from "../components/Footer.vue";
import { postProduct } from "../libs/fetchProduct.js";
import { fetchBrands } from "../libs/fetchBrand.js";
import { useStateStore } from "../stores/stateStore.js";

// get Image from store
const stateStore = useStateStore();
const { getImageUrl } = stateStore;

// router
const router = useRouter();

// all brand
const brands = ref([]);

// form data
const form = ref({
  brandId: 1,
  brandName: "",
  model: "",
  price: null,
  ramGb: null,
  storageGb: null,
  screenSizeInch: null,
  color: "",
  quantity: null,
  description: "",
});

onMounted(async () => {
  try {
    brands.value = await fetchBrands();
    brands.value.sort((a, b) => a.name.localeCompare(b.name));
    if (brands.value.length > 0) {
      form.brandId = brands.value[0].id;
    }
  } catch (err) {
    console.error("Failed to load brands", err);
  }
});

//trim value in form
const trim = (field) => {
  if (form.value[field]) {
    form.value[field] = form.value[field].trim();
  }
};

// set brand value to form
watch(
  () => form.value.brandId,
  (selectedId) => {
    const selected = brands.value.find((b) => b.id === selectedId);
    form.value.brandName = selected ? selected.name : "";
  }
);

// check if require input have data => can submit
const isFormValid = computed(
  () =>
    form.value.brandId &&
    form.value.model.trim() &&
    form.value.price !== null &&
    form.value.description.trim() !== ""
);

const submitting = ref(false);
const handleSubmit = async () => {
  if (submitting.value) return;
  submitting.value = true;

  const payload = {
    model: form.value.model.trim(),
    description: form.value.description.trim(),
    price: Number(form.value.price),
    quantity: form.value.quantity,
    color: form.value.color?.trim() || undefined,
    ramGb: form.value.ramGb ? Number(form.value.ramGb) : undefined,
    screenSizeInch: form.value.screenSizeInch
      ? Number(form.value.screenSizeInch)
      : undefined,
    storageGb: form.value.storageGb ? Number(form.value.storageGb) : undefined,
    brand: {
      id: form.value.brandId,
      name: form.value.brandName,
    },
  };

  try {
    const res = await postProduct(payload);
    if (res && res.ok) {
      router.push({ path: "/sale-items", query: { added: "true" } });
    } else {
      alert("Failed to add product.");
    }
  } catch (error) {
    console.error(error);
    alert("An error occurred.");
  }
  submitting.value = false;
};

</script>
