import { defineStore , acceptHMRUpdate } from 'pinia';
import { ref , computed } from 'vue';

export const useStateStore = defineStore('imageStore', () => {
  const imageUrl = ref(JSON.parse(sessionStorage.getItem('imageUrl')) || null)
  const errorModal = ref(false)

  const setImageUrl = (url) => {
    imageUrl.value = url
    sessionStorage.setItem('imageUrl',JSON.stringify(url))
  }


  const getImageUrl = computed(() => imageUrl.value || null);
 
  return {
    setImageUrl,
    getImageUrl
  }
});

if(import.meta.hot){
    import.meta.hot.accept(acceptHMRUpdate(useStateStore,import.meta.hot))
}