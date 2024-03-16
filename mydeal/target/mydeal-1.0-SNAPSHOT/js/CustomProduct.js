let start = 0;
let size = 6;
let loadMore = document.getElementById('load-more');

function getProductsFromServlet() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var products = JSON.parse(jsonResponse);
                start += products.length;
                displayProducts(products);
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };

    var params = {
        startIdx: start,
        limit: size
    };

    // Convert parameters to URL query string
    var queryString = Object.keys(params).map(key => key + '=' + params[key]).join('&');

    xhr.open('GET', 'products?' + queryString, true);

    xhr.send();
}

function getCategoriesFromServlet() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var categories = JSON.parse(jsonResponse);
                console.log(jsonResponse);
                displayCategories(categories);
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };

    xhr.open('GET', 'categories', true);

    xhr.send();
}

function displayCategories(categories) {
    var categoryContainer = document.querySelector("body > section.product_list.section_padding > div > div > div.col-md-4 > div > div:nth-child(2) > div > div.select_option_dropdown");
    categoryContainer.innerHTML = '';
    var categoryItem = document.createElement('p');
    categoryItem.textContent = 'All';
    categoryContainer.appendChild(categoryItem);
    for (var i = 0; i < categories.length; i++) {
        var category = categories[i];
        categoryItem = document.createElement('p');
        categoryItem.textContent = category;
        categoryContainer.appendChild(categoryItem);
    }
}


function displayProducts(products) {
    var productContainer = document.querySelector("body > section.product_list.section_padding > div > div > div.col-md-8 > div > div.row.items")
    // Loop through the products and create HTML elements for each product
    for (var i = 0; i < products.length; i++) {
        var product = products[i];
        // Create product container
        var productDiv = document.createElement('div');
        productDiv.className = 'col-lg-6 col-sm-6';

        // Create single product item
        var singleProductDiv = document.createElement('div');
        singleProductDiv.className = 'single_product_item';

        // Create product image
        var productImage = document.createElement('img');
        var blob = new Blob([new Uint8Array(product.image)], {type: 'image/jpeg'});
        var imageUrl = URL.createObjectURL(blob);
        productImage.src = imageUrl;
        productImage.alt = 'Product Image';
        productImage.className = 'img-fluid';

        // Create product name
        var productName = document.createElement('h3');
        var productLink = document.createElement('a');
        productLink.href = 'single-product.html'; // Link to product details page
        productLink.textContent = product.productName; // Assuming 'productName' is a property of the product
        productName.appendChild(productLink);

        // Create product price
        var productPrice = document.createElement('p');
        productPrice.textContent = 'From $' + product.price; // Assuming 'price' is a property of the product

        // Append elements to single product item
        singleProductDiv.appendChild(productImage);
        singleProductDiv.appendChild(productName);
        singleProductDiv.appendChild(productPrice);

        // Append single product item to product container
        productDiv.appendChild(singleProductDiv);

        // Append product container to product list
        productContainer.appendChild(productDiv);

        if (start % size !== 0) {
            loadMore.style.display = 'none';
        }
    }
}

document.addEventListener('DOMContentLoaded', function () {
    getCategoriesFromServlet();
    getProductsFromServlet();
});
