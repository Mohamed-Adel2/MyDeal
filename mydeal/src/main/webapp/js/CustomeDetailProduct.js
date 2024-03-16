document.addEventListener('DOMContentLoaded', function () {
    console.log("listener");
    getDetailFromServlet();
});
function getDetailFromServlet(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var product = JSON.parse(jsonResponse);
                console.log(product.id+" "+product.productName);
                displayProduct(product);
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    let paramValue = getURLParameter('Id');
    console.log(paramValue);
    var parms={
        Id:paramValue
    }
    var paramStr = Object.keys(parms).map(key => key + '=' + encodeURIComponent(parms[key])).join('&');
    xhr.open('GET', 'productDetail?'+paramStr, true);
    xhr.send();
}
function getURLParameter(name) {
    // Get the URL parameters part
    let params = new URLSearchParams(window.location.search);

    return params.get(name);
}
async function displayProduct(product){
    var productImgSlide = document.querySelector('.product_img_slide');
    var productName = document.querySelector('.single_product_text h3');
    var productDescription = document.querySelector('.single_product_text p');
    var productPrice = document.querySelector('.product_count_area p span:first-child');
    var productQuantityInput = document.querySelector('.product_count_item.input-number');
    var productQuantityPrice = document.querySelector('.product_count_area p:last-child');
    productName.innerText = product.productName;
    productDescription.innerText = product.description;
    var priceSpan = document.createElement('span');
    priceSpan.textContent = '$' + product.price;
    product.images.forEach(function(imageArray) {

        var img = document.createElement('img');
        //const resizedBlob =  resizeImage(new Blob([new Uint8Array(imageArray)], { type: 'image/jpeg' }), 300, 200, 'blob');
        var blob = new Blob([new Uint8Array(imageArray)], {type: 'image/jpeg'});
        img.src = URL.createObjectURL(blob);



        var singleProductImg = document.createElement('div');
        singleProductImg.classList.add('single_product_img');
        singleProductImg.appendChild(img);


        productImgSlide.appendChild(singleProductImg);
        productPrice.innerHTML = '';
        productPrice.appendChild(priceSpan);
        //Update the maximum quantity allowed
//        productQuantity.setAttribute('max', product.availableQuantity);
    });
}
async function resizeImage(input, maxWidth, maxHeight) {
    return new Promise((resolve, reject) => {
        const img = new Image();
        img.onload = function () {
            const canvas = document.createElement('canvas');
            const ctx = canvas.getContext('2d');

            // Calculate new dimensions
            let width = img.width;
            let height = img.height;
            if (width > maxWidth) {
                height *= maxWidth / width;
                width = maxWidth;
            }
            if (height > maxHeight) {
                width *= maxHeight / height;
                height = maxHeight;
            }

            // Set canvas dimensions
            canvas.width = width;
            canvas.height = height;

            // Draw image on canvas with new dimensions
            ctx.drawImage(img, 0, 0, width, height);

            // Convert canvas to Blob
            canvas.toBlob(blob => {
                resolve(blob);
            }, 'image/jpeg');
        };
        img.onerror = function () {
            reject(new Error('Failed to load the image.'));
        };

        // Load image from URL or Blob
        if (input instanceof Blob) {
            const reader = new FileReader();
            reader.onload = function () {
                img.src = reader.result;
            };
            reader.readAsDataURL(input);
        } else if (typeof input === 'string') {
            img.src = input;
        } else {
            reject(new Error('Invalid input type.'));
        }
    });
}


