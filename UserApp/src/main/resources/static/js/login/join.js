$(document).ready(function() {

    // flatpickr 초기화
    var datepicker = flatpickr(".birth", {
        altInput: true,
        altFormat: "Y년 m월 d일",
        locale: 'ko',
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




    // 아이디 중복체크
    $(".check-btn").on("click", function() {
        var email = $(".id").val()

        if(email == "aaa") {
            console.log("중복");
        } else {
            console.log("사용가능");
        }
    })

    // 비밀번호 확인
    $(".pass-check,.pass").on("change", function() {
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


    // 우편번호찾기
    $('.address-find-btn, .address-number, .address-road').on('click', function () {
        // Postcode 객체를 외부에서 참조할 수 있도록 변수에 할당
       new daum.Postcode({
            oncomplete: function (data) {
                // 우편번호와 주소 정보를 입력 필드에 자동으로 채워줍니다.
                $('.address-number').val(data.zonecode); // 5자리 우편번호
                $('.address-road').val(data.roadAddress); // 도로명 주소
                // $('#jibunAddress').val(data.jibunAddress); // 지번 주소 (선택적)

                // 예: 건물명이 있다면 표시하거나 안내 문구 등 추가 가능
                var fullAddr = data.roadAddress;

                // 좌표 변환
                var geocoder = new kakao.maps.services.Geocoder();
                geocoder.addressSearch(fullAddr, function (result, status) {
                    if (status === kakao.maps.services.Status.OK) {
                        var lat = result[0].y;
                        var lng = result[0].x;

                        console.log("위도:", lat);
                        console.log("경도:", lng);

                        // 예: 좌표를 hidden input에 넣기
                        // $('.address-lat').val(lat);
                        // $('.address-lng').val(lng);
                    }
                });

                // 우편번호 선택 후 주소 입력이 끝나면 창을 닫기
                self.close(); // Postcode 창 닫기
            }
            // 우편번호 검색 창 열기
        }).open({autoClose:true});


    });
    

    
    // 회원가입 버튼
    $(".join-btn").on("click",function(){
        // **회원가입 버튼누를시 값 제대로 들어갔는지, 중복인지 확인 기능 추가**

        document.joinForm.submit();
    })


});