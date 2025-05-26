async function fetchSaleItemByCondition(payload) {
  try {
    const request = new URLSearchParams(payload).toString();
    const response = await fetch(
      `http://intproj24.sit.kmutt.ac.th:8080/ssa3/itb-mshop/v2/sale-items?${request}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
        // body: JSON.stringify(payload),
      }
    );

    if (!response.ok) {
      throw new Error(`Failed to edit product (HTTP ${response.status})`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    return { error };
  }
}

export {fetchSaleItemByCondition}
