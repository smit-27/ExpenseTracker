const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "index.html";
}


document.addEventListener("DOMContentLoaded", () => {

    const username =
        localStorage.getItem("username");

    document.getElementById("welcome-message").textContent =
        `Welcome back, ${username}!`;

    loadDashboard();
    loadTransactions();
    loadBudgetReport();

});


async function loadDashboard() {

    try {

        const dashboard =
            await apiRequest("/dashboard");

        document.getElementById("total-income").textContent =
            formatCurrency(dashboard.totalIncome);

        document.getElementById("total-expense").textContent =
            formatCurrency(dashboard.totalExpense);

        document.getElementById("balance").textContent =
            formatCurrency(dashboard.balance);

    } catch (error) {

        console.error(
            "Failed to load dashboard:",
            error
        );

        handleAuthenticationError(error);
    }
}


async function loadTransactions() {

    try {

        const transactions =
            await apiRequest("/transactions");

        const container =
            document.getElementById("recent-transactions");

        if (!transactions || transactions.length === 0) {

            container.innerHTML =
                `<p class="empty-message">
                    No transactions yet.
                </p>`;

            return;
        }


        const recent =
            transactions.slice(0, 5);


        container.innerHTML =
            recent.map(transaction => {

                const category =
                    transaction.category?.name || "Unknown";

                return `
                    <div class="transaction-row">

                        <div>
                            <strong>${escapeHtml(category)}</strong>

                            <p>
                                ${escapeHtml(
                                    transaction.description || ""
                                )}
                            </p>
                        </div>

                        <div class="transaction-amount">
                            ₹${Number(
                                transaction.amount
                            ).toFixed(2)}
                        </div>

                    </div>
                `;

            }).join("");


    } catch (error) {

        console.error(
            "Failed to load transactions:",
            error
        );
    }
}


async function loadBudgetReport() {

    try {

        const budgets =
            await apiRequest("/budgets/report");

        const container =
            document.getElementById("budget-overview");

        if (!budgets || budgets.length === 0) {

            container.innerHTML =
                `<p class="empty-message">
                    No budgets created yet.
                </p>`;

            return;
        }


        const recentBudgets =
            budgets.slice(0, 4);


        container.innerHTML =
            recentBudgets.map(budget => {

                const budgetAmount =
                    Number(budget.budgetAmount);

                const spentAmount =
                    Number(budget.spentAmount);

                const percentage =
                    budgetAmount > 0
                        ? Math.min(
                            (spentAmount / budgetAmount) * 100,
                            100
                        )
                        : 0;

                return `
                    <div class="budget-row">

                        <div class="budget-info">

                            <strong>
                                ${escapeHtml(
                                    budget.categoryName
                                )}
                            </strong>

                            <span>
                                ₹${spentAmount.toFixed(2)}
                                /
                                ₹${budgetAmount.toFixed(2)}
                            </span>

                        </div>

                        <div class="progress-bar">

                            <div
                                class="progress-fill"
                                style="width: ${percentage}%">
                            </div>

                        </div>

                    </div>
                `;

            }).join("");


    } catch (error) {

        console.error(
            "Failed to load budgets:",
            error
        );
    }
}


function formatCurrency(value) {

    return new Intl.NumberFormat(
        "en-IN",
        {
            style: "currency",
            currency: "INR"
        }
    ).format(Number(value) || 0);
}


function escapeHtml(value) {

    const div =
        document.createElement("div");

    div.textContent = value;

    return div.innerHTML;
}


function handleAuthenticationError(error) {

    if (
        error.message.includes("401") ||
        error.message.includes("Unauthorized")
    ) {

        localStorage.clear();

        window.location.href =
            "index.html";
    }
}


document
    .getElementById("logout-button")
    .addEventListener("click", () => {

        localStorage.clear();

        window.location.href =
            "index.html";
    });