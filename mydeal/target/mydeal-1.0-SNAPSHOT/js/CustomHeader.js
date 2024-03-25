// This function checks if the user is logged in or not
function checkUserStatus(status) {
    console.log(status);
    var homeTab = document.getElementById('homeTab');
    var aboutTab = document.getElementById('aboutTab');
    var contactTab = document.getElementById('contactTab');
    var productsTab = document.getElementById('productsTab');
    var profileIcon = document.getElementById('profileIcon');
    var loginTab = document.getElementById('loginTab');
    var logoutTab = document.getElementById('logoutTab');
    var historyTab = document.getElementById('historyTab');
    var registerTab = document.getElementById('registerTab');
    var cartIcon = document.getElementById('cartIcon');
    var addCategoryTab = document.getElementById('addCategoryTab');
    var addProductTab = document.getElementById('addProductTab');
    var viewCustomersTab = document.getElementById('viewCustomersTab');

    homeTab.style.display = 'block';
    aboutTab.style.display = 'block';
    contactTab.style.display = 'block';
    productsTab.style.display = 'block';
    cartIcon.style.display = 'block';

    if (status === 'user') {
        profileIcon.style.display = 'block';
        historyTab.style.display = 'block';
        logoutTab.style.display = 'block';
        logoutTab.setAttribute('onclick', 'logOut()');
        loginTab.style.display = 'none';
        registerTab.style.display = 'none';
        addCategoryTab.style.display = 'none';
        addProductTab.style.display = 'none';
        viewCustomersTab.style.display = 'none';
    } else if (status === 'notAuthorized') {
        profileIcon.style.display = 'none';
        historyTab.style.display = 'none';
        logoutTab.style.display = 'none';
        loginTab.style.display = 'block';
        registerTab.style.display = 'block';
        addCategoryTab.style.display = 'none';
        addProductTab.style.display = 'none';
        viewCustomersTab.style.display = 'none';
    } else {
        logoutTab.style.display = 'block';
        logoutTab.setAttribute('onclick', 'logOut()');
        addProductTab.style.display = 'block';
        addCategoryTab.style.display = 'block';
        viewCustomersTab.style.display = 'block';
        profileIcon.style.display = 'none';
        historyTab.style.display = 'none';
        loginTab.style.display = 'none';
        registerTab.style.display = 'none';
        contactTab.style.display = 'none';
        cartIcon.style.display = 'none';
        var addProductLink = document.getElementById("addProductLink");
        if (addProductLink === null)
            console.log("error");
        addProductLink.href = 'adminHome.html';
    }
}

function checkStatus() {
    console.log('Checking user status');
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                checkUserStatus(response);
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    xhr.open('GET', 'checkStatus', true);
    xhr.send();
}

function logOut() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if (response === "success") {
                    window.location.href = "index.html";
                } else {
                    customAlert("Something went wrong, please try again later");
                }
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    xhr.open('GET', 'logout', true);
    xhr.send();
}

document.addEventListener('DOMContentLoaded', function () {
    checkStatus();
});