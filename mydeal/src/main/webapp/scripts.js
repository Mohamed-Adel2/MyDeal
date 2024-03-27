/**
 ************************* to know in which html file im in currently ************************************
 */
$(document).ready(function () {
    $.ajax({
        url: window.location.href, // Fetch the current URL
        type: 'GET',
        success: function (data) {
            // Extract the file name from the URL
            var fileName = window.location.pathname.split('/').pop();// Here you can perform actions based on the file name
        },
        error: function (xhr, status, error) {
        }
    });
});
