<template>
  <div>
     <form @submit.prevent="handleSubmit" class="flex flex-col md:flex-row gap-12">
        <!-- Product Image Preview -->
        <div class="md:w-1/2 w-full">
          <div
            class="bg-gray-50 rounded-xl border border-gray-200 shadow-sm p-3 transition-all duration-300 hover:shadow-md">
            <img :src="getImageUrl" class="w-full rounded-lg object-cover aspect-square" />
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
              <select v-model="form.brandName"
                class="itbms-brand w-full border border-gray-300 p-3 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all duration-200"
                required :ref="el => inputRefs[0] = el" @keydown.enter.prevent="handleEnter(0)">
                <option v-for="(brand, index) in brands" :key="brand.id || index" :value="brand.name">
                  {{ brand?.name }}
                </option>
              </select>
            </div>

            <div>
              <label class="text-gray-700 font-medium block mb-1">Model <span class="text-red-500">*</span></label>
              <input @blur="trim('model')" v-model="form.model" type="text" placeholder="Enter model name"
                class="itbms-model w-full border border-gray-300 p-3 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all duration-200"
                maxlength="60" :ref="el => inputRefs[1] = el" @keydown.enter.prevent="handleEnter(1)" />

            </div>

            <!-- Price + Quantity -->
            <div class="grid grid-cols-2 gap-5">
              <div>
                <label class="text-gray-700 font-medium block mb-1">Price <span class="text-red-500">*</span></label>
                <div class="relative">
                  <input v-model.number="form.price" type="number" placeholder="0"
                    class="itbms-price w-full border border-gray-300 p-3 pl-16 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all duration-200"
                    :ref="el => inputRefs[2] = el" @keydown.enter.prevent="handleEnter(2)" />
                  <div
                    class="absolute inset-y-0 left-0 flex items-center px-3 pointer-events-none bg-gray-100 rounded-l-lg border-r border-gray-300 text-gray-700 font-medium">
                    Baht
                  </div>
                </div>
              </div>
            </div>

            <!-- Description -->
            <div>
              <label class="text-gray-700 font-medium block mb-1">Description <span
                  class="text-red-500">*</span></label>
              <textarea v-model="form.description" placeholder="Enter product description"
                class="itbms-description w-full border border-gray-300 p-3 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all duration-200"
                rows="3" @blur="trim('description')" :ref="el => inputRefs[3] = el"
                @keydown.enter.prevent="handleEnter(3)"></textarea>
            </div>

            <!-- Optional Fields Section -->
            <div class="mt-8">
              <h3 class="text-gray-700 font-medium pb-2 border-b border-gray-200 mb-4">
                Additional Information (Optional)
              </h3>
              <div class="grid grid-cols-2 gap-5">
                <div>
                  <label class="text-gray-600 text-sm block mb-1">RAM</label>
                  <div class="relative">
                    <input v-model="form.ramGb" type="number" placeholder="0"
                      class="itbms-ramGb w-full border border-gray-300 p-3 pl-12 rounded-lg focus:ring-1 focus:ring-blue-400 focus:border-blue-400 outline-none transition-all duration-200"
                      :ref="el => inputRefs[4] = el" @keydown.enter.prevent="handleEnter(4)" />
                    <div
                      class="absolute inset-y-0 left-0 flex items-center px-3 pointer-events-none text-gray-500 font-medium">
                      GB
                    </div>
                  </div>
                </div>
                <div>
                  <label class="text-gray-600 text-sm block mb-1">Screen Size</label>
                  <div class="relative">
                    <input v-model="form.screenSizeInch" type="number" step="0.1" placeholder="0.0"
                      class="itbms-screenSizeInch w-full border border-gray-300 p-3 pl-16 rounded-lg focus:ring-1 focus:ring-blue-400 focus:border-blue-400 outline-none transition-all duration-200"
                      :ref="el => inputRefs[5] = el" @keydown.enter.prevent="handleEnter(5)" />
                    <div
                      class="absolute inset-y-0 left-0 flex items-center px-2 pointer-events-none text-gray-500 font-medium">
                      inches
                    </div>
                  </div>
                </div>
                <div>
                  <label class="text-gray-600 text-sm block mb-1">Storage</label>
                  <div class="relative">
                    <input v-model="form.storageGb" type="number" placeholder="0"
                      class="itbms-storageGb w-full border border-gray-300 p-3 pl-12 rounded-lg focus:ring-1 focus:ring-blue-400 focus:border-blue-400 outline-none transition-all duration-200"
                      :ref="el = inputRefs[6] = el" @keydown.enter.prevent="handleEnter(6)" />
                    <div
                      class="absolute inset-y-0 left-0 flex items-center px-3 pointer-events-none text-gray-500 font-medium">
                      GB
                    </div>
                  </div>
                </div>
                <div>
                  <label class="text-gray-600 text-sm block mb-1">Color</label>
                  <input v-model="form.color" type="text" placeholder="Enter color"
                    class="itbms-color w-full border border-gray-300 p-3 rounded-lg focus:ring-1 focus:ring-blue-400 focus:border-blue-400 outline-none transition-all duration-200"
                    :ref="el => inputRefs[7] = el" @keydown.enter.prevent="handleEnter(7)" />
                </div>

                <div>
                  <label class="text-gray-600 text-sm block mb-1">Quantity</label>

                  <input v-model="form.quantity" type="number" placeholder="1"
                    class="itbms-quantity w-full	border border-gray-300 p-3 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all duration-200"
                    :ref="el => inputRefs[8] = el" @keydown.enter.prevent="handleEnter(8)" />
                </div>
              </div>
            </div>

            <!-- Form Actions -->
            <div class="flex items-center gap-4 pt-6 mt-8 border-t border-gray-200">
              <button ref="submitButton" type="submit" :disabled="!isFormValid || submitting || !isFormModified" :class="[
                'itbms-save-button px-6 py-3 rounded-lg font-medium transition-all duration-200',
                isFormValid && !submitting 
                  ? 'bg-blue-600 text-white hover:bg-blue-700 shadow-md hover:shadow-lg disable'
                  : 'bg-gray-100 text-gray-400 border border-gray-300 cursor-not-allowed',
                  !isFormModified ? 'disabled:opacity-50' : ''
              ]">
                Save
              </button>
              <button type="button" @click="router.push('/sale-items')"
                class="itbms-cancel-button px-6 py-3 bg-white border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 hover:text-red-600 hover:border-red-200 transition-all duration-200">
                Cancel
              </button>
            </div>
          </div>
        </div>
      </form>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from "vue";
import { useRouter } from "vue-router";
import { useStateStore } from "../stores/stateStore.js";
import { fetchBrands } from "../libs/fetchBrand.js";

//validate


const stateStore = useStateStore();
const { getImageUrl } = stateStore;

const inputRefs = ref([])
const router = useRouter();
const submitting = ref(false);
const originData = ref({});


const props = defineProps({
  formtype: String,
  form: Object,
});

const form = ref(props.form)

const handleEnter = (index) => {
  const nextInput = inputRefs.value[index + 1]
  if (nextInput) nextInput.focus();
}


onMounted(async () => {
  try {
    brands.value = await fetchBrands();
    brands.value.sort((a, b) => a.name.localeCompare(b.name));
    console.log(brands.value);
    
    if (brands.value.length > 0) {
      form.brandId = brands.value[0].id;
    }
  } catch (err) {
    console.error("Failed to load brands", err);
  }
});

const brands = ref([]);

watch(
  () => form?.value?.brandId,
  (selectedId) => {
    const selected = brands.value.find((b) => b.id === selectedId);
    form.value.brandName = selected ? selected.name : "";
  }
);

  const payload = computed(() => ({
    model: form.value.model.trim(),
    description: form.value.description.trim(),
    price: Number(form.value.price),
    quantity: form.value.quantity,
    color: form.value.color?.trim() || null,
    ramGb: form.value.ramGb ? Number(form.value.ramGb) : null,
    screenSizeInch: form.value.screenSizeInch
      ? Number(form.value.screenSizeInch)
      : null,
    storageGb: form.value.storageGb ? Number(form.value.storageGb) : undefined,
    brand: {
      id: form.value.brandId,
      name: form.value.brandName,
    }
  }) )

const isFormValid = computed(() => {
  return (
    form.value.brandName &&
    form.value.model?.trim().length > 0 &&
    form.value.description?.trim().length > 0 &&
    form.value.price > 0
  );
});

const trim = (field) => {
  if (form.value[field]) {
    form.value[field] = form.value[field].trim();
  }
};

const emits = defineEmits(["payload"]);
const handleSubmit = () => {
    emits("payload", payload.value);
}

//for edit
const isFormModified = computed(() => JSON.stringify(form.value) !== JSON.stringify(originData.value));

if (props.formtype === "edit") {
  onMounted(() => {
    Object.assign(form.value, props?.form);
    originData.value = JSON.parse(JSON.stringify(props.form)); // Save original data for comparison
  });
}
</script>

<style scoped>

</style>