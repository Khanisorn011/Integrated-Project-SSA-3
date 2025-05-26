async function fetchProducts() {
    try {
      const res = await fetch('http://intproj24.sit.kmutt.ac.th:8080/ssa3/itb-mshop/v1/sale-items')
      const data = await res.json()
      return data
    } catch (err) {
      throw err 
    }
  }


async function fetchProductById(id) {
  try {
    const res = await fetch(
      `http://intproj24.sit.kmutt.ac.th:8080/ssa3/itb-mshop/v1/sale-items/${id}`
    );
    const data = await res.json();

    if (!res.ok) {
      throw new Error(`แก้ไขสินค้าไม่สำเร็จ (HTTP ${res.status})`);
    }

    return data;
  } catch (error) {
    throw error;
  }
}

async function postProduct(payload) {
  try {
    const response = await fetch(
      "http://intproj24.sit.kmutt.ac.th:8080/ssa3/itb-mshop/v1/sale-items",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      }
    );
    return response;
  } catch (error) {
    throw error;
  }
}

async function editProduct(id, payload) {
  try {
    const response = await fetch(
      `http://intproj24.sit.kmutt.ac.th:8080/ssa3/itb-mshop/v1/sale-items/${id}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      }
    );

    if (!response.ok) {
      throw new Error(`แก้ไขสินค้าไม่สำเร็จ (HTTP ${response.status})`);
    }

    return response;
  } catch (error) {
    throw error;
  }
}

async function deleteProduct(id) {
  try {
    const response = await fetch(
      `http://intproj24.sit.kmutt.ac.th:8080/ssa3/itb-mshop/v1/sale-items/${id}`,
      {
        method: "DELETE",
      }
    );
    return response;
  } catch (error) {
    return {error};
  }
}

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
      throw new Error(`แก้ไขสินค้าไม่สำเร็จ (HTTP ${response.status})`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    return { error };
  }
}

export {
  fetchProducts,
  fetchProductById,
  postProduct,
  editProduct,
  deleteProduct,
  fetchSaleItemByCondition
};
