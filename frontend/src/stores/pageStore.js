import { ref, computed } from "vue";
import { acceptHMRUpdate, defineStore } from "pinia";

export const usePageStore = defineStore("counter", () => {
  const pageNumber = ref(sessionStorage.getItem("pageNumber") || 0);

  const setPageNumber = (pageNum) => {
    pageNumber.value = pageNum;
    sessionStorage.setItem("pageNumber", pageNum);
  };

  const getPageNumber = () => {
    return Number(pageNumber.value);
  };

  return { setPageNumber, getPageNumber };
});

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(usePageStore, import.meta.hot));
}
