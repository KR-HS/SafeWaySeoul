$(document).ready(function(){
    var path = window.location.pathname;

    $(".logout").on("click", function(){
        event.preventDefault();
        if(confirm("로그아웃 하시겠습니까?")){
            window.location.href="/user/logout";
        } else {
            return;
        }
    });

    // 하단바 메뉴
    if (path === '/home') {
        $('.home').siblings().removeClass("click");
        $('.home').addClass('click');
    } else if (path === '/child' || path.startsWith('/user')) {
        // '/child' 또는 '/user'로 시작하는 경로는 '자녀관리' 활성화
        $('.child').siblings().removeClass("click");
        $('.child').addClass('click');
    }

    else if (path === '/community/postList') {
        $('.tbd').siblings().removeClass("click");
        $('.tbd').addClass('click');
    }

    $('.app-footer > .menu').on('click', function () {
        if ($(this).hasClass('tbd')) {
            window.location.href = "/community/postList";

        }
        else if($(this).hasClass('home') && path !== '/home'){
            window.location.href="/home";
        }
        else if($(this).hasClass('child') && path !== '/child'){
            window.location.href="/child";
        }
    });
});