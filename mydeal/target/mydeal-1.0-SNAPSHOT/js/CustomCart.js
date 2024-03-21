function getCartItemsFromServlet() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var decodedString = atob(jsonResponse);
                var products = JSON.parse(decodedString);
                console.log(products);
                displayProduct(products);

            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    xhr.open('GET', 'cartItems', true);
    xhr.send();
}

function updateCartFromServlet(operation, productId, quantity) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var reply = JSON.parse(jsonResponse);
                if (reply === 'success')
                    customAlert("Your Cart has been updated successfully!");
                else
                    customAlert("Failed to update cart. Check the availability of the product!");
            } else {
                customAlert("Failed to update cart. Please try again later.");
                console.error('Request failed: ' + xhr.status);
            }
            getCartItemsFromServlet();
        }
    };
    var queryString = 'operation=' + operation + '&productId=' + productId + '&quantity=' + quantity;
    xhr.open('GET', 'cartUpdate?' + queryString, true);
    xhr.send();
}

function makeOrderFromServlet() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if (response === 'success') {
                    customAlert("Your order has been placed successfully!");
                    getCartItemsFromServlet();
                } else if (response === 'balanceFailure') {
                    customAlert("Failed to place order. Please check your balance.");
                    getCartItemsFromServlet();
                } else {
                    customAlert("Failed to place order. Please check products availability.");
                    getCartItemsFromServlet();
                }

            } else {
                customAlert("Failed to place order. Please try again later.");
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    xhr.open('GET', 'checkout', true);
    xhr.send();
}


function displayProduct(products) {
    var productContainer = document.getElementById("cart-table");
    productContainer.innerHTML = '';
    // Add header row
    var tableHeader = document.createElement('thead');
    var tableHeaderRow = document.createElement('tr');
    var tableHeaderProduct = document.createElement('th');
    tableHeaderProduct.textContent = 'Product';
    var tableHeaderPrice = document.createElement('th');
    tableHeaderPrice.textContent = 'Price';
    var tableHeaderCount = document.createElement('th');
    tableHeaderCount.textContent = 'Quantity';
    var tableHeaderTotal = document.createElement('th');
    tableHeaderTotal.textContent = 'Total';
    var tableHeaderAvailableQuantity = document.createElement('th');
    tableHeaderAvailableQuantity.textContent = 'Available Quantity';
    tableHeaderRow.appendChild(tableHeaderProduct);
    tableHeaderRow.appendChild(tableHeaderPrice);
    tableHeaderRow.appendChild(tableHeaderCount);
    tableHeaderRow.appendChild(tableHeaderTotal);
    tableHeaderRow.appendChild(tableHeaderAvailableQuantity);
    tableHeader.appendChild(tableHeaderRow);
    productContainer.appendChild(tableHeader);
    //add body
    var tableBody = document.createElement('tbody');
    console.log(products.length);
    //add product rows
    for (var i = 0; i < products.length; i++) {
        console.log(products[i]);
        var product = products[i];
        var tableDataRow = document.createElement('tr');
        var tableDataProduct = document.createElement('td');
        var tableDataProductMedia = document.createElement('div');
        tableDataProductMedia.className = 'media';
        var tableDataProductMediaDiv = document.createElement('div');
        tableDataProductMediaDiv.className = 'd-flex';
        var tableDataProductMediaImage = document.createElement('img');
        var blob = new Blob([new Uint8Array(product.images[0])], {type: 'image/jpeg'});
        tableDataProductMediaImage.src = URL.createObjectURL(blob);
        tableDataProductMediaImage.alt = 'Product Image';
        tableDataProductMediaDiv.appendChild(tableDataProductMediaImage);
        tableDataProductMedia.appendChild(tableDataProductMediaDiv);
        var tableDataProductMediaBody = document.createElement('div');
        tableDataProductMediaBody.className = 'media-body';
        var tableDataProductMediaBodyP = document.createElement('p');
        tableDataProductMediaBodyP.textContent = product.productName;
        tableDataProductMediaBody.appendChild(tableDataProductMediaBodyP);
        tableDataProductMedia.appendChild(tableDataProductMediaBody);
        tableDataProduct.appendChild(tableDataProductMedia);
        tableDataRow.appendChild(tableDataProduct);
        //add price
        var tableDataPrice = document.createElement('td');
        var tableDataPriceH5 = document.createElement('h5');
        tableDataPriceH5.textContent = '$' + product.price;
        tableDataPrice.appendChild(tableDataPriceH5);
        tableDataRow.appendChild(tableDataPrice);
        //add count
        var tableDataCount = document.createElement('td');
        var tableDataCountDiv = document.createElement('div');
        tableDataCountDiv.className = 'product_count';
        var tableDataCountDecrement = document.createElement('span');
        tableDataCountDecrement.className = 'input-number-decrement';
        var tableDataCountDecrementIcon = document.createElement('i');
        tableDataCountDecrementIcon.className = 'ti-minus';
        tableDataCountDecrement.appendChild(tableDataCountDecrementIcon);
        var tableDataCountInput = document.createElement('input');
        tableDataCountInput.className = 'input-number';
        tableDataCountInput.type = 'text';
        tableDataCountInput.value = product.quantity;
        tableDataCountInput.min = 1;
        tableDataCountInput.max = product.availableQuantity;
        tableDataCountInput.value = Math.min(product.quantity, product.availableQuantity);
        var tableDataCountIncrement = document.createElement('span');
        tableDataCountIncrement.className = 'input-number-increment';
        var tableDataCountIncrementIcon = document.createElement('i');
        tableDataCountIncrementIcon.className = 'ti-plus';
        tableDataCountIncrement.appendChild(tableDataCountIncrementIcon);
        tableDataCountDiv.appendChild(tableDataCountDecrement);
        tableDataCountDiv.appendChild(tableDataCountInput);
        tableDataCountDiv.appendChild(tableDataCountIncrement);
        tableDataCount.appendChild(tableDataCountDiv);
        tableDataRow.appendChild(tableDataCount);
        //add total
        var tableDataTotal = document.createElement('td');
        var tableDataTotalH5 = document.createElement('h5');
        tableDataTotalH5.textContent = '$' + product.price * product.quantity;
        tableDataTotal.appendChild(tableDataTotalH5);
        tableDataRow.appendChild(tableDataTotal);
        //add available quantity
        var tableDataAvailableQuantity = document.createElement('td');
        var tableDataAvailableQuantityH5 = document.createElement('h5');
        tableDataAvailableQuantityH5.textContent = product.availableQuantity;
        tableDataAvailableQuantity.appendChild(tableDataAvailableQuantityH5);
        tableDataRow.appendChild(tableDataAvailableQuantity);
        //add action
        var tableDataUpdateAction = document.createElement('td');
        var tableDataUpdateButton = document.createElement('button');
        tableDataUpdateButton.className = 'btn btn-success';
        tableDataUpdateButton.id = 'update-btn';
        tableDataUpdateButton.type = 'button';
        tableDataUpdateButton.textContent = 'Update Item';
        var tableDataRemoveAction = document.createElement('td');
        var tableDataRemoveButton = document.createElement('button');
        tableDataRemoveButton.className = 'btn btn-danger';
        tableDataRemoveButton.id = 'remove-btn';
        tableDataRemoveButton.type = 'button';
        tableDataRemoveButton.textContent = 'Remove Item';
        tableDataUpdateAction.appendChild(tableDataUpdateButton);
        tableDataRemoveAction.appendChild(tableDataRemoveButton);
        tableDataRow.appendChild(tableDataUpdateAction);
        tableDataRow.appendChild(tableDataRemoveAction);
        //add row to body
        tableBody.appendChild(tableDataRow);
        (function () {
            var currentInput = tableDataCountInput;
            tableDataCountDecrement.addEventListener('click', function () {
                var inputValue = parseInt(currentInput.value, 10);
                var minValue = parseInt(currentInput.min, 10);
                if (inputValue > minValue) {
                    currentInput.value = inputValue - 1;
                }
            });

            tableDataCountIncrement.addEventListener('click', function () {
                var inputValue = parseInt(currentInput.value, 10);
                var maxValue = parseInt(currentInput.max, 10);
                if (inputValue < maxValue) {
                    currentInput.value = inputValue + 1;
                }
            });
        })();


        (function (product, tableDataCountInput, tableDataUpdateButton, tableDataRemoveButton) {
            // Add event listener to update button
            tableDataUpdateButton.addEventListener('click', function () {
                var productId = product.id;
                var quantity = parseInt(tableDataCountInput.value, 10);
                updateCartFromServlet('update', productId, quantity);
            });

            // Add event listener to delete button
            tableDataRemoveButton.addEventListener('click', function () {
                var productId = product.id;
                updateCartFromServlet('delete', productId, 0);
            });
        })(products[i], tableDataCountInput, tableDataUpdateButton, tableDataRemoveButton);

        tableDataCountInput.addEventListener('input', function (e) {
            var max = parseInt(e.target.max);
            var value = parseInt(e.target.value);
            if (value < 1) e.target.value = 1;
            if (value > max) e.target.value = max;
        })
    }
    productContainer.appendChild(tableBody);
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

var checkoutButton = document.getElementById('complete-order');
checkoutButton.addEventListener('click', function () {
    var cartTable = document.getElementById('cart-table');
    if (cartTable.rows.length <= 1) {
        customAlert('Your cart is empty. Please add some items to cart first.');
        return;
    }
    makeOrderFromServlet();
});

var continueShoppingButton = document.getElementById('return-shopping');
continueShoppingButton.addEventListener('click', function () {
    window.location.href = 'product_list.html';
});


document.addEventListener('DOMContentLoaded', function () {
    getCartItemsFromServlet();
});
