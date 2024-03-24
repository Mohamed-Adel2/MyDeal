var ImagesIdsDeleted = [];
var ImagesFromServer =[];
const selectedFiles = [];
var ProductData;
var selectedCategoryNow;
document.addEventListener('DOMContentLoaded', function () {
    checkAuth();
    console.log("listener");
  //  getCategoriesFromDatabase();
    const fileInput = document.getElementById('fileInput');
    fileInput.addEventListener('change', function (event) {
        const files = event.target.files;
        displayUploadedImages(files);
        for (let i = 0; i < files.length; i++) {
            selectedFiles.push(files[i]);
        }
    });
});

const UpdateProductButton = document.getElementById('UpdateProductButton');
UpdateProductButton.addEventListener('click', function() {
    updateDataOnServlet();
});
function getURLParameter(name) {
    let params = new URLSearchParams(window.location.search);

    return params.get(name);
}
function getDetailOFProductFromServlet(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var product = JSON.parse(jsonResponse);
                ProductData = product;

                console.log(product);
                appearProductThatComeFromServer(product);
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    let paramValue = getURLParameter('Id');
    console.log(paramValue);
    var parms = {
        Id: paramValue
    }
    var paramStr = Object.keys(parms).map(key => key + '=' + encodeURIComponent(parms[key])).join('&');
    xhr.open('GET', 'productDetail?' + paramStr, true);
    xhr.send();
}
function appearProductThatComeFromServer(productDetailData){

    document.getElementById('productName').value = productDetailData.productName;

    document.getElementById('productDescription').value = productDetailData.description;

    document.getElementById('productPrice').value = productDetailData.price;

    document.getElementById('productQuantity').value = productDetailData.availableQuantity;


    const categoryContainer = document.getElementById('categoryContainer').querySelector('fieldset');
    const radioInputs = categoryContainer.querySelectorAll('input[type="radio"]');

    console.log(radioInputs);
    radioInputs.forEach(radioInput => {
       console.log(radioInput.value+" "+productDetailData.category);
        if (radioInput.value === productDetailData.category) {
            radioInput.checked = true;
            selectedCategoryNow = radioInput.value;
        }
    });

    const imageListDiv = document.getElementById('imageList');
    let i=0;
    let Ids=productDetailData.IdsOfImages;
    ImagesFromServer = Ids;
    productDetailData.images.forEach(buffer => {
        const imgElement = document.createElement('img');
        const base64String = arrayBufferToBase64(buffer); // Convert byte array to Base64
        imgElement.src = `data:image/jpeg;base64,${base64String}`;

        imgElement.className = 'uploaded-image';

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'X';
        deleteButton.className = 'delete-button';
        deleteButton.id = Ids[i];

        i++;
        deleteButton.addEventListener('click', function() {
            const containerDiv = this.parentElement;
            console.log("Yes ", this.id);
            imageListDiv.removeChild(containerDiv);
            if(isDeleteButtonIdInList(this.id, ImagesFromServer)){
                console.log("Yes ", this.id);
                ImagesIdsDeleted.push(parseInt(this.id));
                console.log("I am deleted Images "+ImagesIdsDeleted.length);
            }
        });

        const containerDiv = document.createElement('div');
        containerDiv.className = 'image-container';
        containerDiv.appendChild(imgElement);
        containerDiv.appendChild(deleteButton);

        imageListDiv.appendChild(containerDiv);
    });
}
function getCategoriesFromDatabase(){
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
function appearCategories(categories){


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
        radioButton.addEventListener('change', function() {
            const selectedCategory = this.value;
            console.log('Selected category:', selectedCategory);
            selectedCategoryNow = selectedCategory;

        });
    });
    getDetailOFProductFromServlet();

}
function arrayBufferToBase64(buffer) {
    let binary = '';
    const bytes = new Uint8Array(buffer);
    const len = bytes.byteLength;
    for (let i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return btoa(binary);
}
function isDeleteButtonIdInList(deleteButtonId, list) {
    console.log(list.length)
    for (let i = 0; i < list.length; i++) {
        console.log(list[i] , deleteButtonId);
        if (list[i] == deleteButtonId) {
         //   console.log(list[i]);
            return true;
        }
    }
    return false;
}
function updateDataOnServlet(){
    convertAddedImagesToByteArrays(selectedFiles);
}
function displayUploadedImages(files) {
    const imageListDiv = document.getElementById('imageList');

    for (let i = 0; i < files.length; i++) {
        const file = files[i];

        const reader = new FileReader();
        reader.onload = function(event) {
            const imgElement = document.createElement('img');
            imgElement.src = event.target.result;
            imgElement.className = 'uploaded-image';
            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'X';
            deleteButton.className = 'delete-button';
            deleteButton.addEventListener('click', function() {
                const containerDiv = this.parentElement;
                imageListDiv.removeChild(containerDiv);
                const index = selectedFiles.indexOf(file);
                if (index !== -1) {
                    selectedFiles.splice(index, 1);
                }
                console.log(selectedFiles.length);
            });
            const containerDiv = document.createElement('div');
            containerDiv.className = 'image-container';
            containerDiv.appendChild(imgElement);
            containerDiv.appendChild(deleteButton);

            imageListDiv.appendChild(containerDiv);
        };
        reader.readAsDataURL(file);
    }
}
function PrepareGson(byteArrays2D){
    const ProductId = ProductData.id;
    const productName = document.getElementById('productName').value;
    const productDescription = document.getElementById('productDescription').value;
    const productPrice = document.getElementById('productPrice').value;
    const productQuantity = document.getElementById('productQuantity').value;
    const formattedBytesArray = byteArrays2D.map(byteArray => Array.from(byteArray));
    var  jsonData = {
        "ProductId":ProductId,
        "ProductName": productName,
        "Description": productDescription,
        "price": productPrice,
        "quantity": productQuantity,
        "category": selectedCategoryNow,
        "deleted": ImagesIdsDeleted,
        "added":formattedBytesArray,
    };
    const xhr = new XMLHttpRequest();
    const servletURL = 'admin/adminUpdate';
    xhr.open('POST', servletURL, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            const responseData = JSON.parse(xhr.responseText);
            console.log('Response from server:', responseData);

            if (responseData.success) {
                alert('Product and images added successfully');
            } else {
                alert('Failed to add product or images: ' + responseData.message);
            }
        } else {
            console.error('Request failed with status:', xhr.status);
        }
    };

    xhr.onerror = function() {

        console.error('Request failed');
    };
    xhr.send(JSON.stringify(jsonData));
}
function convertAddedImagesToByteArrays(filesList) {
    const byteArrays2D = [];
    let currentIndex = 0;
    const handleFile = () => {
        if (currentIndex >= filesList.length) {
            PrepareGson(byteArrays2D);
            return;
        }
        const file = filesList[currentIndex];
        fileToByteArray(file, function(byteArray) {
            byteArrays2D.push(Array.from(byteArray));
            currentIndex++;
            handleFile();
        });
    };
    handleFile();
}
function fileToByteArray(file, callback) {
    const reader = new FileReader();
    reader.onload = function(event) {
        const arrayBuffer = event.target.result;
        const bytes = new Uint8Array(arrayBuffer);
        callback(bytes);
    };
    reader.readAsArrayBuffer(file);
}
function checkAuth(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if( response==='admin'){
                    getCategoriesFromDatabase();
                  //  getCategoreiesFromServlet();
                }else{
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