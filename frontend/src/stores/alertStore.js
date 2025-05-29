import { defineStore, acceptHMRUpdate } from "pinia";
import { ref, computed } from 'vue';

export const useAlertStore = defineStore('alertStore', () => {
    // เก็บเป็น object ที่มี key เป็นชื่อโมดูล และ value เป็นสถานะของโมดูลนั้นๆ
    // เช่น { brand: 'success', saleItem: 'error' }
    const alerts = ref({}); // ใช้ Map แทน Object ก็ได้ถ้าต้องการประสิทธิภาพที่ดีกว่ากับการเพิ่ม/ลบบ่อยๆ

    // ฟังก์ชันสำหรับตั้งค่า alert ของโมดูลนั้นๆ
    const setModuleAlert = (moduleName, status) => {
        alerts.value = {
            ...alerts.value,
            [moduleName]: status
        };

    };

    // ฟังก์ชันสำหรับดึง alert ของโมดูลนั้นๆ
    const getModuleAlert = computed(() => (moduleName) => {
        return alerts.value[moduleName];
    });

    // ฟังก์ชันสำหรับล้าง alert ของโมดูลนั้นๆ
    const clearModuleAlert = (moduleName) => {
        const newAlerts = { ...alerts.value };
        delete newAlerts[moduleName];
        alerts.value = newAlerts;
    };

    return { setModuleAlert, getModuleAlert, clearModuleAlert };
});

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useAlertStore, import.meta.hot));
}