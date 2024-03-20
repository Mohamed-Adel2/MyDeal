var userEmail;
var userPhone;

function getDataFromServlet() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if (response === "notAuthorized") {
                    window.location.href = "login.html";
                } else {
                    setData(response);
                }
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    xhr.open('GET', 'info', true);
    xhr.send();
}

function updateInfo() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if (response === "success") {
                    customAlert("Your information has been updated successfully");
                    userPhone = document.getElementById("phoneNumber").value;
                    userEmail = document.getElementById("email").value;
                } else {
                    customAlert("Something went wrong, please try again later.");
                }
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };
    var params = {
        username: document.getElementById("name").value,
        email: document.getElementById("email").value,
        phone_number: document.getElementById("phoneNumber").value,
        password: document.getElementById("password").value,
        street: document.getElementById("street").value,
        city: document.getElementById("city").value,
        apartment: document.getElementById("apartment").value,
        date_of_birth: document.getElementById("dob").value,
        credit_limit: document.getElementById("creditLimit").value
    };
    var queryString = Object.keys(params).map(key => key + '=' + params[key]).join('&');
    xhr.open('GET', 'updateUserInfo?' + queryString, true);
    xhr.send();
}

function setData(response) {
    var name = document.getElementById("name");
    var email = document.getElementById("email");
    var phone = document.getElementById("phoneNumber");
    var password = document.getElementById("password");
    var confirmationPassword = document.getElementById("confirmationPassword");
    var street = document.getElementById("street");
    var city = document.getElementById("city");
    var apartment = document.getElementById("apartment");
    var dob = document.getElementById("dob");
    var creditLimit = document.getElementById("creditLimit");

    name.value = response.userName;
    email.value = response.email;
    userEmail = response.email;
    userPhone = response.phoneNumber;
    phone.value = response.phoneNumber;
    password.value = response.password;
    confirmationPassword.value = response.password;
    street.value = response.street;
    city.value = response.city;
    apartment.value = response.apartment;
    dob.value = new Date(response.dob).toISOString().substring(0, 10);
    creditLimit.value = response.creditLimit;
}

document.addEventListener('DOMContentLoaded', function () {
    // prevent the default behavior of the form

    document.getElementById("update-form").addEventListener("submit", function (event) {
        event.preventDefault();
    });
    getDataFromServlet();
});

function validateForm() {
    if ($('#emailValidity').is(':visible') || $('#phoneValidity').is(':visible') || $('#usernameValidity').is(':visible') || $('#dobValidity').is(':visible') || $('#creditValidity').is(':visible') || $('#streetValidity').is(':visible') || $('#cityValidity').is(':visible') || $('#apartmentValidity').is(':visible') || $('#passwordValidity').is(':visible') || $('#confirmationValidity').is(':visible')) {
        return;
    }
    updateInfo();
    getDataFromServlet();
}


$('input[required]').on('input', function () {
    $(this).next('.error-message').remove();
});

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

$(document).ready(function () {
    $('#emailValidity').hide();
    $('#phoneValidity').hide();
    $('#usernameValidity').hide();
    $('#dobValidity').hide();
    $('#creditValidity').hide();
    $('#streetValidity').hide();
    $('#cityValidity').hide();
    $('#apartmentValidity').hide();
    $('#passwordValidity').hide();
    $('#confirmationValidity').hide();

    $('#phoneNumber').on('blur', function () {
        var phoneNumber = $(this).val();
        var phonePattern = /^(010|011|012|015)[0-9]{8}$/;
        if (phoneNumber === userPhone) {
            $('#phoneValidity').hide();
        } else if (phonePattern.test(phoneNumber)) {
            $('#phoneValidity').hide();
            $.ajax({
                url: 'validate',
                type: 'POST',
                data: {phone_number: phoneNumber},
                success: function (response) {
                    if (response.trim() == 'valid') {
                        $('#phoneValidity').text('');
                        $('#phoneValidity').hide();
                    } else {
                        $('#phoneValidity').show();
                        $('#phoneValidity').text('This phone number is already exist').css('color', 'red');
                    }
                }
            });
        } else {
            $('#phoneValidity').show();
            $('#phoneValidity').text('Phone number must start with 010, 011, 012, or 015 and consist of 11 digits.').css('color', 'red');
        }
    });

    $('#email').on('blur', function () {
        var email = $(this).val();
        var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        if (email.length == 0) {
            $('#emailValidity').text("Please enter a valid email").css('color', 'red');
        } else if ((userEmail === email)) {
            $('#emailValidity').hide();
        } else if (emailPattern.test(email)) {
            $('#emailValidity').hide();
            $.ajax({
                url: 'validate', // Change this to the URL where you handle validation
                type: 'POST',
                data: {email: email},
                beforeSend: function () {
                    $('#emailValidity').hide();
                },
                success: function (response) {
                    if (response.trim() == 'valid') {
                        $('#emailValidity').hide();
                    } else {
                        $('#emailValidity').show();
                        $('#emailValidity').text('This email is already exist').css('color', 'red');
                    }
                }, onerror: function () {
                    $('#emailValidity').text('There is an error occurred').css('color', 'red');
                }
            });
        } else {
            $('#emailValidity').show();
            $('#emailValidity').text("Email must be in the correct format.").css('color', 'red');
        }
    });

    $('#name').on('blur', function () {
        var name = $(this).val();
        if (name.length < 4) {
            $('#usernameValidity').show();
            $('#usernameValidity').text('Name must be more than 4 characters long.').css('color', 'red');
        } else {
            $('#usernameValidity').text('').css('color', 'red');
            $('#usernameValidity').hide();
        }
    });

    $('#dob').on('blur', function () {
        var dob = $(this).val();
        var date = new Date();
        var today = date.toISOString().substring(0, 10);
        var dobPattern = /^(19|20)\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$/;
        if (!dobPattern.test(dob) || dob > today) {
            $('#dobValidity').show();
            $('#dobValidity').text('Date of birth must be a valid date in the past.').css('color', 'red');
        } else {
            $('#dobValidity').text('').css('color', 'red');
            $('#dobValidity').hide();
        }
    });

    $('#creditLimit').on('blur', function () {
        var creditLimit = $(this).val();
        var creditLimitPattern = /^[0-9]*\.?[0-9]+$/;
        if (!creditLimitPattern.test(creditLimit) || creditLimit > 100000) {
            $('#creditValidity').show();
            $('#creditValidity').text('Credit limit must be a positive number between 0 and 100000.').css('color', 'red');
        } else {
            $('#creditValidity').text('').css('color', 'red');
            $('#creditValidity').hide();
        }
    });

    $('#street').on('blur', function () {
        var street = $(this).val();
        var streetPattern = /^.{8,}$/;
        if (!streetPattern.test(street)) {
            $('#streetValidity').show();
            $('#streetValidity').text('Street must be more than 8 characters long.').css('color', 'red');
        } else {
            $('#streetValidity').text('').css('color', 'red');
            $('#streetValidity').hide();
        }
    });

    $('#city').on('blur', function () {
        var city = $(this).val();
        var cityPattern = /^.{3,}$/;
        if (!cityPattern.test(city)) {
            $('#cityValidity').show();
            $('#cityValidity').text('City must be more than 3 characters long.').css('color', 'red');
        } else {
            $('#cityValidity').text('').css('color', 'red');
            $('#cityValidity').hide();
        }
    });

    $('#apartment').on('blur', function () {
        var apartment = $(this).val();
        var apartmentPattern = /^[1-9][0-9]?$|^100$/;
        if (!apartmentPattern.test(apartment)) {
            $('#apartmentValidity').show();
            $('#apartmentValidity').text('Apartment must be an integer between 1 and 100.').css('color', 'red');
        } else {
            $('#apartmentValidity').text('').css('color', 'red');
            $('#apartmentValidity').hide();
        }
    });

    $('#password').on('blur', function () {
        var password = $(this).val();
        var passwordPattern = /^.{8,}$/;
        if (!passwordPattern.test(password)) {
            $('#passwordValidity').show();
            $('#passwordValidity').text('Password must be at least 8 characters long.').css('color', 'red');
        } else {
            $('#passwordValidity').text('').css('color', 'red');
            $('#passwordValidity').hide();
        }
    });

    $('#confirmationPassword').on('blur', function () {
        var password = $('#password').val();
        var confirmPassword = $(this).val();
        if (password !== confirmPassword) {
            $('#confirmationValidity').show();
            $('#confirmationValidity').text('Passwords do not match.').css('color', 'red');
        } else {
            $('#confirmationValidity').text('').css('color', 'red');
            $('#confirmationValidity').hide();
        }
    });

    $('input[required]').on('input', function () {
        $(this).removeClass('error');
        $(this).next('.error-message').remove();
    });
})
;