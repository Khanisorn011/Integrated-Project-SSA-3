<template>
  <header
    class="sticky top-0 z-50 bg-white shadow-md border-b border-gray-200 px-6 py-4 flex flex-col md:flex-row justify-between items-center space-y-4 md:space-y-0">
    
    <!-- Logo -->
    <router-link
      to="/"
      class="text-3xl font-extrabold text-gray-800 tracking-tight hover:text-blue-600 transition">
      ITB MSHOP
    </router-link>

    <!-- Controls -->
    <div class="flex flex-col sm:flex-row items-center gap-3 w-full md:w-auto">
      
      <!-- Search Box -->
      <div class="flex items-center w-full sm:w-auto bg-gray-100 border border-gray-300 rounded-full px-4 py-2 shadow-sm focus-within:ring-2 focus-within:ring-blue-400 transition">
        <input
          type="text"
          placeholder="ค้นหาสินค้า..."
          class="w-full sm:w-60 bg-transparent text-gray-700 text-sm focus:outline-none placeholder-gray-400" />
        <i class="fas fa-search text-gray-500 ml-3 text-sm"></i>
      </div>

      <!-- Cart Button -->
      <button
        class="relative bg-white text-gray-800 border border-gray-300 px-4 py-2 rounded-full font-medium flex items-center gap-2 hover:bg-gray-100 transition shadow-sm">
        <i class="fas fa-shopping-cart"></i>
        <span>ตะกร้า</span>
      </button>

      <!-- Login Button -->
      <button
        class="bg-blue-600 text-white px-5 py-2 rounded-full font-medium flex items-center gap-2 hover:bg-blue-700 transition shadow-sm">
        <i class="fas fa-user"></i>
        <span>เข้าสู่ระบบ</span>
      </button>
    </div>
  </header>
</template>
