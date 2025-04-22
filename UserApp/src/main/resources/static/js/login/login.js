$(document).ready(function() {
    $(".email").focus();

    $(".login-btn").on("click", function() {
        var email = $(".email").val()
        var password = $(".password").val()
        console.log(email, password);

        document.loginForm.submit();
    });

    $(".email, .password").on("keydown", function(e) {
        if(e.keyCode == 13) {
            $(".login-btn").click();
        }
    });

});