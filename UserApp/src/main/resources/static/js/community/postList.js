$(document).ready(function() {

   $(".post-write").on("click",function () {
       console.log("click");
       window.location.href="/community/postWrite";
   });

    $(".post-card").on("click",function () {
        var postKey = $(this).data("post-key");

        window.location.href="/community/postDetail?postKey=" + postKey;
    });

   $(".search-icon").on("click", function () {
        $(".search").css("display", "block");
   });
});