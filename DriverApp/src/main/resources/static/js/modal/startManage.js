$(document).ready(function() {
    $(".cancel").on("click", function() {
        $(".modal-overlay").css("display", "none");
    })
    $(".start").on("click",function(){
        window.location.href="/manage";
    })
    $(".modal-overlay").on("click", function() {
       $(".modal-overlay").css("display", "none");
    });
});