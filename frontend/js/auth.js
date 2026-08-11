const loginSection = document.getElementById("login-section");
const registerSection = document.getElementById("register-section");

const loginForm = document.getElementById("login-form");
const registerForm = document.getElementById("register-form");

const showRegisterButton =
    document.getElementById("show-register");

const showLoginButton =
    document.getElementById("show-login");

const message = document.getElementById("message");


showRegisterButton.addEventListener("click", () => {

    loginSection.classList.add("hidden");
    registerSection.classList.remove("hidden");

    message.textContent = "";
});


showLoginButton.addEventListener("click", () => {

    registerSection.classList.add("hidden");
    loginSection.classList.remove("hidden");

    message.textContent = "";
});


loginForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    const username =
        document.getElementById("login-username").value;

    const password =
        document.getElementById("login-password").value;

    try {

        const response = await apiRequest(
            "/users/login",
            {
                method: "POST",
                body: JSON.stringify({
                    username,
                    password
                })
            }
        );

        localStorage.setItem("token", response.token);

        localStorage.setItem(
            "username",
            response.username
        );

        localStorage.setItem(
            "userId",
            response.userId
        );

        message.textContent = "Login successful!";

        window.location.href = "dashboard.html";

    } catch (error) {

        message.textContent = error.message;
    }
});


registerForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    const username =
        document.getElementById("register-username").value;

    const password =
        document.getElementById("register-password").value;

    try {

        await apiRequest(
            "/users/register",
            {
                method: "POST",
                body: JSON.stringify({
                    username,
                    password
                })
            }
        );

        message.textContent =
            "Registration successful. Please login.";

        registerForm.reset();

        registerSection.classList.add("hidden");
        loginSection.classList.remove("hidden");

    } catch (error) {

        message.textContent = error.message;
    }
});