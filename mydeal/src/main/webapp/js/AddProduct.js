// Define a global array to store the selected images
var imagesList = [];
var selectedCategoryNow

function removeFileFromFileInput(file) {

    const newFiles = Array.from(fileInput.files).filter(f => f !== file);
    fileInput.files = newFiles;
    fileInput.dispatchEvent(new Event('change', {bubbles: true}));
}

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('add-form').addEventListener('submit', function (event) {
        event.preventDefault();
    });
    getCategoriesFromServlet();
});

// Function to display all images
function displayImages(files) {
    const imageListDiv = document.getElementById('imageList');

    for (let i = 0; i < files.length; i++) {
        const file = files[i];

        const reader = new FileReader();
        reader.onload = function (event) {
            const imgElement = document.createElement('img');
            imgElement.src = event.target.result;
            imgElement.className = 'uploaded-image';


            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'X';
            deleteButton.className = 'delete-button';
            deleteButton.addEventListener('click', function () {
                const containerDiv = this.parentElement;
                imageListDiv.removeChild(containerDiv);

                const imageIndex = imagesList.findIndex(img => img === file);
                if (imageIndex !== -1) {
                    imagesList.splice(imageIndex, 1);
                }
                console.log("deleted");
                removeFileFromFileInput(file);
            });


            const containerDiv = document.createElement('div');
            containerDiv.className = 'image-container';
            containerDiv.appendChild(imgElement);
            containerDiv.appendChild(deleteButton);


            imageListDiv.appendChild(containerDiv);


            imagesList.push(file);
            validateProductImage();
        };
        reader.readAsDataURL(file);
    }
}


const fileInput = document.getElementById('fileInput');
const addProductButton = document.getElementById('addProductButton');

fileInput.addEventListener('change', function () {
    const files = this.files;

    if (files.length > 0) {
        displayImages(files);
    }
});


addProductButton.addEventListener('click', function () {
    var productNameValid = validateProductName();
    var productDescriptionValid = validateProductDescription();
    var productPriceValid = validateProductPrice();
    var productQuantityValid = validateQuantity();
    var categoryValid = categoryValidation();
    var productImageValid = validateProductImage();

    if (productNameValid && productDescriptionValid && productPriceValid && productQuantityValid && categoryValid && productImageValid) {
        convertFilesToByteArrays(imagesList);
    }
});

function makeGson(BytesArray) {
    const productName = document.getElementById('productName').value;
    const productDescription = document.getElementById('productDescription').value;
    const productPrice = document.getElementById('productPrice').value;
    const productQuantity = document.getElementById('productQuantity').value;
    //const category = document.querySelector('input[name="category"]:checked').value;
    const formattedBytesArray = BytesArray.map(byteArray => Array.from(byteArray));

    // Prepare product data as JSON object
    var jsonData = {
        "ProductName": productName,
        "Description": productDescription,
        "Price": productPrice,
        "AvailableQuantity": productQuantity,
        "Category": selectedCategoryNow,
        "Rating": "0.0",
        "Images": formattedBytesArray,
    };
    const xhr = new XMLHttpRequest();
    const servletURL = 'adminAdd';
    xhr.open('POST', servletURL, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            const responseData = JSON.parse(xhr.responseText);
            console.log('Response from server:', responseData);

            if (responseData.success) {
                customAlert('Product has been added successfully', true);
            } else {
                customAlert('Failed to add the product', false);
            }
        } else {
            console.error('Request failed with status:', xhr.status);
        }
    };

    xhr.onerror = function () {
        //   console.log(jsonData);

        console.error('Request failed');
    };
    //  console.log("Data "+JSON.stringify(jsonData));
    xhr.send(JSON.stringify(jsonData));

    //  SendJsonToServlet(productData);

}

function fileToByteArray(file, callback) {
    const reader = new FileReader();
    reader.onload = function (event) {
        const arrayBuffer = event.target.result;
        const bytes = new Uint8Array(arrayBuffer);
        callback(bytes);
    };
    reader.readAsArrayBuffer(file);
}

function convertFilesToByteArrays(filesList) {
    const byteArrays2D = [];
    let currentIndex = 0;
    const handleFile = () => {
        if (currentIndex >= filesList.length) {
            makeGson(byteArrays2D);
            return;
        }
        const file = filesList[currentIndex];
        fileToByteArray(file, function (byteArray) {
            byteArrays2D.push(Array.from(byteArray));
            currentIndex++;
            handleFile();
        });
    };
    handleFile();
}

function getCategoriesFromServlet() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var categories = JSON.parse(jsonResponse);
                console.log(jsonResponse);
                appearCategoriesOnScreen(categories);
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };

    xhr.open('GET', 'categories', true);

    xhr.send();
}

function appearCategoriesOnScreen(categories) {


    const categoryContainer = document.getElementById('categoryContainer');


    categories.forEach((category, index) => {

        const radioButton = document.createElement('input');
        radioButton.setAttribute('type', 'radio');
        radioButton.setAttribute('name', 'category');
        radioButton.setAttribute('id', category);
        radioButton.setAttribute('value', category);


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
            console.log('Selected category:', selectedCategory);
            selectedCategoryNow = selectedCategory;

        });
    });
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

function validateProductName() {
    var productName = document.getElementById('productName').value;
    if (productName === '' || productName.length < 3) {
        $('#productNameValidity').text('Please enter a product name with at least 3 characters').css('color', 'red');
        $('#productNameValidity').show();
        return false;
    } else {
        $('#productNameValidity').hide();
        $('#productNameValidity').text('');
        return true;
    }
}

function validateProductDescription() {
    var productDescription = document.getElementById('productDescription').value;
    if (productDescription === '' || productDescription.length < 20) {
        $('#productDescriptionValidity').text('Please enter a product description with at least 20 characters').css('color', 'red');
        $('#productDescriptionValidity').show();
        return false;
    } else {
        $('#productDescriptionValidity').hide();
        $('#productDescriptionValidity').text('');
        return true;
    }
}

function validateProductPrice() {
    var productPrice = document.getElementById('productPrice').value;
    if (productPrice === '' || productPrice < 1) {
        $('#priceValidity').text('Please a valid price').css('color', 'red');
        $('#priceValidity').show();
        return false;
    } else {
        $('#priceValidity').hide();
        $('#priceValidity').text('');
        return true;
    }
}

function validateQuantity() {
    var productQuantity = document.getElementById('productQuantity').value;
    if (productQuantity === '' || productQuantity < 1) {
        $('#quantityValidity').text('Please enter a valid quantity').css('color', 'red');
        $('#quantityValidity').show();
        return false;
    } else {
        $('#quantityValidity').hide();
        $('#quantityValidity').text('');
        return true;
    }
}

function categoryValidation() {
    var category = document.querySelector('input[name="category"]:checked');
    if (category === null) {
        $('#categoryValidity').text('Please select a category').css('color', 'red');
        $('#categoryValidity').show();
        return false;
    } else {
        $('#categoryValidity').hide();
        $('#categoryValidity').text('');
        return true;
    }
}

function validateProductImage() {
    var parentDiv = document.getElementById('imageList');
    var childDivs = parentDiv.getElementsByTagName('div');
    if (childDivs.length > 0) {
        $('#imageValidity').hide();
        $('#imageValidity').text('');
        return true;
    } else {
        $('#imageValidity').text('Please upload at least one image').css('color', 'red');
        $('#imageValidity').show();
        return false;
    }
}

$('#productName').on('blur', function () {
    validateProductName();
});

$('#productDescription').on('blur', function () {
    validateProductDescription();
});

$('#productPrice').on('blur', function () {
    validateProductPrice();
});

$('#productQuantity').on('blur', function () {
    validateQuantity();
});

$('#categoryContainer').on('change', function () {
    categoryValidation();
});