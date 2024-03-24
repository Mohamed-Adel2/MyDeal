// Define a global array to store the selected images
var imagesList = [];
var selectedCategoryNow
function removeFileFromFileInput(file) {

    const newFiles = Array.from(fileInput.files).filter(f => f !== file);
    fileInput.files = newFiles;
    fileInput.dispatchEvent(new Event('change', { bubbles: true }));
}
document.addEventListener('DOMContentLoaded', function () {
    //getCategoriesFromServlet();
    checkAuth();

});

// Function to display all images
function displayImages(files) {
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
            console.log("Added");
        };
        reader.readAsDataURL(file);
    }
}


const fileInput = document.getElementById('fileInput');
const addProductButton = document.getElementById('addProductButton');

fileInput.addEventListener('change', function() {
    const files = this.files;

    if (files.length > 0) {
        displayImages(files);
    }
});


addProductButton.addEventListener('click', function() {
    convertFilesToByteArrays(imagesList);


});
function makeGson(BytesArray){
    const productName = document.getElementById('productName').value;
    const productDescription = document.getElementById('productDescription').value;
    const productPrice = document.getElementById('productPrice').value;
    const productQuantity = document.getElementById('productQuantity').value;
    //const category = document.querySelector('input[name="category"]:checked').value;
    const formattedBytesArray = BytesArray.map(byteArray => Array.from(byteArray));

    // Prepare product data as JSON object
     var  jsonData = {
        "ProductName": productName,
        "Description": productDescription,
        "Price": productPrice,
        "AvailableQuantity": productQuantity,
        "Category": selectedCategoryNow,
        "Rating":"0.0",
        "Images": formattedBytesArray,
    };
    const xhr = new XMLHttpRequest();
    const servletURL = 'adminAdd';
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
        //   console.log(jsonData);

        console.error('Request failed');
    };
  //  console.log("Data "+JSON.stringify(jsonData));
    xhr.send(JSON.stringify(jsonData));

  //  SendJsonToServlet(productData);

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
function convertFilesToByteArrays(filesList) {
    const byteArrays2D = [];
    let currentIndex = 0;
    const handleFile = () => {
        if (currentIndex >= filesList.length) {
            makeGson(byteArrays2D);
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
function getCategoriesFromServlet(){
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
function appearCategoriesOnScreen(categories){


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
}
function checkAuth(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if( response==='admin'){
                    getCategoriesFromServlet();
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


