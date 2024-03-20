document.addEventListener('DOMContentLoaded', function () {
    console.log("listener");
    getDetailFromServlet();
    var addToCartBtn = document.getElementById('addToCartBtn');

    addToCartBtn.addEventListener('click', function (event) {
        event.preventDefault();
        var quantity = parseInt(document.getElementById("product-quantity").value);
        console.log("quantity equal" + " " + quantity);
        addToCart(quantity);
    });
    var backBtn = document.getElementById('backBtn');
    backBtn.addEventListener('click', function (event) {
        event.preventDefault();
        window.location.href = 'product_list.html';
    });
});

function getDetailFromServlet() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var product = JSON.parse(jsonResponse);
                displayProduct(product);
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

function getURLParameter(name) {
    // Get the URL parameters part
    let params = new URLSearchParams(window.location.search);

    return params.get(name);
}

async function displayProduct(product) {
    var productImgSlide = document.getElementById('images-slide');
    var productName = document.querySelector('.single_product_text h3');
    var productDescription = document.querySelector('.single_product_text p');
    var productPrice = document.getElementById('product-price');
    var productQuantityInput = document.getElementById('product-quantity');
    var productQuantity = document.getElementById('available-quantity');
    console.log(product);
    productName.innerText = product.productName;
    productDescription.innerText = product.description;
    var priceSpan = document.createElement('span');
    priceSpan.textContent = 'Unit Price: $' + product.price;

    $(productImgSlide).trigger('destroy.owl.carousel').removeClass('owl-carousel owl-loaded');
    $(productImgSlide).find('.owl-stage-outer').children().unwrap();

    var cnt = 1;
    product.images.forEach(function (imageArray) {
        if (cnt !== product.images.length) {
            var img = document.createElement('img');
            img.className = 'img-fluid';
            var blob = new Blob([new Uint8Array(imageArray)], {type: 'image/jpeg'});
            img.src = URL.createObjectURL(blob);
            img.alt = "#";

            var singleProductImg = document.createElement('div');
            singleProductImg.className = 'single_product_img';
            singleProductImg.appendChild(img);

            // Append the singleProductImg div to the productImgSlide div
            productImgSlide.appendChild(singleProductImg);
            cnt++;
        }
    });
    productPrice.innerHTML = '';
    productPrice.appendChild(priceSpan);
    //Update the maximum quantity allowed
    productQuantity.innerHTML = 'Available Quantity: ' + product.availableQuantity;
    productQuantityInput.max = product.availableQuantity;
    productQuantityInput.min = 1;

    $(productImgSlide).addClass('owl-carousel').owlCarousel({
        loop: true,
        margin: 10,
        nav: true,
        items: 1,
        autoplay: true,
        autoplayTimeout: 2000,
        autoplayHoverPause: true
    });
}

document.getElementById('product-quantity').addEventListener('input', function (e) {
    var max = parseInt(e.target.max);
    var value = parseInt(e.target.value);
    if (value < 1) e.target.value = 1;
    if (value > max) e.target.value = max;
});

function addToCart(val) {
    console.log("function invoked");
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var product = JSON.parse(jsonResponse);
                console.log(product);
                if (product === "valid")
                    customAlert('Item added to cart successfully!');
                else
                    customAlert('The requested quantity is unavailable now!');

            } else {
                customAlert('Failed adding to cart, please try again!');
            }
        }
    };

    var parms = {
        productId: getURLParameter("Id"),
        quantity: val
    }
    var paramStr = Object.keys(parms).map(key => key + '=' + encodeURIComponent(parms[key])).join('&');
    xhr.open('GET', 'cart?' + paramStr, true);
    xhr.send();
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

function customAlert(message) {
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
    });
    alertContainer.appendChild(okButton);

    // Append alert container to body
    document.body.appendChild(alertContainer);

    // Show the alert
    alertContainer.style.display = 'block';
}
