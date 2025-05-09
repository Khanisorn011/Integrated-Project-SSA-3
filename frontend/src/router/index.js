import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import SaleItems from "../views/SaleItems.vue"
import SaleItemDetail from "../views/SaleItemDetail.vue"
import EditSaleItemDetail from "../views/EditSaleItemDetail.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/sale-items",
      name: "sale-items",
      component: SaleItems
    },
    {
      path: "/sale-items/:id",
      name: "SaleItemDetail",
      component: SaleItemDetail,
      props: route => ({
        imageUrl: route.state?.imageUrl || ''
      })
    },
    {
      path: "/sale-items/:id/edit",
      name: "EditSaleItemDetail",
      component: EditSaleItemDetail,
      props: route => ({
        imageUrl: route.state?.imageUrl || ''
      })
    }
  ],
});

export default router;
