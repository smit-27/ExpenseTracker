const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "index.html";
}


document.addEventListener(
    "DOMContentLoaded",
    loadCategories
);


async function loadCategories() {

    try {

        const categories =
            await apiRequest("/categories");

        const tbody =
            document.getElementById(
                "categories-table-body"
            );

        tbody.innerHTML = "";


        if (
            !categories ||
            categories.length === 0
        ) {

            tbody.innerHTML = `
                <tr>

                    <td colspan="4"
                        class="empty-table">

                        No categories found.

                    </td>

                </tr>
            `;

            return;
        }


        categories.forEach(category => {

            const row =
                document.createElement("tr");


            row.innerHTML = `

                <td>
                    ${category.categoryId}
                </td>

                <td>
                    ${escapeHtml(category.name)}
                </td>

                <td>

                    <span
                        class="type-badge
                        ${category.type.toLowerCase()}">

                        ${category.type}

                    </span>

                </td>

                <td>

                    <button
                        class="delete-button"
                        onclick="deleteCategory(
                            ${category.categoryId}
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
    .getElementById("category-form")
    .addEventListener(
        "submit",
        async event => {

            event.preventDefault();


            const name =
                document.getElementById(
                    "category-name"
                ).value;


            const type =
                document.getElementById(
                    "category-type"
                ).value;


            try {

                await apiRequest(
                    "/categories",
                    {
                        method: "POST",

                        body: JSON.stringify({

                            name,

                            type

                        })
                    }
                );


                showMessage(
                    "Category added successfully.",
                    false
                );


                document
                    .getElementById(
                        "category-form"
                    )
                    .reset();


                await loadCategories();


            } catch (error) {

                showMessage(
                    error.message,
                    true
                );
            }

        }
    );


async function deleteCategory(categoryId) {

    if (
        !confirm(
            "Deleting a category may fail if it is being used by transactions or budgets. Continue?"
        )
    ) {
        return;
    }


    try {

        await apiRequest(
            `/categories/${categoryId}`,
            {
                method: "DELETE"
            }
        );


        showMessage(
            "Category deleted successfully.",
            false
        );


        await loadCategories();


    } catch (error) {

        showMessage(
            error.message,
            true
        );
    }
}


function showMessage(message, error) {

    const element =
        document.getElementById(
            "category-message"
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