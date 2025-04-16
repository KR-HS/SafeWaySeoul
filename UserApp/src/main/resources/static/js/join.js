$(document).ready(function() {

    // flatpickr 초기화
    var datepicker = flatpickr(".birth", {
        dateFormat: "Y-m-d", // 날짜 포맷
        maxDate: "today", // 오늘 이전 날짜만 선택 가능
        onReady: function(selectedDates, dateStr, instance) {
            // 초기 상태에서 placeholder만 보이도록 날짜 값을 초기화
            instance.clear();  // 날짜를 비워서 placeholder가 보이도록 처리
        },
        onChange: function(selectedDates, dateStr, instance) {
            if (selectedDates.length === 0) {
                instance.input.placeholder = "생년월일 8자리";  // 날짜를 선택하지 않으면 placeholder 표시
            }
        }
    });

    // 날짜 입력란에 포커스가 가면 flatpickr 날짜 선택기 열기
    $(".birth").on("focus", function() {
        datepicker.open();  // input에 focus 시 날짜 선택기가 열리도록
    });




    $(".check-btn").on("click", function() {
        var email = $(".id").val()

        if(email == "aaa") {
            console.log("중복");
        } else {
            console.log("사용가능");
        }
    })

    $(".pass-check").on("change", function() {
        var password = $(".pass").val()
        var passCheck = $(".pass-check").val()
        

        if(password == passCheck) {
            console.log("일치");
            $(".passMsg").text("비밀번호가 일치합니다").css("color", "green")
        } else {
            console.log("불일치");
            $(".passMsg").text("비밀번호가 일치하지 않습니다.").css("color", "red")
        }
    })

    $(".join-btn").on("click",function(){
        // **회원가입 버튼누를시 값 제대로 들어갔는지, 중복인지 확인 기능 추가**

        document.joinForm.submit();
    })


});