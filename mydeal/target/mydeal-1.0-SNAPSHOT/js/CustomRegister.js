function register() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var response = JSON.parse(jsonResponse);
                if (response === "success") {
                    customAlert("You have registered successfully.");
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
    xhr.open('GET', 'register?' + queryString, true);
    xhr.send();
}

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById("update-form").addEventListener("submit", function (event) {
        event.preventDefault();
    });
});

function validateForm() {
    validateCity();
    validateStreet();
    validateCredit();
    validateDob();
    validateEmail();
    validateName();
    validatePassword();
    validatePhone();
    validateApartment();
    validateConfirmation();
    if ($('#emailValidity').is(':visible') || $('#phoneValidity').is(':visible') || $('#usernameValidity').is(':visible') || $('#dobValidity').is(':visible') || $('#creditValidity').is(':visible') || $('#streetValidity').is(':visible') || $('#cityValidity').is(':visible') || $('#apartmentValidity').is(':visible') || $('#passwordValidity').is(':visible') || $('#confirmationValidity').is(':visible')) {
        return;
    }
    register();
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
    messageElement.id = 'alert-message';
    alertContainer.appendChild(messageElement);

    // Create OK button
    var okButton = document.createElement('button');
    okButton.textContent = 'OK';
    okButton.onclick = function () {
        if (document.getElementById('alert-message').textContent === 'You have registered successfully.') {
            window.location.href = 'login.html';
        }
    }
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
        console.log('blur');
        validatePhone();
    });

    $('#email').on('blur', function () {
        validateEmail();
    });

    $('#name').on('blur', function () {
        validateName();
    });

    $('#dob').on('blur', function () {
        validateDob();
    });

    $('#creditLimit').on('blur', function () {
        validateCredit();
    });

    $('#street').on('blur', function () {
        validateStreet();
    });

    $('#city').on('blur', function () {
        validateCity();
    });

    $('#apartment').on('blur', function () {
        validateApartment();
    });

    $('#password').on('blur', function () {
        validatePassword();
    });

    $('#confirmationPassword').on('blur', function () {
        validateConfirmation();
    });

    $('input[required]').on('input', function () {
        $(this).removeClass('error');
        $(this).next('.error-message').remove();
    });
});

function validateEmail() {
    var email = $('#email').val().trim();
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if (email.length === 0) {
        $('#emailValidity').text("Please enter a valid email").css('color', 'red');
        $('#emailValidity').show();
    } else if (emailPattern.test(email)) {
        $('#emailValidity').hide();
        $.ajax({
            url: 'validate',
            type: 'POST',
            data: {email: email},
            beforeSend: function () {
                $('#emailValidity').hide();
            },
            success: function (response) {
                if (response.trim() == 'valid') {
                    $('#emailValidity').hide();
                } else {
                    $('#emailValidity').text('This email is already exist').css('color', 'red');
                    $('#emailValidity').show();
                }
            }, onerror: function () {
                $('#emailValidity').text('There is an error occurred').css('color', 'red');
                $('#emailValidity').show();
            }
        });
    } else {
        $('#emailValidity').text("Email must be in the correct format.").css('color', 'red');
        $('#emailValidity').show();
    }
}

function validatePhone() {
    var phoneNumber = $('#phoneNumber').val().trim();
    var phonePattern = /^(010|011|012|015)[0-9]{8}$/;
    if (phonePattern.test(phoneNumber)) {
        $('#phoneValidity').hide();
        $.ajax({
            url: 'validate',
            type: 'POST',
            data: {phone_number: phoneNumber},
            success: function (response) {
                if (response.trim() == 'valid') {
                    $('#phoneValidity').hide();
                    $('#phoneValidity').text('');
                } else {
                    $('#phoneValidity').text('This phone number is already exist').css('color', 'red');
                    $('#phoneValidity').show();
                }
            }
        });
    } else {
        $('#phoneValidity').text('Phone number must start with 010, 011, 012, or 015 and consist of 11 digits.').css('color', 'red');
        $('#phoneValidity').show();
    }
}

function validateName() {
    var name = $('#name').val().trim();
    if (name.length < 4) {
        $('#usernameValidity').text('Name must be more than 4 characters long.').css('color', 'red');
        $('#usernameValidity').show();
    } else {
        $('#usernameValidity').hide();
        $('#usernameValidity').text('').css('color', 'red');
    }
}

function validateDob() {
    var dob = $('#dob').val().trim();
    var date = new Date();
    var today = date.toISOString().substring(0, 10);
    var dobPattern = /^(19|20)\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$/;
    if (!dobPattern.test(dob) || dob > today) {
        $('#dobValidity').text('Date of birth must be a valid date in the past.').css('color', 'red');
        $('#dobValidity').show();
    } else {
        $('#dobValidity').hide();
        $('#dobValidity').text('').css('color', 'red');
    }
}

function validateCredit() {
    var creditLimit = $('#creditLimit').val();
    var creditLimitPattern = /^[0-9]*\.?[0-9]+$/;
    if (!creditLimitPattern.test(creditLimit) || creditLimit > 100000) {
        $('#creditValidity').text('Credit limit must be a positive number between 0 and 100000.').css('color', 'red');
        $('#creditValidity').show();
    } else {
        $('#creditValidity').hide();
        $('#creditValidity').text('').css('color', 'red');
    }
}

function validateStreet() {
    var street = $('#street').val().trim();
    var streetPattern = /^.{8,}$/;
    if (!streetPattern.test(street)) {
        $('#streetValidity').text('Street must be more than 8 characters long.').css('color', 'red');
        $('#streetValidity').show();
    } else {
        $('#streetValidity').hide();
        $('#streetValidity').text('').css('color', 'red');
    }
}

function validateCity() {
    var city = $('#city').val().trim();
    var cityPattern = /^.{3,}$/;
    if (!cityPattern.test(city)) {
        $('#cityValidity').text('City must be more than 3 characters long.').css('color', 'red');
        $('#cityValidity').show();
    } else {
        $('#cityValidity').hide();
        $('#cityValidity').text('').css('color', 'red');
    }
}

function validateApartment() {
    var apartment = $('#apartment').val();
    var apartmentPattern = /^[1-9][0-9]?$|^100$/;
    if (!apartmentPattern.test(apartment)) {
        $('#apartmentValidity').text('Apartment must be an integer between 1 and 100.').css('color', 'red');
        $('#apartmentValidity').show();
    } else {
        $('#apartmentValidity').hide();
        $('#apartmentValidity').text('').css('color', 'red');
    }
}

function validatePassword() {
    var password = $('#password').val();
    var passwordPattern = /^.{8,}$/;
    if (!passwordPattern.test(password)) {
        $('#passwordValidity').text('Password must be at least 8 characters long.').css('color', 'red');
        $('#passwordValidity').show();
    } else {
        $('#passwordValidity').hide();
        $('#passwordValidity').text('').css('color', 'red');
    }
}

function validateConfirmation() {
    var password = $('#password').val();
    var confirmPassword = $('#confirmationPassword').val();
    if (password !== confirmPassword) {
        $('#confirmationValidity').text('Passwords do not match.').css('color', 'red');
        $('#confirmationValidity').show();
    } else {
        $('#confirmationValidity').hide();
        $('#confirmationValidity').text('').css('color', 'red');
    }
}



