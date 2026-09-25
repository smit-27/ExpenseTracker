const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "index.html";
}


document.addEventListener("DOMContentLoaded", () => {

    setDefaultDate();

    loadCategories();
    loadTransactions();

});


async function loadCategories() {

    try {

        const categories =
            await apiRequest("/categories");

        const select =
            document.getElementById(
                "transaction-category"
            );

        select.innerHTML =
            `<option value="">Select category</option>`;


        categories.forEach(category => {

            const option =
                document.createElement("option");

            option.value =
                category.categoryId;

            option.textContent =
                `${category.name} (${category.type})`;

            select.appendChild(option);

        });

    } catch (error) {

        showMessage(
            error.message,
            true
        );
    }
}


async function loadTransactions() {

    try {

        const transactions =
            await apiRequest("/transactions");

        const tbody =
            document.getElementById(
                "transactions-table-body"
            );

        tbody.innerHTML = "";


        if (
            !transactions ||
            transactions.length === 0
        ) {

            tbody.innerHTML = `
                <tr>
                    <td colspan="6"
                        class="empty-table">
                        No transactions found.
                    </td>
                </tr>
            `;

            return;
        }


        transactions.forEach(transaction => {

            const row =
                document.createElement("tr");

            const category =
                transaction.category;

            const type =
                category?.type || "EXPENSE";

            const amount =
                Number(transaction.amount);


            row.innerHTML = `
                <td>
                    ${transaction.transactionDate}
                </td>

                <td>
                    ${escapeHtml(
                        category?.name || "Unknown"
                    )}
                </td>

                <td>
                    ${escapeHtml(
                        transaction.description || "-"
                    )}
                </td>

                <td>
                    <span class="type-badge ${type.toLowerCase()}">
                        ${type}
                    </span>
                </td>

                <td class="${type.toLowerCase()}-amount">
                    ₹${amount.toFixed(2)}
                </td>

                <td>
                    <button
                        class="delete-button"
                        onclick="deleteTransaction(
                            ${transaction.transactionId}
                        )">
                        Delete
                    </button>
                </td>
            `;

            tbody.appendChild(row);

        });

    } catch (error) {

        showMessage(
            error.message,
            true
        );
    }
}


document
    .getElementById("transaction-form")
    .addEventListener("submit", async event => {

        event.preventDefault();


        const categoryId =
            Number(
                document.getElementById(
                    "transaction-category"
                ).value
            );

        const amount =
            Number(
                document.getElementById(
                    "transaction-amount"
                ).value
            );

        const description =
            document.getElementById(
                "transaction-description"
            ).value;

        const transactionDate =
            document.getElementById(
                "transaction-date"
            ).value;


        try {

            await apiRequest(
                "/transactions",
                {
                    method: "POST",

                    body: JSON.stringify({

                        category: {
                            categoryId
                        },

                        amount,

                        description,

                        transactionDate

                    })
                }
            );


            showMessage(
                "Transaction added successfully.",
                false
            );


            document
                .getElementById("transaction-form")
                .reset();


            setDefaultDate();

            await loadTransactions();

        } catch (error) {

            showMessage(
                error.message,
                true
            );
        }

    });


async function deleteTransaction(transactionId) {

    if (
        !confirm(
            "Are you sure you want to delete this transaction?"
        )
    ) {
        return;
    }


    try {

        await apiRequest(
            `/transactions/${transactionId}`,
            {
                method: "DELETE"
            }
        );


        showMessage(
            "Transaction deleted successfully.",
            false
        );


        await loadTransactions();

    } catch (error) {

        showMessage(
            error.message,
            true
        );
    }
}


function setDefaultDate() {

    document.getElementById(
        "transaction-date"
    ).value =
        new Date()
            .toISOString()
            .split("T")[0];
}


function showMessage(message, error) {

    const element =
        document.getElementById(
            "transaction-message"
        );

    element.textContent = message;

    element.className =
        error
            ? "error-message"
            : "success-message";
}


function escapeHtml(value) {

    const div =
        document.createElement("div");

    div.textContent = value;

    return div.innerHTML;
}


document
    .getElementById("logout-button")
    .addEventListener("click", () => {

        localStorage.clear();

        window.location.href =
            "index.html";

    });