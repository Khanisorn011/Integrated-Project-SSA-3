import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";

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

      component: () => import("../views/SaleItems.vue"),
    },
    {
      path: "/sale-items/:id",
      name: "SaleItemDetail",
      component: () => import("../views/SaleItemDetail.vue"),
      props: route => ({
        imageUrl: route.state?.imageUrl || ''
      })
    },
  ],
});

export default router;
