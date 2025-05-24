<template>
  <div>
    <div class="join" v-if="totalPages > 1">
      <button @click="$emit('update:currentPage', 0)" :disabled="currentPage === 0" class="join-item btn">First</button>
      <button @click="$emit('update:currentPage', currentPage - 1)" :disabled="currentPage === 0" class="join-item btn">Prev</button>

      <button
        v-for="page in visiblePages"
        :key="page"
        @click="$emit('update:currentPage', page)"
        :class="['join-item btn', currentPage === page && 'btn-primary']"
      >
        {{ page + 1 }}
      </button>

      <button @click="$emit('update:currentPage', currentPage + 1)" :disabled="currentPage === totalPages - 1" class="join-item btn">Next</button>
      <button @click="$emit('update:currentPage', totalPages - 1)" :disabled="currentPage === totalPages - 1" class="join-item btn">Last</button>
    </div>

    <div class="mt-4">
      <label>Show</label>
      <select :value="pageSize" @change="$emit('update:pageSize', Number($event.target.value))" class="ml-2 text-black">
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
