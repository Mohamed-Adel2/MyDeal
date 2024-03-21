let start = 0;
let size = 5;

function getOrders() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var decodedString = atob(jsonResponse);
                var products = JSON.parse(decodedString);
                start += products.length;
                displayOrders(products);

            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    var params = {
        startIdx: start,
        limit: size
    };
    var queryString = Object.keys(params).map(key => key + '=' + params[key]).join('&');
    xhr.open('GET', 'orders?' + queryString, true);
    xhr.send();
}


function displayOrders(orders) {
    if (orders.length < size) {
        document.getElementById("load-more").style.display = "none";
    }
    var orderList = document.getElementById("order-table");
    for (var i = 0; i < orders.length; i++) {
        var order = orders[i];
        var row = document.createElement("tr");
        var id = document.createElement("td");
        id.innerHTML = order.id;
        row.appendChild(id);
        var date = document.createElement("td");
        date.innerHTML = order.date;
        row.appendChild(date);
        var itemsCount = document.createElement("td");
        itemsCount.innerHTML = order.itemsCount;
        row.appendChild(itemsCount);
        var totalPrice = document.createElement("td");
        totalPrice.innerHTML = order.totalPrice;
        row.appendChild(totalPrice);
        var tableDataInfoAction = document.createElement('td');
        var tableDataInfoButton = document.createElement('button');
        tableDataInfoButton.className = 'btn_3';
        tableDataInfoButton.id = 'info-btn';
        tableDataInfoButton.type = 'button';
        tableDataInfoButton.textContent = 'Order Details';
        tableDataInfoButton.onclick = (function(order) {
            return function() {
                window.location.href = 'order-items.html?id=' + order.id;
            };
        })(order);
        tableDataInfoAction.appendChild(tableDataInfoButton);
        row.appendChild(tableDataInfoAction);
        orderList.appendChild(row);
    }
}

document.addEventListener('DOMContentLoaded', function () {
    getOrders();
});