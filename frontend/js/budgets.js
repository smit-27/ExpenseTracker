const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "index.html";
}


document.addEventListener(
    "DOMContentLoaded",
    () => {

        setDefaultMonth();

        loadCategories();
        loadBudgetReport();

    }
);


/* =========================
   Categories
   ========================= */

async function loadCategories() {

    try {

        const categories =
            await apiRequest("/categories");

        const select =
            document.getElementById(
                "budget-category"
            );

        select.innerHTML =
            `<option value="">
                Select expense category
            </option>`;


        categories
            .filter(category =>
                category.type === "EXPENSE"
            )
            .forEach(category => {

                const option =
                    document.createElement("option");

                option.value =
                    category.categoryId;

                option.textContent =
                    category.name;

                select.appendChild(option);

            });

    } catch (error) {

        showMessage(
            error.message,
            true
        );
    }
}


/* =========================
   Budget Report
   ========================= */

async function loadBudgetReport() {

    try {

        const budgets =
            await apiRequest("/budgets/report");

        const container =
            document.getElementById(
                "budget-report"
            );

        container.innerHTML = "";


        if (
            !budgets ||
            budgets.length === 0
        ) {

            container.innerHTML = `
                <p class="empty-message">
                    No budgets created yet.
                </p>
            `;

            return;
        }


        budgets.forEach(budget => {

            const budgetAmount =
                Number(budget.budgetAmount) || 0;

            const spentAmount =
                Number(budget.spentAmount) || 0;

            const remaining =
                budgetAmount - spentAmount;

            const percentage =
                budgetAmount > 0
                    ? (spentAmount / budgetAmount) * 100
                    : 0;

            const progress =
                Math.min(percentage, 100);


            const isOverBudget =
                spentAmount > budgetAmount;


            const card =
                document.createElement("div");

            card.className =
                "budget-report-card";


            card.innerHTML = `

                <div class="budget-report-header">

                    <div>

                        <h3>
                            ${escapeHtml(
                                budget.categoryName
                            )}
                        </h3>

                        <p>
                            Budget:
                            ₹${budgetAmount.toFixed(2)}
                        </p>

                    </div>


                    <button
                        class="delete-button"
                        onclick="deleteBudget(
                            ${budget.budgetId}
                        )">

                        Delete

                    </button>

                </div>


                <div class="budget-stats">

                    <div>

                        <span>Spent</span>

                        <strong>
                            ₹${spentAmount.toFixed(2)}
                        </strong>

                    </div>


                    <div>

                        <span>
                            ${isOverBudget
                                ? "Over Budget"
                                : "Remaining"}
                        </span>

                        <strong class="${
                            isOverBudget
                                ? "over-budget"
                                : "remaining-budget"
                        }">

                            ₹${Math.abs(
                                remaining
                            ).toFixed(2)}

                        </strong>

                    </div>


                    <div>

                        <span>Used</span>

                        <strong>
                            ${percentage.toFixed(1)}%
                        </strong>

                    </div>

                </div>


                <div class="progress-bar budget-progress">

                    <div
                        class="progress-fill ${
                            isOverBudget
                                ? "over-progress"
                                : ""
                        }"
                        style="width: ${progress}%">
                    </div>

                </div>


                <div class="budget-status">

                    ${
                        isOverBudget

                        ? `<span class="status-danger">
                            ⚠ Over budget by
                            ₹${Math.abs(
                                remaining
                            ).toFixed(2)}
                           </span>`

                        : percentage >= 80

                        ? `<span class="status-warning">
                            ⚠ Approaching budget limit
                           </span>`

                        : `<span class="status-good">
                            ✓ Within budget
                           </span>`
                    }

                </div>

            `;


            container.appendChild(card);

        });


    } catch (error) {

        showMessage(
            error.message,
            true
        );
    }
}


/* =========================
   Add Budget
   ========================= */

document
    .getElementById("budget-form")
    .addEventListener(
        "submit",
        async event => {

            event.preventDefault();


            const categoryId =
                Number(
                    document.getElementById(
                        "budget-category"
                    ).value
                );


            const budgetAmount =
                Number(
                    document.getElementById(
                        "budget-amount"
                    ).value
                );


            const month =
                document.getElementById(
                    "budget-month"
                ).value;


            if (!month) {

                showMessage(
                    "Please select a budget month.",
                    true
                );

                return;
            }


            /*
             * LocalDate expected by Spring Boot:
             *
             * YYYY-MM-DD
             *
             * We use the first day of the
             * selected month.
             */

            const budgetMonth =
                `${month}-01`;


            try {

                await apiRequest(
                    "/budgets",
                    {
                        method: "POST",

                        body: JSON.stringify({

                            category: {
                                categoryId
                            },

                            budgetAmount,

                            budgetMonth

                        })
                    }
                );


                showMessage(
                    "Budget added successfully.",
                    false
                );


                document
                    .getElementById(
                        "budget-form"
                    )
                    .reset();


                setDefaultMonth();

                await loadBudgetReport();


            } catch (error) {

                showMessage(
                    error.message,
                    true
                );
            }

        }
    );


/* =========================
   Delete Budget
   ========================= */

async function deleteBudget(budgetId) {

    if (
        !confirm(
            "Are you sure you want to delete this budget?"
        )
    ) {
        return;
    }


    try {

        await apiRequest(
            `/budgets/${budgetId}`,
            {
                method: "DELETE"
            }
        );


        showMessage(
            "Budget deleted successfully.",
            false
        );


        await loadBudgetReport();


    } catch (error) {

        showMessage(
            error.message,
            true
        );
    }
}


/* =========================
   Helpers
   ========================= */

function setDefaultMonth() {

    const now =
        new Date();

    const year =
        now.getFullYear();

    const month =
        String(
            now.getMonth() + 1
        ).padStart(2, "0");


    document.getElementById(
        "budget-month"
    ).value =
        `${year}-${month}`;
}


function showMessage(message, error) {

    const element =
        document.getElementById(
            "budget-message"
        );

    element.textContent =
        message;

    element.className =
        error
            ? "error-message"
            : "success-message";
}


function escapeHtml(value) {

    const div =
        document.createElement("div");

    div.textContent =
        value;

    return div.innerHTML;
}


/* =========================
   Logout
   ========================= */

document
    .getElementById("logout-button")
    .addEventListener(
        "click",
        () => {

            localStorage.clear();

            window.location.href =
                "index.html";

        }
    );