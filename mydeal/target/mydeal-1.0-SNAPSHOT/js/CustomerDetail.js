var startIdx = 0;
var size = 6;
var searchKey = '';

function getOrders(state) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var customers = JSON.parse(jsonResponse);
                startIdx += customers.length;
                if (startIdx % size !== 0 || customers.length === 0) {
                    document.getElementById('load-more').style.display = 'none';
                }
                displayCustomers(customers, state);

            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    if (state) {
        startIdx = 0;
        searchKey = document.getElementById('search-key').value;
    }
    var params = {
        startIdx: startIdx,
        limit: size,
        searchKey: searchKey
    };
    var queryString = Object.keys(params).map(key => key + '=' + params[key]).join('&');
    xhr.open('GET', 'showAllCustomers?' + queryString, true);
    xhr.send();
}

function displayCustomers(customers, state) {
    var orderList = document.getElementById("table-body");
    if (state)
        orderList.innerHTML = '';
    for (var i = 0; i < customers.length; i++) {
        var customer = customers[i];
        var row = document.createElement("tr");
        var id = document.createElement("td");
        id.innerHTML = customer.id;
        row.appendChild(id);
        var username = document.createElement("td");
        username.innerHTML = customer.userName;
        row.appendChild(username);

        var Email = document.createElement("td");
        Email.innerHTML = customer.email;
        row.appendChild(Email);

        var phoneNumber = document.createElement("td");
        phoneNumber.innerHTML = customer.phoneNumber;
        row.appendChild(phoneNumber);

        var creditLimit = document.createElement("td");
        creditLimit.innerHTML = customer.creditLimit.toFixed(2);
        row.appendChild(creditLimit);

        var city = document.createElement("td");
        city.innerHTML = customer.addressDataModel.city;
        row.appendChild(city);


        var street = document.createElement("td");
        street.innerHTML = customer.addressDataModel.street;
        row.appendChild(street);


        var apartment = document.createElement("td");
        apartment.innerHTML = customer.addressDataModel.apartment;
        row.appendChild(apartment);

        var tableDataInfoAction = document.createElement('td');
        var tableDataInfoButton = document.createElement('button');
        tableDataInfoButton.className = 'btn_1';
        tableDataInfoButton.id = 'info-btn';
        tableDataInfoButton.type = 'button';
        tableDataInfoButton.textContent = 'Order History';
        tableDataInfoButton.onclick = (function (currentCustomer) {
            return function () {
                window.location.href = 'orders.html?id=' + currentCustomer.id;
            };
        })(customer);
        tableDataInfoAction.appendChild(tableDataInfoButton);
        row.appendChild(tableDataInfoAction);
        orderList.appendChild(row);
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
                if (response === 'admin') {
                    getOrders(true);
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