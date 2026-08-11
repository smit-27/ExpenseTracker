const API_BASE_URL = "http://localhost:8080/api";

async function apiRequest(endpoint, options = {}) {

    const token = localStorage.getItem("token");

    const headers = {
        "Content-Type": "application/json",
        ...options.headers
    };

    if (token) {
        headers["Authorization"] = `Bearer ${token}`;
    }

    const response = await fetch(
        `${API_BASE_URL}${endpoint}`,
        {
            ...options,
            headers
        }
    );

    if (!response.ok) {

        let message = "Something went wrong";

        try {
            const error = await response.json();
            message = error.message || message;
        } catch (e) {
            // Response may not contain JSON
        }

        throw new Error(message);
    }

    if (response.status === 204) {
        return null;
    }

    return response.json();
}