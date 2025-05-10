<template>
  <div class="home">
    <div
      class="bg-gradient-to-br from-zinc-900 to-black text-white min-h-screen font-[Poppins] flex flex-col justify-between">
      <!-- Header -->
      <Header />

      <!-- Hero Section -->
      <section class="text-center py-24 px-6">
        <h2 class="text-5xl font-bold mb-6 tracking-wide text-white drop-shadow-lg">
          ยินดีต้อนรับสู่ <span class="text-[#facc15]">ITB MSHOP</span>
        </h2>
        <p class="text-gray-400 text-xl mb-10 max-w-2xl mx-auto leading-relaxed">
          พบกับมือถือรุ่นล่าสุด ดีไซน์พรีเมียม พร้อมข้อเสนอสุดพิเศษที่คุณไม่ควรพลาด
        </p>

        <router-link to="/sale-items"
          class="Itbms-shopnow inline-block bg-white text-black px-10 py-3 rounded-full font-semibold text-lg shadow-lg hover:bg-gray-200 transition-all duration-300">
          SHOP NOW
        </router-link>
      </section>

      <!-- Footer -->
      <Footer />
    </div>
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'
</script>