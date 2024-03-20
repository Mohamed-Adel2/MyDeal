// This function checks if the user is logged in or not
function checkUserStatus(isLoggedIn) {
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

    homeTab.style.display = 'block';
    aboutTab.style.display = 'block';
    contactTab.style.display = 'block';
    productsTab.style.display = 'block';
    cartIcon.style.display = 'block';

    if (isLoggedIn) {
        profileIcon.style.display = 'block';
        historyTab.style.display = 'block';
        logoutTab.style.display = 'block';
        loginTab.style.display = 'none';
        registerTab.style.display = 'none';
    } else {
        profileIcon.style.display = 'none';
        historyTab.style.display = 'none';
        logoutTab.style.display = 'none';
        loginTab.style.display = 'block';
        registerTab.style.display = 'block';
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
                if (response === 'true') {
                    checkUserStatus(true);
                } else {
                    checkUserStatus(false);
                }
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    xhr.open('GET', 'checkStatus', true);
    xhr.send();
}

document.addEventListener('DOMContentLoaded', function () {
    checkStatus();
});