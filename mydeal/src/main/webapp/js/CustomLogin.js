function getUserFromServlet(first) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                console.log(response);
                if (response === 'invalid' && !first) {
                    customAlert('Wrong email or password, please try again');
                }
                else if(response === 'Admin'){
                    console.log("Yes Admin");
                    window.location.href ='index.html';
                }
                else if (response === 'valid') {
                   // window.location.href ='adminHome.html';
                    window.location.href = 'index.html';
                }
            } else {
                customAlert('Something went wrong, please try again later');
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    var email = (first ? "" : document.getElementById('email').value);
    var password = (first ? "" : document.getElementById('password').value);
    var rememberMe = (first ? true : document.getElementById('f-option').checked);
    var params = {
        email: email,
        password: password,
        rememberMe: rememberMe
    };
    var queryString = Object.keys(params).map(key => key + '=' + params[key]).join('&');
    xhr.open('GET', 'login?' + queryString, true);
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

document.querySelector('.contact_form').addEventListener('submit', function (event) {
    event.preventDefault();
});

document.addEventListener('DOMContentLoaded', function () {
    getUserFromServlet(true);
});