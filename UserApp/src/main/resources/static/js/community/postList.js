$(document).ready(function() {

   $(".post-write").on("click",function () {
       console.log("click");
       window.location.href="/community/postWrite";
   });

   $(".search-icon").on("click", function () {
        $(".search").css("display", "block");
   });
});