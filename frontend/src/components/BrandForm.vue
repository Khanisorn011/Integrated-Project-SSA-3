<template>
  <div>
    <form @submit.prevent="handleSubmit" class="space-y-6">
      <!-- Name -->
      <div class="space-y-1">
        <label for="name" class="block text-sm font-medium text-gray-700">
          Name <span class="text-red-500">*</span>
        </label>
        <input
          id="name"
          v-model="form.name"
          type="text"
          placeholder="Brand Name"
          required
          class="itbms-name w-full border border-gray-300 rounded-xl p-3 focus:outline-none focus:ring-2 focus:ring-blue-300"
          :ref="(el) => (inputRefs[0] = el)"
          @keydown.enter.prevent="handleEnter(0)"
          @blur="trimField('name')"
        />
      </div>

      <!-- Other Fields -->
      <div class="grid grid-cols-2 gap-6">
        <div>
          <label
            for="websiteUrl"
            class="block text-sm font-medium mb-1 text-gray-700"
            >Website URL</label
          >
          <input
            id="websiteUrl"
            v-model="form.websiteUrl"
            type="url"
            placeholder="https://example.com"
            class="itbms-websiteUrl w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
            :ref="(el) => (inputRefs[1] = el)"
            @keydown.enter.prevent="handleEnter(1)"
            @blur="trimField('websiteUrl')"
          />
        </div>
        <div>
          <label
            for="countryOfOrigin"
            class="block text-sm font-medium mb-1 text-gray-700"
            >Country of Origin</label
          >
          <input
            id="countryOfOrigin"
            v-model="form.countryOfOrigin"
            type="text"
            placeholder="e.g., Japan"
            class="itbms-countryOfOrigin w-full border border-gray-300 rounded-xl p-2 focus:outline-none focus:ring-2 focus:ring-blue-300"
            :ref="(el) => (inputRefs[2] = el)"
            @keydown.enter.prevent="handleEnter(2)"
            @blur="trimField('countryOfOrigin')"
          />
        </div>
        <div>
          <label
            for="isActive"
            class="block text-sm font-medium mb-1 text-gray-700"
            >Active Status</label
          >
          <input
            type="checkbox"
            class="itbms-isActive toggle"
            v-model="form.isActive"
            @click="form.isActive = !form.isActive"
          />
        </div>
      </div>

      <!-- Buttons -->
      <div class="flex gap-4 pt-4">
        <button
          type="submit"
          class="itbms-save-button flex-1 py-3 rounded-xl bg-blue-600 text-white font-medium hover:bg-blue-700 transition"
          :disabled="!isFormValid || submitting"
          :class="!isFormValid || submitting ? 'opacity-50 cursor-not-allowed' : ''"
        >
          Save
        </button>
        <button
          type="button"
          @click="cancelAdd"
          class="itbms-cancel-button flex-1 py-3 rounded-xl border border-red-400 text-red-600 font-medium hover:bg-red-50 transition"
        >
          Cancel
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from "vue";
import { useRouter } from "vue-router";

const emits = defineEmits(['payload']);

const inputRefs = ref([]);
const submitting = ref(false);

const props = defineProps({
  saveBrand: Function,
});

const form = reactive({
  name: "",
  websiteUrl: "",
  countryOfOrigin: "",
  isActive: true,
});

const payload = computed(() => ({
  name: form.name.trim(),
  websiteUrl: form.websiteUrl.trim() || undefined,
  countryOfOrigin: form.countryOfOrigin.trim() || undefined,
  isActive: form.isActive,
}));

const trimField = (key) => {
  if (typeof form[key] === "string") form[key] = form[key].trim();
};

const handleEnter = (index) => {
  const nextInput = inputRefs.value[index + 1];
  if (nextInput) nextInput.focus();
};

const cancelAdd = () => {
  router.back();
};

const isFormValid = computed(() => {
  return form.name.trim() !== "";
});

const handleSubmit = () => {
  props.saveBrand?.();
  emits('payload', payload.value);
};
</script>
