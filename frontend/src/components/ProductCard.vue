<template>
  <router-link
    @click="setImage(imageUrl)"
    :to="{
      name: 'SaleItemDetail',
      params: { id: product.id },
    }"
    class="block"
  >
    <div
      class="bg-white text-black rounded-xl shadow-md p-4 hover:-translate-y-1 transition-transform w-full max-w-xs mx-auto min-h-[350px] flex flex-col justify-between"
    >
      <!-- Image -->
      <div
        class="h-48 bg-zinc-200 rounded-lg mb-4 bg-center bg-cover"
        :style="{ backgroundImage: `url(${imageUrl})` }"
      ></div>

      <!-- Product Info -->
      <div class="text-sm leading-snug space-y-1">
        <p
          class="Itbms-brand font-bold"
          :class="{ 'text-gray-400': !product.brandName?.trim() }"
        >
          {{ product.brandName || "-" }}
        </p>
        <p
          class="Itbms-model"
          :class="{ 'text-gray-400': !product.model?.trim() }"
        >
          {{ product.model || "-" }}
        </p>
        <p>
          <span
            class="Itbms-ramGb"
            :class="{ 'text-gray-400': !product.ramGb?.toString().trim() }"
          >
            {{ product.ramGb || "-" }}
          </span>
          /
          <span
            class="Itbms-storageGb"
            :class="{ 'text-gray-400': !product.storageGb?.toString().trim() }"
          >
            {{ product.storageGb || "-" }}
          </span>
          <span class="Itbms-storageGb-unit">GB</span>
        </p>
        <p
          class="Itbms-color"
          :class="{ 'text-gray-400': !product.color?.trim() }"
        >
          {{ product.color || "-" }}
        </p>
      </div>

      <!-- Price -->
      <p class="text-lg font-semibold mt-3">
        <span class="Itbms-price-unit">Baht</span>
        <span class="Itbms-price pl-1.5">
          {{ product.price?.toLocaleString?.() || "-" }}
        </span>
      </p>
    </div>
  </router-link>
</template>

<script setup>
import {useImageStore} from '../stores/imageStore.js'
const imageStore = useImageStore()
const {setImageUrl} = imageStore
const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
  imageUrl: {
    type: String,
  },
});

const setImage = (imageUrl) => {
  setImageUrl(imageUrl)
}
</script>
