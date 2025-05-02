<template>
    <router-link :to="`/sale-items/${product.id}`" class="block">
      <div
        class="itbms-row bg-white text-black rounded-xl shadow-md p-4 hover:-translate-y-1 transition-transform w-full max-w-xs mx-auto min-h-[350px] flex flex-col justify-between">
  
        <!-- Image -->
        <div
          class="h-48 bg-zinc-200 rounded-lg mb-4 bg-center bg-cover"
          :style="{ backgroundImage: `url(${product.image})` }">
        </div>
  
        <!-- Product Info -->
        <div class="text-sm leading-snug space-y-1">
          <p class="itbms-brand font-bold">{{ product.brandName || '-' }}</p>
          <p class="itbms-model">{{ product.model || '-' }}</p>
          <p>
            <span class="itbms-ramGb">{{ product.ramGb || '-' }}</span>/
            <span class="itbms-storageGb">{{ product.storageGb || '-' }}</span>
            <span class="itbms-storageGb-unit">GB</span>
          </p>
          <p class="itbms-color">{{ product.color || '-' }}</p>
        </div>
  
        <!-- Price -->
        <p class="text-lg font-semibold mt-3">
          <span class="itbms-price-unit">Baht </span>
          <span class="itbms-price">{{ product.price?.toLocaleString?.() || '-' }}</span>
        </p>
      </div>
    </router-link>
  </template>
  
  <script setup>
  defineProps({
    product: {
      type: Object,
      required: true
    }
  })
  </script>
  