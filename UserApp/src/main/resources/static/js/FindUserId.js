$(document).ready(function() {
    $(".btn-outline").on("click", function() {
        var userId = $(".user-id").val()

        console.log(userId);

        document.loginForm.submit();
    })




});