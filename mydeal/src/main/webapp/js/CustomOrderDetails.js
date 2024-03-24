document.addEventListener('DOMContentLoaded', function () {
   // getOrderDetails();
    checkAuth();
});

function getURLParameter(name) {
    let params = new URLSearchParams(window.location.search);
    return params.get(name);
}

function displayProducts(products) {
    var productContainer = document.getElementById("details-table").getElementsByTagName('tbody')[0];
    productContainer.innerHTML = '';

    var totalQuantity = 0;
    var totalPrice = 0;

    for (var i = 0; i < products.length; i++) {
        var product = products[i];
        var tableDataRow = document.createElement('tr');

        // Product name
        // add a elements that links to th product page
        var tableDataProduct = document.createElement('th');
        tableDataProduct.colSpan = "2";
        var productNameSpan = document.createElement('span');
        productNameSpan.textContent = product.productName;
        productNameSpan.style.cursor = 'pointer';
        (function (product) {
            productNameSpan.addEventListener('click', function () {
                window.location.href = 'single-product.html?Id=' + product.id;
            });
        })(product);
        tableDataProduct.appendChild(productNameSpan);
        tableDataRow.appendChild(tableDataProduct);

        // Quantity
        var tableDataQuantity = document.createElement('th');
        tableDataQuantity.textContent = 'x' + product.quantity;
        tableDataRow.appendChild(tableDataQuantity);

        // Total
        var tableDataTotal = document.createElement('th');
        var totalSpan = document.createElement('span');
        totalSpan.textContent = '$' + (product.price * product.quantity).toFixed(2);
        tableDataTotal.appendChild(totalSpan);
        tableDataRow.appendChild(tableDataTotal);

        productContainer.appendChild(tableDataRow);

        totalQuantity += product.quantity;
        totalPrice += product.price * product.quantity;
    }

    // Add subtotal row
    var subtotalRow = document.createElement('tr');
    var subtotalTh = document.createElement('th');
    subtotalTh.colSpan = "3";
    subtotalTh.textContent = 'Subtotal';
    subtotalRow.appendChild(subtotalTh);
    var subtotalTotalTh = document.createElement('th');
    var subtotalTotalSpan = document.createElement('span');
    subtotalTotalSpan.textContent = '$' + totalPrice.toFixed(2);
    subtotalTotalTh.appendChild(subtotalTotalSpan);
    subtotalRow.appendChild(subtotalTotalTh);
    productContainer.appendChild(subtotalRow);

    // Add shipping row
    var shippingRow = document.createElement('tr');
    var shippingTh = document.createElement('th');
    shippingTh.colSpan = "3";
    shippingTh.textContent = 'Shipping';
    shippingRow.appendChild(shippingTh);
    var shippingTotalTh = document.createElement('th');
    var shippingTotalSpan = document.createElement('span');
    shippingTotalSpan.textContent = 'free delivery: $00.00';
    shippingTotalTh.appendChild(shippingTotalSpan);
    shippingRow.appendChild(shippingTotalTh);
    productContainer.appendChild(shippingRow);

    // Update the footer
    var footer = document.getElementById("details-table").getElementsByTagName('tfoot')[0];
    footer.getElementsByTagName('th')[1].textContent = totalQuantity;
    footer.getElementsByTagName('th')[2].textContent = '$' + (totalPrice.toFixed(2));
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
                    displayCustomerDetails(orderDetails[0]);
                    displayProducts(orderDetails[1]);
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


function displayCustomerDetails(customer) {
    // Order info
    var orderInfoList = document.getElementById("order-details");
    orderInfoList.innerHTML = `
        <li>
            <p>Order ID</p><span>: ${getURLParameter("id")}</span>
        </li>
        <li>
            <p>Date</p><span>: ${new Date(getURLParameter("date")).toLocaleDateString()}</span>
        </li>
        <li>
            <p>Total</p><span>: USD ${getURLParameter("totalPrice")}</span>
        </li>
        <li>
            <p>Payment method</p><span>: Cash on Delivery</span>
        </li>
        <li>
            <p>Status</p><span>: Under delivery</span>
        </li>
    `;

    // Shipping address
    var shippingAddressList = document.getElementById("shipping-details");
    shippingAddressList.innerHTML = `
        <li>
            <p>Customer Name</p><span>: ${customer.name}</span>
        </li>
        <li>
            <p>Phone</p><span>: ${customer.phone}</span>
        </li>
        <li>
            <p>Street</p><span>: ${customer.address.street}</span>
        </li>
        <li>
            <p>City</p><span>: ${customer.address.city}</span>
        </li>
        <li>
            <p>Apartment</p><span>: ${customer.address.apartment}</span>
        </li>
    `;
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
function checkAuth(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                let jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if(response==='user'|| response==='admin'){
                    getOrderDetails();
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
