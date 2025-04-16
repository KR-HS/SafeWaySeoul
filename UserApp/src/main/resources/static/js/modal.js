$(document).ready(function(){
    $(".addChild").on('click',function(){
        // window.location.href="/regChild"
        $(".addModal").css("display","block");
    })
    $(".close").on('click',function(){
        $(".modal").css("display","none");
    })

    $(".modal").on('click',function(){
        if(event.target.className.includes('modal')){
            $(this).css("display","none");
        }
    })
})
