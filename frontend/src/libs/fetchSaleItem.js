async function fetchSaleItems() {
    try {
      const res = await fetch(`${import.meta.env.VITE_APP_URL}/sale-items`)
      const data = await res.json()
      return data
    } catch (err) {
      throw err 
    }
  }


async function fetchSaleItemById(id) {
  try {
    const res = await fetch(
      `${import.meta.env.VITE_APP_URL}/sale-items/${id}`
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

async function postSaleItem(payload) {
  try {
    const response = await fetch(
      `${VITE_APP_URL}/sale-items`,
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

async function editSaleItem(id, payload) {
  try {
    const response = await fetch(
      `${import.meta.env.VITE_APP_URL}/sale-items/${id}`,
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

async function deleteSaleItem(id) {
  try {
    const response = await fetch(
      `${import.meta.env.VITE_APP_URL}/sale-items/${id}`,
      {
        method: "DELETE",
      }
    );
    return response;
  } catch (error) {
    return {error}
  }
}

async function fetchSaleItemByCondition(payload) {
  try {
    const request = new URLSearchParams(payload).toString();
    const response = await fetch(
      `${import.meta.env.VITE_APP_URL_2}/sale-items?${request}`,
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
  fetchSaleItems,
  fetchSaleItemById,
  postSaleItem,
  editSaleItem,
  deleteSaleItem,
  fetchSaleItemByCondition
};
