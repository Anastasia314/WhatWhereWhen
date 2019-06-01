$('#login-form-link').click(function (event) {
    $('#login-form').delay(100).fadeIn(100);
    $('#register-form').fadeOut(100);
    $('#register-form-link').removeClass('active');
    $('#login-form-link').addClass('active');
    $('#register-form-link-div').addClass('dis-active');
    $('#server-message').empty();
    event.preventDefault();
});
$('#register-form-link').click(function (event) {
    $('#register-form').delay(100).fadeIn(100);
    $('#login-form').fadeOut(100);
    $('#login-form-link').removeClass('active');
    $('#register-form-link').addClass('active');
    $('#login-form-link-div').addClass('dis-active');
    $('#server-message').empty();
    event.preventDefault();
});


