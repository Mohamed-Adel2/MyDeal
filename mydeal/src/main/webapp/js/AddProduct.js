// Define a global array to store the selected images
var imagesList = [];

function removeFileFromFileInput(file) {

    const newFiles = Array.from(fileInput.files).filter(f => f !== file);
    fileInput.files = newFiles;
    fileInput.dispatchEvent(new Event('change', { bubbles: true }));
}


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
        "Category": "1",
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

/*function SendJsonToServlet(jsonData) {
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
    console.log("Json Data before send "+jsonData);
    xhr.send(jsonData);
}*/


