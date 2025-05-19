<template>
  <div class="bg-gray-50 min-h-screen">
    <Header />

    <!-- Product Detail -->
    <div v-if="brand"
      class="bg-white text-gray-800 px-8 py-12 mx-auto max-w-5xl rounded-3xl shadow-2xl mt-10 space-y-8">
     <!-- Breadcrumb -->
      <nav class="flex items-center space-x-2 text-sm text-gray-500">
        <router-link to="/sale-items/list" class="itbms-item-list font-medium text-blue-600 hover:underline">
          Sale Item List
        </router-link>
         <span> > </span>
        <router-link to="/brands" class="itbms-manage-brand text-blue-500 hover:text-blue-700 transition-colors">Brand List</router-link>
        <span> > </span>
        <b>Edit Brand</b>
      </nav>

      <div class="flex flex-col lg:flex-row gap-12">
        <!-- Left: Images -->
        <div class="lg:w-1/2 w-full">
          <img :src="getImageUrl" :alt="form.brandName + ' ' + form.model"
            class="w-full rounded-2xl shadow-lg object-cover aspect-square" />
          <div class="flex gap-4 mt-4 overflow-x-auto">
            <img v-for="(img, i) in brand.images" :key="i" :src="img" :alt="`Image ${i + 1}`"
              class="w-20 h-20 object-cover rounded-lg border border-gray-200 hover:ring-2 hover:ring-blue-300 transition cursor-pointer" />
          </div>
        </div>

        <!-- Right: Form -->
        <form @submit.prevent="saveBrand" class="lg:w-1/2 w-full space-y-6">

          <!-- Model -->
          <div class="space-y-1">
            <label for="name" class="block text-sm font-medium text-gray-700">Name</label>
            <input id="name" required v-model="form.name" type="text" placeholder="Name"
              class="itbms-name w-full border border-gray-300 rounded-xl p-3 focus:outline-none focus:ring-2 focus:ring-blue-300"
              :ref="el => inputRefs[1] = el" @keydown.enter.prevent="handleEnter(1)" @blur="trimField('name')" />
          </div>

          <!-- Other Fields -->
          <div class="grid grid-cols-2 gap-6">
            <div>
              <label class="block text-sm font-medium mb-1">WebsiteUrl</label>
              <input id="websiteUrl" v-model="form.websiteUrl" type="text"
                class="itbms-websiteUrl w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
                :ref="el => inputRefs[2] = el" @keydown.enter.prevent="handleEnter(2)" @blur="trimField('websiteUrl')"/>
            </div>
            <div>
              <label class="block text-sm font-medium mb-1">CountryOfOrigin</label>
              <input id="countryOfOrigin" v-model="form.countryOfOrigin" type="text" step="0.1"
                class="itbms-countryOfOrigin w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
                :ref="el => inputRefs[3] = el" @keydown.enter.prevent="handleEnter(3)" @blur="trimField('countryOfOrigin')"/>
            </div>
            <div>
            <div>
            <label for="isActive" class="block text-sm font-medium mb-1 text-gray-700">Active Status</label>
            <input type="checkbox" class="itbms-isActive toggle" v-model="form.isActive" @click="form.isActive = !form.isActive" />
          </div>
            </div>        
          </div>

          <!-- Buttons -->
          <div class="flex gap-4 pt-4">
            <button type="submit"
              class="itbms-save-button flex-1 py-3 rounded-xl bg-blue-600 text-white font-medium hover:bg-blue-700 transition"
              :disabled="!isFormModified"
              :class="!isFormModified ? 'disabled:opacity-50' : ''"
              @click="saveBrand"
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
      Brand not found.
    </div>

    <!-- Error Modal -->
    <transition name="fade">
      <div v-if="showErrorModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center">
        <div class="bg-white p-6 rounded-2xl shadow-lg max-w-sm text-center">
          <h2 class="text-xl font-bold text-red-600 mb-2">Brand Not Found</h2>
          <p class="mb-4 text-gray-700">The brand does not exist.</p>
          <p class="text-sm text-gray-500 mb-6">Redirecting in {{ secondsLeft }} seconds...</p>
          <button @click="goBrandList" class="px-6 py-2 rounded-xl bg-yellow-500 text-white font-medium hover:bg-yellow-600 transition">OK</button>
        </div>
      </div>
    </transition>

    <Footer />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { fetchBrandById , editBrand} from '../libs/fetchBrand.js'

import Header from "../components/Header.vue";
import Footer from "../components/Footer.vue";
import { useStateStore } from "../stores/stateStore.js";

const route = useRoute();
const router = useRouter();
const stateStore = useStateStore();
const { getImageUrl } = stateStore;

const brand = ref(null);
const inputRefs = ref([]);
const showErrorModal = ref(false);
const secondsLeft = ref(3);
let timeoutRef = null;
let countdownInterval = null;

// form state
const form = reactive({
  name: "",
  websiteUrl : "",
  countryOfOrigin: "",
  isActive : true
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
  else saveBrand();
};

onMounted(async () => {
  try {
    const data = await fetchBrandById(route.params.id);
    brand.value = data;
    console.log(brand.value);
    console.log(brand.value.name);
    
    Object.assign(form, {
         name: brand.value.name,
         websiteUrl : brand.value.websiteUrl,
         countryOfOrigin:  brand.value.countryOfOrigin,
         isActive : brand.value.isActive
    });
    console.log(form);
    
    originData.value = { ...form };
    console.log("test");
    
  } catch (err) {
    showErrorModal.value = true;
    timeoutRef = setTimeout(() => router.push("/brands"), 3000);
    countdownInterval = setInterval(() => { if (secondsLeft.value > 0) secondsLeft.value--; }, 1000);
  }
});

const saveBrand = async () => {
  try {
    const payload = {
     id : route.params.id,
     name: form.name,
     websiteUrl : form.websiteUrl,
     countryOfOrigin: form.countryOfOrigin,
     isActive : form.isActive
    };
    console.log(payload);
    const a = await editBrand(route.params.id, payload);
    
    router.push({ path: `/brands`, query: { updated: 'true' } });
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

const goBrandList = () => {
  clearTimeout(timeoutRef); clearInterval(countdownInterval);
  router.push("/brands/list");
};
</script>

<style scoped>
.fade-enter-active, .fade-leave-active { transition: opacity 0.3s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }
</style>