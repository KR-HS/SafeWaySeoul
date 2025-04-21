$(document).ready(function() {
    $(".cancel").on("click", function() {
        $(".modal-overlay").css("display", "none");
    })
    $(".start").on("click",function(){
        window.location.href="/manage?recordKey=1";
    })
    $(".modal-overlay").on("click", function() {
       $(".modal-overlay").css("display", "none");
    });
});