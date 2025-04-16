$(document).ready(function(){



    $(".backBtn").on('click',function(){
        history.back();
    })


    $(".mylocation").on('click',function(){
        alert('현재위치버튼작동됨');
    })

    $(".phonecall").on('click',function(){
        alert('통화하기버튼 작동됨');
    })


})