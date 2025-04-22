$(document).ready(function() {

    $(".find-btn").on("click", function() {
        var name = $(".name").val()
        var phone = $(".phone").val()
        
        if(name == null && phone == null) {
            alert("정보를 입력해주세요")
        }
    })

});