// Get the button element
document.addEventListener('DOMContentLoaded', function () {
    checkAuth();
    console.log("listener");

});
const deleteProductBtn = document.getElementById('DeleteProductBtn');
const updateProductBtn = document.getElementById('UpdateProductBtn');
deleteProductBtn.addEventListener('click', function() {
    if (confirm('mydeal: Are you sure you want to delete this product?')) {
        deleteProduct();
    } else {
        console.log('Product deletion cancelled.');
    }
});
updateProductBtn.addEventListener('click', function (){
    var parms={
        Id:getURLParameter("Id")
    }
    var paramStr = Object.keys(parms).map(key => key + '=' + encodeURIComponent(parms[key])).join('&');
    window.location.href = 'updateProduct.html'+'?'+paramStr;
})
function deleteProduct() {
    let paramValue = getURLParameter('Id');
    console.log(paramValue);

    var parms = {
        Id: paramValue
    }
    var paramStr = Object.keys(parms).map(key => key + '=' + encodeURIComponent(parms[key])).join('&');

    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'admin/adminDelete?' + paramStr, true);


    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            const response = xhr.responseText;
            console.log('Message from server:', response);
        } else {
            console.log('Message from server:', "Request Failed");
        }
    };
    xhr.onerror = function() {
        console.error('Request failed');
    };

    xhr.send();
}
function getURLParameter(name) {
    let params = new URLSearchParams(window.location.search);

    return params.get(name);
}
function checkAuth(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if(response==='admin'){
                    getDetailFromServlet();
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
function getDetailFromServlet() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                console.log("response" + " " + response);
                if (response === "removed") {
                    document.getElementById("body").innerHTML = '';
                    customAlert('The product is no longer available!');
                } else if (response === "notFound") {
                    document.getElementById("body").innerHTML = '';
                    customAlert('The product you are looking for is not available!');
                } else
                    displayProduct(response);
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
    productQuantityInput.value = 1;

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