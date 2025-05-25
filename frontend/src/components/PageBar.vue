<template>
  <div class="p-4 bg-gray-100 rounded-xl shadow-md flex flex-col md:flex-row md:items-center md:justify-between gap-4">
    <!-- Pagination Controls -->
    <div class="join flex flex-wrap items-center gap-2" v-if="totalPages > 1">
      <button @click="$emit('update:currentPage', 0)" :disabled="currentPage === 0" class="join-item btn btn-sm px-4">First</button>
      <button @click="$emit('update:currentPage', currentPage - 1)" :disabled="currentPage === 0" class="join-item btn btn-sm px-4">Prev</button>

      <button
        v-for="page in visiblePages"
        :key="page"
        @click="$emit('update:currentPage', page)"
        :class="['join-item btn btn-sm px-3', currentPage === page && 'btn-primary']"
      >
        {{ page + 1 }}
      </button>

      <button @click="$emit('update:currentPage', currentPage + 1)" :disabled="currentPage === totalPages - 1" class="join-item btn btn-sm px-4">Next</button>
      <button @click="$emit('update:currentPage', totalPages - 1)" :disabled="currentPage === totalPages - 1" class="join-item btn btn-sm px-4">Last</button>
    </div>

    <!-- Page Size Selector -->
    <div class="flex items-center text-sm">
      <label class="mr-2 font-medium">Show</label>
      <select :value="pageSize" @change="$emit('update:pageSize', Number($event.target.value))" class="px-2 py-1 rounded-md border border-gray-300 text-black bg-white shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-400">
        <option :value="5">5</option>
        <option :value="10">10</option>
        <option :value="20">20</option>
      </select>
    </div>
  </div>
</template>


<script setup>
import { computed } from 'vue';

const props = defineProps({
  currentPage: Number,
  pageSize: Number,
  totalPages: Number,
});

const maxVisible = 10;
const visibleStart = computed(() =>
  Math.max(0, Math.min(props.currentPage - Math.floor(maxVisible / 2), props.totalPages - maxVisible))
);

const visiblePages = computed(() => {
  const pages = [];
  const end = Math.min(visibleStart.value + maxVisible, props.totalPages);
  for (let i = visibleStart.value; i < end; i++) {
    pages.push(i);
  }
  return pages;
});
</script>
