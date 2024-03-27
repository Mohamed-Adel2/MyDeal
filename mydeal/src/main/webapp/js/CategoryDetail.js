var selectedCategoryNow;
document.addEventListener('DOMContentLoaded', function () {
    checkAuth();
});
const DeleteCategoryButton = document.getElementById('DeleteCategory');
const AddCategoryButton = document.getElementById('AddCategory');
DeleteCategoryButton.addEventListener('click', function () {
    deleteCategoryFromServlet();
});
AddCategoryButton.addEventListener('click', function () {
    if (!validateCategory())
        return;
    addCategoryToServlet();
})

function getCategoreiesFromServlet() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var categories = JSON.parse(jsonResponse);
                console.log(jsonResponse);
                appearCategories(categories);
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };

    xhr.open('GET', 'categories', true);

    xhr.send();
}

function appearCategories(categories) {
    //  categories = ["Category1 ", "Category2", "Category3"];
    const categoryContainer = document.getElementById('categoryContainer');

    categories.forEach((category, index) => {

        const radioButton = document.createElement('input');
        radioButton.setAttribute('type', 'radio');
        radioButton.setAttribute('name', 'category');
        radioButton.setAttribute('id', category);
        radioButton.setAttribute('value', category);
        radioButton.classList.add('form-check-input');


        const label = document.createElement('label');
        label.setAttribute('for', category);
        label.textContent = category;

        const div = document.createElement('div');
        div.classList.add('form-check');
        div.appendChild(radioButton);
        div.appendChild(label);
        categoryContainer.querySelector('fieldset').appendChild(div);
        radioButton.addEventListener('change', function () {
            const selectedCategory = this.value;
            selectedCategoryNow = selectedCategory;

        });
    });

}

function deleteCategoryFromServlet() {
    var jsonData = {
        "categoryName": selectedCategoryNow,
    };
    const xhr = new XMLHttpRequest();
    const servletURL = 'admin/adminDeleteCategory';
    xhr.open('POST', servletURL, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            const responseData = JSON.parse(xhr.responseText);
            console.log('Response from server:', responseData);

            if (responseData.success) {
                customAlert('Category Deleted successfully!', true);
            } else {
                customAlert('Failed to Delete Category', false);
            }
        } else {
            console.error('Request failed with status:', xhr.status);
        }
    };

    xhr.onerror = function () {

        console.error('Request failed');
    };
    xhr.send(JSON.stringify(jsonData));
}

function addCategoryToServlet() {
    const CategoryName = document.getElementById('CategoryName').value;
    var jsonData = {
        "categoryName": CategoryName,
    };
    const xhr = new XMLHttpRequest();
    const servletURL = 'admin/adminAddCategory';
    xhr.open('POST', servletURL, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            const responseData = JSON.parse(xhr.responseText);
            console.log('Response from server:', responseData);

            if (responseData.success) {
                customAlert('Category Added successfully!', true);
            } else {
                customAlert('Failed to Add Category', false);
            }
        } else {
            console.error('Request failed with status:', xhr.status);
        }
    };

    xhr.onerror = function () {

        console.error('Request failed');
    };
    xhr.send(JSON.stringify(jsonData));
}

function checkAuth() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if (response === 'admin') {

                    getCategoreiesFromServlet();
                } else {
                    //need to redirect to home screen
                    window.location.href = 'index.html';
                }

            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };

    xhr.open('GET', 'checkStatus', true);

    xhr.send();
}

function customAlert(message, state) {
    // Create overlay
    var overlay = document.createElement('div');
    overlay.style.position = 'fixed';
    overlay.style.top = 0;
    overlay.style.left = 0;
    overlay.style.width = '100%';
    overlay.style.height = '100%';
    overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.5)';
    overlay.style.zIndex = 999;
    document.body.appendChild(overlay);

    // Create alert container
    var alertContainer = document.createElement('div');
    alertContainer.id = 'custom-alert';

    // Create message element
    var messageElement = document.createElement('p');
    messageElement.textContent = message;
    alertContainer.appendChild(messageElement);

    // Create OK button
    var okButton = document.createElement('button');
    okButton.textContent = 'OK';
    okButton.addEventListener('click', function () {
        alertContainer.style.display = 'none';
        overlay.style.display = 'none';
        if (state)
            location.reload();
    });
    alertContainer.appendChild(okButton);

    // Append alert container to body
    document.body.appendChild(alertContainer);

    // Show the alert
    alertContainer.style.display = 'block';
}

$('#CategoryName').on('blur', function () {
    validateCategory();
});

function validateCategory() {
    var CategoryName = document.getElementById('CategoryName').value;
    if (CategoryName === "") {
        document.getElementById('CategoryName').style.borderColor = "red";
        $('#categoryValidity').text('You must enter the Category name').css('color', 'red');
        $('#categoryValidity').show();
        return false;
    } else {
        document.getElementById('CategoryName').style.borderColor = "grey";
        $('#categoryValidity').show();
        $('#categoryValidity').text('');
        return true;
    }
}