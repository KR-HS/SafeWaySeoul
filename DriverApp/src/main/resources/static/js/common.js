$(document).ready(function(){
    var path = window.location.pathname;


    $(".logout").on("click",function(){
        event.preventDefault();
        if(confirm("로그아웃 하시겠습니까?")){
            window.location.href="/user/logout";
        }else return;

    });


});

