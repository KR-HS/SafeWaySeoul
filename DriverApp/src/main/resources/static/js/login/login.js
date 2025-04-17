$(document).ready(function() {
    $(".email").focus();

    $(".login-btn").on("click", function() {
        var email = $(".email").val()
        var password = $(".password").val()
        console.log(email, password);

        document.loginForm.submit();
    })

    


});


