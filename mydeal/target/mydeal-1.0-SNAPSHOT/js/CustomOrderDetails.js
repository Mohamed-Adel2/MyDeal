document.addEventListener('DOMContentLoaded', function () {
    getOrderDetails();
});

function getURLParameter(name) {
    let params = new URLSearchParams(window.location.search);
    return params.get(name);
}

function displayProducts(products) {
    console.log(products);
    var productContainer = document.getElementById("details-table");
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
    tableHeaderRow.appendChild(tableHeaderProduct);
    tableHeaderRow.appendChild(tableHeaderPrice);
    tableHeaderRow.appendChild(tableHeaderCount);
    tableHeaderRow.appendChild(tableHeaderTotal);
    tableHeader.appendChild(tableHeaderRow);
    productContainer.appendChild(tableHeader);
    //add body
    var tableBody = document.createElement('tbody');
    //add product rows
    for (var i = 0; i < products.length; i++) {
        var product = products[i];
        var tableDataRow = document.createElement('tr');
        var tableDataProduct = document.createElement('td');
        var tableDataProductMedia = document.createElement('div');
        tableDataProductMedia.className = 'media';
        var tableDataProductMediaDiv = document.createElement('div');
        tableDataProductMediaDiv.className = 'd-flex';
        var tableDataProductMediaImage = document.createElement('img');
        var blob = new Blob([new Uint8Array(product.image)], {type: 'image/jpeg'});
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
        var tableDataQuantityH5 = document.createElement('h5');
        tableDataQuantityH5.textContent = product.quantity;
        tableDataCount.appendChild(tableDataQuantityH5);
        tableDataRow.appendChild(tableDataCount);

        //add total
        var tableDataTotal = document.createElement('td');
        var tableDataTotalH5 = document.createElement('h5');
        tableDataTotalH5.textContent = '$' + product.price * product.quantity;
        tableDataTotal.appendChild(tableDataTotalH5);
        tableDataRow.appendChild(tableDataTotal);

        tableBody.appendChild(tableDataRow);

        productContainer.appendChild(tableBody);

    }
}


function getOrderDetails() {
    console.log("function invoked");
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var decodedString = atob(jsonResponse);
                var orderDetails = JSON.parse(decodedString);
                if (orderDetails === "failure")
                    customAlert('Something went wrong, please try again!');
                else {
                    displayProducts(orderDetails);
                }

            } else {
                customAlert('Something went wrong, please try again!');
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    xhr.open('GET', 'orderDetails?orderId=' + getURLParameter("id"), true);
    xhr.send();
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
