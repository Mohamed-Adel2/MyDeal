function AddProductScreen() {
    // Redirect to another page within the same domain
    window.location.href = "addProduct.html";
}

let start = 0;
let size = 6;
let loadMore = document.getElementById('load-more');
let category = "All";
let minPrice = 0;
let maxPrice = 1000;
let searchKey = "";

function moveToCategoryScreen() {
    window.location.href = "CategoriesDetail.html";
}

function getProductsFromServlet(newFilter) {
    var xhr = new XMLHttpRequest();
    if (newFilter === true) {
        category = document.querySelector("body > section.product_list.section_padding > div > div > div.col-md-4 > div > div:nth-child(2) > div > div.select_option_list > p").textContent;
        minPrice = document.getElementById('min-price').value;
        maxPrice = document.getElementById('max-price').value;
        searchKey = document.getElementById('search-key').value;
        start = 0;
    }
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var products = JSON.parse(jsonResponse);
                start += products.length;
                if (start % size !== 0 || products.length === 0) {
                    loadMore.style.display = 'none';
                } else {
                    loadMore.style.visibility = 'visible';
                }
                displayProducts(products, newFilter);
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    var params = {
        startIdx: start,
        limit: size,
        category: category,
        minPrice: minPrice,
        maxPrice: maxPrice,
        searchKey: searchKey
    };
    var queryString = Object.keys(params).map(key => key + '=' + params[key]).join('&');
    xhr.open('GET', 'products?' + queryString, true);
    xhr.send();
}


function getCategoriesFromServlet() {
    console.log("data appear");
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
    categoryItem.onclick = selectCategory;
    categoryItem.style.cursor = 'pointer';
    categoryContainer.appendChild(categoryItem);
    for (var i = 0; i < categories.length; i++) {
        var category = categories[i];
        categoryItem = document.createElement('p');
        categoryItem.onclick = selectCategory;
        categoryItem.textContent = category;
        categoryItem.style.cursor = 'pointer';
        categoryContainer.appendChild(categoryItem);
    }
}


function displayProducts(products, newFilter) {
    var productContainer = document.querySelector("body > section.product_list.section_padding > div > div > div.col-md-8 > div > div.row.items")
    if (newFilter === true) {
        productContainer.innerHTML = '';
    }
    for (var i = 0; i < products.length; i++) {
        var product = products[i];
        var productDiv = document.createElement('div');
        productDiv.className = 'col-lg-6 col-sm-6';

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
        console.log(paramValue);
        var parms = {
            Id: paramValue
        }
        var paramStr = Object.keys(parms).map(key => key + '=' + encodeURIComponent(parms[key])).join('&');
        console.log(paramStr);
        productLink.href = 'adminProductDetail.html' + '?' + paramStr;

        productLink.textContent = product.productName;
        productName.appendChild(productLink);
        console.log(paramStr);
        var productPrice = document.createElement('p');
        productPrice.textContent = 'From $' + product.price;

        singleProductDiv.appendChild(productImage);
        singleProductDiv.appendChild(productName);
        singleProductDiv.appendChild(productPrice);

        productDiv.appendChild(singleProductDiv);

        productContainer.appendChild(productDiv);
    }
}


function selectCategory(event) {
    var selectedCategory = event.target.textContent;
    var selectOptionList = document.querySelector("body > section.product_list.section_padding > div > div > div.col-md-4 > div > div:nth-child(2) > div > div.select_option_list > p");
    selectOptionList.textContent = selectedCategory;
    var menu = document.querySelector("body > section.product_list.section_padding > div > div > div.col-md-4 > div > div:nth-child(2) > div > div.select_option_list");
    menu.click();
}

document.addEventListener('DOMContentLoaded', function () {
    console.log("listener");
    checkAuth();
});

function checkAuth() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if (response === 'admin') {
                    // getCategoriesFromServlet();
                    getCategoriesFromServlet();
                    getProductsFromServlet(true);
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
