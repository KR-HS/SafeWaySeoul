$(document).ready(function() {
    $(".active").on("click", function() {
        $(".modal-overlay").css("display", "block");
    });

    $(".scheduled").on("click", function() {
        $(".modal-overlay").css("display", "block");
    });
});