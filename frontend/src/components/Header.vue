<template>
  <header

    class="flex flex-col md:flex-row justify-between items-center px-6 py-4 bg-white sticky top-0 z-50 shadow-md border-b border-gray-200"
  >
    <!-- Logo (Click to go home) -->
    <router-link
      to="/"
      class="text-2xl font-bold text-gray-800 tracking-wide hover:text-gray-600 transition"
    >
      ITB MSHOP
    </router-link>

    <div class="flex items-center gap-3 mt-3 md:mt-0">
      <!-- Search Box -->
      <div
        class="flex items-center bg-gray-100 border border-gray-300 rounded-full px-3 py-1"
      >
        <input
          type="text"
          placeholder="ค้นหาสินค้า..."
          class="bg-transparent text-gray-700 outline-none placeholder-gray-400 w-40 md:w-60"
        />
        <i class="fas fa-search text-gray-500 ml-2"></i>
      </div>

      <!-- Cart Button -->
      <button
        class="relative bg-white text-gray-800 border border-gray-300 px-4 py-2 rounded-full font-medium flex items-center gap-2 hover:bg-gray-50 transition"
      >
        <i class="fas fa-shopping-cart"></i> ตะกร้า
      </button>

      <!-- Login Button -->
      <button
        class="bg-black text-white px-4 py-2 rounded-full font-medium flex items-center gap-2 hover:opacity-90 transition"
      >
        <i class="fas fa-user"></i> เข้าสู่ระบบ
      </button>
    </div>
  </header>
</template>
<script setup></script>
<style scoped></style>
