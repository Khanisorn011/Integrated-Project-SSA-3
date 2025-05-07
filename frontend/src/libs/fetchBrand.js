async function fetchBrands() {
    try {
        const res = await fetch('http://ip24ssa3.sit.kmutt.ac.th:8080/itb-mshop/v1/brands')
        const data = await res.json()
        return data
    } catch (err) {
        throw err
    }
}
export { fetchBrands }
