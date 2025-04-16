$(document).ready(function(){
   $(".app-content>.content-header>.backBtn").on('click',function(){
       window.location.href="/home";
   })

    function updateBoardingStatus() {
        var total = $('.list-time').length;
        var done = $('.list-time.done').length;
        var remain = total - done;
        $('.boarding-status').text(remain + "명 남음 - " + done + "명 완료");
    }
    updateBoardingStatus();
    $(document).on('click', '.list-time', function() {
        if ($(this).hasClass('done')) return;
        $(this).text('하차완료').addClass('done');
        $(this).closest('.list-box').addClass('done');
        updateBoardingStatus();
    });
    $(document).on('click', '.list-img', function() {
        //아이 상세 보기 모달창 띄우기
        $(".addModal").css("display","block");
    });

    $(".close").on('click',function(){
        $(".modal").css("display","none");
    })
});