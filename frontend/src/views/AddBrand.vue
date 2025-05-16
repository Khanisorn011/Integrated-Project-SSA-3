<template>
  <div class="bg-gray-50 min-h-screen pb-12">
    <Header />

    <div class="bg-white text-gray-800 px-8 py-12 mx-auto max-w-5xl rounded-3xl shadow-2xl mt-10 space-y-8">
      <!-- Breadcrumb -->
      <nav class="flex items-center space-x-2 text-sm text-gray-500">
        <router-link to="/sale-items/list" class="itbms-item-list font-medium text-blue-600 hover:underline">
          Sale Item List
        </router-link>
         <span> > </span>
        <router-link to="/brands" class="itbms-manage-brand text-blue-500 hover:text-blue-700 transition-colors">Brand List</router-link>
        <span> > </span>
        <b>New Brand</b>
      </nav>

      <h1 class="text-3xl font-bold text-gray-900 mb-8 border-b border-gray-200 pb-4">Add New Brand</h1>

      <form @submit.prevent="saveBrand" class="space-y-6">
        <!-- Name -->
        <div class="space-y-1">
          <label for="name" class="block text-sm font-medium text-gray-700">
            Name <span class="text-red-500">*</span>
          </label>
          <input
            id="name"
            v-model="form.name"
            type="text"
            placeholder="Brand Name"
            required
            class="itbms-name w-full border border-gray-300 rounded-xl p-3 focus:outline-none focus:ring-2 focus:ring-blue-300"
            :ref="el => inputRefs[0] = el"
            @keydown.enter.prevent="handleEnter(0)"
            @blur="trimField('name')"
          />
        </div>

        <!-- Other Fields -->
        <div class="grid grid-cols-2 gap-6">
          <div>
            <label for="websiteUrl" class="block text-sm font-medium mb-1 text-gray-700">Website URL</label>
            <input
              id="websiteUrl"
              v-model="form.websiteUrl"
              type="url"
              placeholder="https://example.com"
              class="itbms-websiteUrl w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
              :ref="el => inputRefs[1] = el"
              @keydown.enter.prevent="handleEnter(1)"
              @blur="trimField('websiteUrl')"
            />
          </div>
          <div>
            <label for="countryOfOrigin" class="block text-sm font-medium mb-1 text-gray-700">Country of Origin</label>
            <input
              id="countryOfOrigin"
              v-model="form.countryOfOrigin"
              type="text"
              placeholder="e.g., Japan"
              class="itbms-countryOfOrigin w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
              :ref="el => inputRefs[2] = el"
              @keydown.enter.prevent="handleEnter(2)"
              @blur="trimField('countryOfOrigin')"
            />
          </div>
          <div>
            <label for="isActive" class="block text-sm font-medium mb-1 text-gray-700">Active Status</label>
            <select
              id="isActive"
              v-model="form.isActive"
              class="itbms-isActive w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
              :ref="el => inputRefs[3] = el"
              @keydown.enter.prevent="handleEnter(3)"
            >
              <option :value="true">true</option>
              <option :value="false">false</option>
            </select>
          </div>
        </div>

        <!-- Buttons -->
        <div class="flex gap-4 pt-4">
          <button
            type="submit"
            class="itbms-save-button flex-1 py-3 rounded-xl bg-blue-600 text-white font-medium hover:bg-blue-700 transition"
            :disabled="!isFormValid || submitting"
            :class="!isFormValid || submitting ? 'opacity-50 cursor-not-allowed' : ''"
          >
            Save
          </button>
          <button
            type="button"
            @click="cancelAdd"
            class="itbms-cancel-button flex-1 py-3 rounded-xl border border-red-400 text-red-600 font-medium hover:bg-red-50 transition"
          >
            Cancel
          </button>
        </div>
      </form>
    </div>

    <Footer />
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import Header from '../components/Header.vue';
import Footer from '../components/Footer.vue';
import { AddBrand } from '../libs/fetchBrand.js';

const router = useRouter();

const form = reactive({
  name: '',
  websiteUrl: '',
  countryOfOrigin: '',
  isActive: true,
});

const inputRefs = ref([]);
const submitting = ref(false);

const trimField = (key) => {
  if (typeof form[key] === 'string') form[key] = form[key].trim();
};

const handleEnter = (index) => {
  const nextInput = inputRefs.value[index + 1];
  if (nextInput) nextInput.focus();
  else saveBrand();
};

const isFormValid = computed(() => {
  return (
    form.name.trim() !== '' &&
    form.websiteUrl.trim() !== '' &&
    form.countryOfOrigin.trim() !== ''
  );
});


const saveBrand = async () => {
  if (submitting.value || !isFormValid.value) return;

  submitting.value = true;

  const payload = {
    name: form.name.trim(),
    websiteUrl: form.websiteUrl.trim() || undefined,
    countryOfOrigin: form.countryOfOrigin.trim() || undefined,
    isActive: form.isActive,
  };

  try {
    const res = await AddBrand(payload);
    if (res) {
      router.push({ path: '/brands', query: { brandAdded: 'true' } });
    } else {
      alert('Failed to add brand.');
    }
  } catch (error) {
    console.error(error);
    alert('An error occurred while adding the brand.');
  } finally {
    submitting.value = false;
  }
};

const cancelAdd = () => {
  router.back();
};
</script>

<style scoped>
</style>
