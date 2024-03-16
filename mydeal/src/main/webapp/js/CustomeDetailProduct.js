document.addEventListener('DOMContentLoaded', function () {
    console.log("listener");
    getDetailFromServlet();
});
function getDetailFromServlet(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var product = JSON.parse(jsonResponse);

                displayProduct(product);
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    let paramValue = getURLParameter('Id');
    xhr.open('GET', '', true);
    xhr.send();
}
function getURLParameter(name) {
    // Get the URL parameters part
    let params = new URLSearchParams(window.location.search);
    // Return the value of the specified parameter
    return params.get(name);
}