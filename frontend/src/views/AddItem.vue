<template>
    <div>
        <Header />

        <!-- Add Product Form -->
        <div class="bg-white text-black px-6 py-10 mx-auto max-w-6xl rounded-3xl shadow-xl mt-6 space-y-6">
            <nav class="text-xl text-gray-700 px-1 pt-6 max-w-6xl mx-auto">
                <router-link to="/sale-items" class="itbms-home-button font-medium text-blue-600 hover:underline">
                    Home <span> ></span>
                </router-link>
                <span class="ml-2 text-black font-medium">Add New Product</span>
            </nav>

            <form @submit.prevent="handleSubmit" class="flex flex-col md:flex-row gap-10">
                <!-- Product Image Previews -->
                <div class="md:w-1/2 w-full">
                    <img :src="getImageUrl" class="w-full rounded-2xl shadow-md object-cover aspect-square" />
                    <div class="flex gap-3 mt-4 overflow-x-auto">
                        <img v-for="(img, index) in form.images" :key="index" :src="img"
                            class="w-20 h-20 object-cover rounded-lg border hover:ring-2 hover:ring-blue-400 transition-all duration-200 cursor-pointer" />
                    </div>
                </div>

                <!-- Product Info Form -->
                <div class="md:w-1/2 w-full space-y-4 text-base itbms-row">
                    <span class=" text-red-500 font-medium "> Required*</span>
                    <select v-model="form.brandId"
                        class="itbms-brand input text-gray-500 w-full border-black p-2 rounded" required>
                        <option value="" disabled>Select a brand</option>
                        <option v-for="(brand, index) in brands" :key="brand.id || index" :value="brand.id">
                            {{ brand.name }}
                        </option>
                    </select>
                    <input @blur="trim('model')" v-model="form.model" type="text" placeholder="Model"
                        class="itbms-model w-full border p-2 rounded" />
                    <div class="grid grid-cols-2 gap-y-3 gap-x-4  text-sm">
                        <input v-model.number="form.price" type="number" placeholder="Price (Baht)"
                            class="itbms-price border p-2 rounded" />
                        <input v-model="form.quantity" type="number" placeholder="Quantity"
                            class="itbms-quantity border p-2 rounded" />
                    </div>
                    <textarea v-model="form.description" placeholder="Description"
                        class="itbms-description w-full border p-2 rounded" rows="3"></textarea>

                    <p class=" text-black font-medium"> Optional </p>
                    <div class="grid grid-cols-2 gap-y-3 gap-x-4 text-sm">
                        <input v-model="form.ramGb" type="number" placeholder="RAM (GB)"
                            class="itbms-ramGb border p-2 rounded" />
                        <input v-model="form.screenSizeInch" type="number" step="0.1" placeholder="Screen Size (Inch)"
                            class="itbms-screenSizeInch border p-2 rounded" />
                        <input v-model="form.storageGb" type="number" placeholder="Storage Size"
                            class="itbms-storageGb border p-2 rounded" />
                        <input v-model="form.color" type="text" placeholder="Color"
                            class="itbms-color border p-2 rounded" />
                    </div>

                    <button type="submit" :disabled="!isFormValid" :class="[
                        'itbms-save-button px-5 py-2 rounded-lg transition',
                        isFormValid
                            ? 'bg-blue-600 text-white hover:bg-blue-700'
                            : 'bg-white text-gray-400 border border-gray-300 cursor-not-allowed'
                    ]">
                        Save
                    </button>
                    <button type="cancel" @click="router.push('/sale-items')"
                        class="itbms-cancel-button ml-2 px-5 py-2 bg-red-500 text-white rounded-lg hover:bg-red-700 transition">
                        Cancal
                    </button>
                </div>
            </form>
        </div>

        <Footer />
    </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { useRouter } from 'vue-router'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { postProduct } from '../libs/fetchProduct.js'
import { fetchBrands } from "../libs/fetchBrand.js"
import { useStateStore } from "../stores/stateStore.js";

const stateStore = useStateStore();
const { getImageUrl } = stateStore;
const router = useRouter()
const brands = ref([])
const form = ref({
    brandId: '',
    brandName: '',
    model: '',
    price: null,
    ramGb: null,
    storageGb: null,
    screenSizeInch: null,
    color: '',
    quantity: null,
    description: '',
})

const trim = (field) => {
    if (form.value[field]) {
        form.value[field] = form.value[field].trim();
    }
};

onMounted(async () => {
    try {
        brands.value = await fetchBrands()
    } catch (err) {
        console.error("Failed to load")
    }
})

const submitting = ref(false);
const handleSubmit = async () => {
    if (submitting.value) return
    submitting.value = true
    const payload = {
        model: form.value.model.trim(),
        description: form.value.description.trim(),
        price: Number(form.value.price),
        quantity: Number(form.value.quantity),
        color: form.value.color.trim() || undefined,
        ramGb: form.value.ramGb ? Number(form.value.ramGb) : undefined,
        screenSizeInch: form.value.screenSizeInch ? Number(form.value.screenSizeInch) : undefined,
        storageGb: form.value.storageGb ? Number(form.value.storageGb) : undefined,
        brand: {
            id: form.value.brandId,
            name: form.value.brandName,
        },
    }

    try {
        const res = await postProduct(payload)
        if (res && res.ok) {
            router.push({ path: '/sale-items', query: { added: 'true' } })
        } else {
            alert('Failed to add product.')
        }
    } catch (error) {
        console.error(error)
        alert('An error occurred.')
    }
    submitting.value = false;
}
watch(() => form.value.brandId, (selectedId) => {
    const selected = brands.value.find(b => b.id === selectedId)
    form.value.brandName = selected ? selected.name : ''
})

const isFormValid = computed(() =>
    form.value.brandId && form.value.model && form.value.price !== null &&
    form.value.quantity !== null && form.value.description.trim() !== ''
)
</script>