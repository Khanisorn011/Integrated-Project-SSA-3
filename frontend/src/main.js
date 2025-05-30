import './assets/main.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router/index.js'
import { useAlertStore } from './stores/alertStore'; // แก้ path ให้ถูกต้อง

const app = createApp(App)
const pinia = createPinia()
app.use(pinia)
app.use(router)

router.beforeEach((to, from, next) => {
    const alertStore = useAlertStore()
    if (to.name !== 'Brand') {
     alertStore.clearModuleAlert('brand'); 
    }
    if (to.name !== 'SaleItemDetail' && to.name !== 'sale-items' && to.name !== 'SaleItemList') {
     alertStore.clearModuleAlert('saleItem');
    }
    next(); 
});

app.mount('#app')

