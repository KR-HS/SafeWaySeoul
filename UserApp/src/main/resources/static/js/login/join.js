$(document).ready(function () {

    new AirDatepicker('.birth', {
        autoClose: true,
        dateFormat: 'yyyy-MM-dd',
        startView: 'years',
        view: 'days',
        maxDate: new Date(),
        locale: {
            days: ['일', '월', '화', '수', '목', '금', '토'],
            daysShort: ['일', '월', '화', '수', '목', '금', '토'],
            daysMin: ['일', '월', '화', '수', '목', '금', '토'],
            months: ['1월', '2월', '3월', '4월', '5월', '6월',
                '7월', '8월', '9월', '10월', '11월', '12월'],
            monthsShort: ['1월', '2월', '3월', '4월', '5월', '6월',
                '7월', '8월', '9월', '10월', '11월', '12월'],
            today: '오늘',
            clear: '지우기',
            dateFormat: 'yyyy-MM-dd',
            firstDay: 0
        }
    });


    // 아이디 중복 체크
    $(".check-btn").on("click", function () {
        var email = $(".id").val();
        if (email === "aaa") {
            console.log("중복");
        } else {
            console.log("사용가능");
        }
    });

    // 비밀번호 확인
    $(".pass-check, .pass").on("change", function () {
        var password = $(".pass").val();
        var passCheck = $(".pass-check").val();

        if (password === passCheck) {
            $(".passMsg").text("비밀번호가 일치합니다").css("color", "green");
        } else {
            $(".passMsg").text("비밀번호가 일치하지 않습니다.").css("color", "red");
        }
    });

    // 우편번호 찾기
    $(".address-find-btn, .address-number, .address-road").on("click", function () {
        new daum.Postcode({
            oncomplete: function (data) {
                $(".address-number").val(data.zonecode);
                $(".address-road").val(data.roadAddress);

                var fullAddr = data.roadAddress;

                var geocoder = new kakao.maps.services.Geocoder();
                geocoder.addressSearch(fullAddr, function (result, status) {
                    if (status === kakao.maps.services.Status.OK) {
                        var lat = result[0].y;
                        var lng = result[0].x;

                        console.log("위도:", lat);
                        console.log("경도:", lng);
                    }
                });

                self.close();
            }
        }).open({ autoClose: true });
    });

    // 회원가입 버튼 클릭
    $(".join-btn").on("click", function () {
        document.joinForm.submit();
    });
});
