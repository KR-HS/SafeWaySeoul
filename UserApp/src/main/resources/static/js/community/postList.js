$(document).ready(function() {
   $(".post-card").on("click",function () {
       window.location.href="/community/postDetail";
   });

   $(".search-icon").on("click", function () {
        $(".search").css("display", "block");
   });
});