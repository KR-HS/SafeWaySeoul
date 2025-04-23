$(document).ready(function(){
    $(".addChild, .register-link").on('click',function(){
        // window.location.href="/regChild"
        $(".addModal").css("display","block");
    })
    $(".close").on('click',function(){
        $(".modal").css("display","none");
    })

    // $(".modal").on('click',function(){
    //     if(event.target.classList.contains('modal')){
    //         $(this).css("display","none");
    //     }
    // });

    // 아이 등록 모달 바깥 클릭 시 닫기
    $(".addModal").on("click", function (e) {
        // e.target이 정확히 addModal 자체일 때만 (자식 요소 클릭 시 무시)
        if (e.target.classList.contains("addModal")) {
            $(".addModal").fadeOut();
        }
    });



});
