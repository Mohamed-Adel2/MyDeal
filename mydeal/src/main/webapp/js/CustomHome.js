let userType='';

function getProductsFromServlet() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var products = JSON.parse(jsonResponse);
                displayProducts(products);
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    var params = {
        startIdx: 0,
        limit: 6,
        category: "All",
        minPrice: 0,
        maxPrice: 100000,
        searchKey: ""
    };
    var queryString = Object.keys(params).map(key => key + '=' + params[key]).join('&');
    xhr.open('GET', 'products?' + queryString, true);
    xhr.send();
}

function displayProducts(products) {
    var productContainer = document.getElementById("trending-items");
    productContainer.innerHTML = '';
    for (var i = 0; i < products.length; i++) {
        var product = products[i];
        var productDiv = document.createElement('div');
        productDiv.className = 'col-lg-4 col-sm-6';

        var singleProductDiv = document.createElement('div');
        singleProductDiv.className = 'single_product_item';

        var productImage = document.createElement('img');
        var blob = new Blob([new Uint8Array(product.image)], {type: 'image/jpeg'});
        productImage.src = URL.createObjectURL(blob);
        productImage.alt = 'Product Image';
        productImage.className = 'product_image';

        var productName = document.createElement('h3');
        var productLink = document.createElement('a');
        let paramValue = product.id;
        var parms = {
            Id: paramValue
        }
        var paramStr = Object.keys(parms).map(key => key + '=' + encodeURIComponent(parms[key])).join('&');
        if (userType === 'admin') {
            productLink.href = 'adminProductDetail.html' + '?' + paramStr;
        } else
            productLink.href = 'single-product.html' + '?' + paramStr;
        productLink.textContent = product.productName;
        productName.appendChild(productLink);

        var productPrice = document.createElement('p');
        productPrice.textContent = 'From $' + product.price;

        singleProductDiv.appendChild(productImage);
        singleProductDiv.appendChild(productName);
        singleProductDiv.appendChild(productPrice);

        productDiv.appendChild(singleProductDiv);

        productContainer.appendChild(productDiv);
    }
}


document.addEventListener('DOMContentLoaded', function () {
    checkAuth();
});

function checkAuth() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if (response === 'admin' || response === 'customer') {
                    userType = response;
                }
                getProductsFromServlet();
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };

    xhr.open('GET', 'checkStatus', true);

    xhr.send();
}