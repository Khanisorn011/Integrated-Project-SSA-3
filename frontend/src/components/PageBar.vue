<template>
  <div>
    <div class="join" v-if="pageResponse.totalPages > 1">
      <button @click="goToFirstPage" :disabled="currentPage === 0" class="join-item btn">First</button>
      <button @click="goToPreviousPage" :disabled="currentPage === 0" class="join-item btn">Prev</button>

      <button
        v-for="page in visiblePages"
        :key="page"
        @click="goToPage(page)"
        :class="['join-item btn', currentPage === page && 'btn-primary']"
      >
        {{ page + 1 }}
      </button>

      <button @click="goToNextPage" :disabled="currentPage === pageResponse.totalPages - 1" class="join-item btn">Next</button>
      <button @click="goToLastPage" :disabled="currentPage === pageResponse.totalPages - 1" class="join-item btn">Last</button>
    </div>

    <div class="mt-4">
      <label>Show</label>
      <select v-model="payload.size" class="ml-2 text-black">
        <option :value="5">5</option>
        <option :value="10">10</option>
        <option :value="20">20</option>
      </select>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, computed, onMounted } from 'vue';
import { fetchSaleItemByCondition } from '../libs/fetchSaleItem.js';
import { usePageStore } from '../stores/pageStore.js';
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  selectedBrands: Array,
  saleItems: Array,
});
const emit = defineEmits(['updateSaleItems']);

const currentPage = ref(0);
const pageResponse = ref({});
const pageStore = usePageStore();
const maxVisible = 10;
const visibleStart = ref(0);
const saleItems = ref([]);


const payload = ref({
  filterBrands: [],
  page: 0,
  size: 100,
  sortField: 'createdOn',
  sortDirection: null,
});

const visiblePages = computed(() => {
  const total = pageResponse.value.totalPages || 0;
  return Array.from(
    { length: Math.min(maxVisible, total - visibleStart.value) },
    (_, i) => visibleStart.value + i
  );
});

watch(() => props.selectedBrands, async (newBrands) => {
  payload.value.filterBrands = newBrands;
  payload.value.page = 0;
  currentPage.value = 0;
  pageResponse.value = await fetchSaleItemByCondition(payload.value);
  saleItems.value = pageResponse.value.content;
  emit('updateSaleItems', saleItems.value);
});

watch(() => payload.value.size, async () => {
  payload.value.page = 0;
  currentPage.value = 0;
  pageResponse.value = await fetchSaleItemByCondition(payload.value);
  saleItems.value = pageResponse.value.content;
  emit('updateSaleItems', saleItems.value);
});

watch(currentPage, async () => {
  payload.value.page = currentPage.value;
  pageResponse.value = await fetchSaleItemByCondition(payload.value);
  saleItems.value = pageResponse.value.content;
  emit('updateSaleItems', saleItems.value);
});

onMounted(async () => {
  pageResponse.value = await fetchSaleItemByCondition(payload.value);
  saleItems.value = pageResponse.value.content;
  emit('updateSaleItems',saleItems.value)
});

function goToPage(page) {
  currentPage.value = page;
}
function goToNextPage() {
  if (currentPage.value < pageResponse.value.totalPages - 1) {
    currentPage.value++;
    if (currentPage.value > visibleStart.value + maxVisible - 1) {
      visibleStart.value++;
    }
  }
}
function goToPreviousPage() {
  if (currentPage.value > 0) {
    currentPage.value--;
    if (currentPage.value < visibleStart.value) {
      visibleStart.value--;
    }
  }
}
function goToFirstPage() {
  currentPage.value = 0;
  visibleStart.value = 0;
}
function goToLastPage() {
  currentPage.value = pageResponse.value.totalPages - 1;
  visibleStart.value = Math.max(0, pageResponse.value.totalPages - maxVisible);
}
</script>

