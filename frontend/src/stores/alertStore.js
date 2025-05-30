import { defineStore, acceptHMRUpdate } from "pinia";
import { ref, computed } from 'vue';

export const useAlertStore = defineStore('alertStore', () => {
    const alerts = ref({})
    const setModuleAlert = (moduleName, status) => {
        alerts.value = {
            ...alerts.value,
            [moduleName]: status
        };

    };
    const getModuleAlert = computed(() => (moduleName) => {
        return alerts.value[moduleName];
    });
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
