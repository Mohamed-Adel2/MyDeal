// Get the button element
const deleteProductBtn = document.getElementById('DeleteProductBtn');
deleteProductBtn.addEventListener('click', function() {
    if (confirm('mydeal: Are you sure you want to delete this product?')) {
        deleteProduct();
    } else {
        console.log('Product deletion cancelled.');
    }
});
function deleteProduct() {
    let paramValue = getURLParameter('Id');
    console.log(paramValue);

    var parms = {
        productId: paramValue
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
