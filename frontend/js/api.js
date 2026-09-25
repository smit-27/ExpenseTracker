const API_BASE_URL = "http://localhost:8080/api";

async function apiRequest(endpoint, options = {}) {

    const token = localStorage.getItem("token");

    const headers = {
        "Content-Type": "application/json",
        ...options.headers
    };

    if (
       token &&
       endpoint !== "/users/login" &&
       endpoint !== "/users/register"
    ) {
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
            message = error.message || error.error || message;
        } catch (e) {
        }

        throw new Error(message);
    }

    if (response.status === 204) {
        return null;
    }

    return response.json();
}