function getDataFromServlet() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {

                var jsonResponse = xhr.responseText;
                var products = JSON.parse(jsonResponse);
                console.log(jsonResponse);
                displayProducts(products);
            } else {

                console.error('Request failed: ' + xhr.status);
            }
        }
    };

    xhr.open('GET', 'products', true);

    xhr.send();
}
function displayProducts(products) {
    var productContainer = document.querySelector('.product_list .row');
    productContainer.innerHTML = '';

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
        var blob = new Blob([new Uint8Array(product.image)], { type: 'image/jpeg' });
        var imageUrl = URL.createObjectURL(blob);
        productImage.onload = function() {
            var canvas = document.createElement('canvas');
            var ctx = canvas.getContext('2d');

            canvas.width = 362;
            canvas.height = 250;

            // Draw the image onto the canvas with the desired dimensions
            ctx.drawImage(productImage, 0, 0, 362, 250);

            // Convert the canvas content to a resized image data URL
            var resizedImageData = canvas.toDataURL('image/jpeg');

            // Set the resized image data URL as the source of the product image
            productImage.src = resizedImageData;
        };

        productImage.src =  imageUrl;
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
    }
}

document.addEventListener('DOMContentLoaded', function() {
    console.log("start get");
    console.log("data start handel");
    getDataFromServlet();
});
