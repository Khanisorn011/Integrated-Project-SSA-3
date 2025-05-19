import { defineConfig } from "cypress";

export default defineConfig({
  e2e: {
    specPattern: 'cypress/e2e/**/*.{cy,spec}.{js,jsx,ts,tsx}',
    experimentalRunAllSpecs: true,
    baseUrl: 'http://ip24ssa3.sit.kmutt.ac.th:80',
    baseAPI: 'http://ip24ssa3.sit.kmutt.ac.th:8080/itb-mshop',
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});
