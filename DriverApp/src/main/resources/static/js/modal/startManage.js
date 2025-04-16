$(document).ready(function() {
    $(".cancel").on("click", function() {
        window.history.back();
    })
    $(".start").on("click",function(){
        window.location.href="/manage";
    })
});