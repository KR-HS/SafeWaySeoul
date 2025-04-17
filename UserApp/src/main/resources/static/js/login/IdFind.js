$(document).ready(function() {

    $(".find-btn").on("click", function() {
        var name = $(".name").val()
        var phone = $(".phone").val()
        
        if(name.equals("ㄱㄱㄱ" )&& phone.equals("01011111111")) {
            console.log("아이디");
        } else {
            alert("일치하는 정보가 없습니다.")
        }
    })

});