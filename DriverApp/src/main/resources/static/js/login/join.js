$(document).ready(function() {

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


    // 아이디 중복 확인
    var isIdChecked = false;


    $(".check-btn").click(function () {
        var userId = $("input[name='userId']").val().trim();
        if (userId === "") {
            alert("아이디를 입력하세요.");
            return;
        }
        $.ajax({
            url: "checkId",
            type: "POST",
            data: JSON.stringify({ userId: userId }),
            contentType: "application/json",
            dataType: "json",
            success: function (result) {
                if (result>0) {
                    alert("이미 사용 중인 아이디입니다.");
                    isIdChecked = false;
                } else {
                    alert("사용 가능한 아이디입니다.");
                    isIdChecked = true;
                }
            },
            error: function () {
                alert("중복확인중 에러가 발생하였습니다.");
                isIdChecked = false;
            }
        });
    });

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



    // 회원가입 버튼 클릭
    $(".join-btn").on("click", function () {

        // 주소 유효성 검사////////////////////////////////////////
        var address = $(".address-road").val();
        var postcode = $(".address-number").val();
        var detail = $(".address-detail").val();

        // 주소 또는 우편번호가 비어있으면
        if (address === "" || postcode === "" || detail === "") {
            alert("주소를 모두 입력해주세요.");
            return;
        }
        // ////////////////////////////////////////////////////

        if (!isIdChecked) {
            alert("아이디 중복 확인을 해주세요.");
            return;
        }

        document.joinForm.submit();
    });

    //유치원 주소
    $("input[name='userAddressDetail']").on("input click", function () {
        const keyword = $(this).val();
        if (keyword.length < 2) {
            $("#autocomplete-list").hide();
            return;
        }

        fetch(`/user/find?name=${encodeURIComponent(keyword)}`, { method: 'get' })
            .then(res => {
                return res.json();
            })
            .then(data => {
                console.log(data);

                let html = "";

                if (data.length > 0) {
                    data.forEach(kinder => {
                        html += `<div class="autocomplete-item" data-name="${kinder.kinderName}" data-postcode="${kinder.kinderPostcode}" data-address="${kinder.kinderAddress}"> <div class="kinderNameSt">${kinder.kinderName}</div><div class="KinderAddressSt">${kinder.kinderAddress}</div></div>`;
                    });
                } else {
                    html = `<div class="autocomplete-item" style="color:#999;">검색 결과 없음</div>`;
                }

                $("#autocomplete-list").html(html).show();
            })
            .catch(err => {
                console.error("자동완성 요청 실패", err);
                $("#autocomplete-list").hide();
            });
    });

// 자동완성 항목 클릭 시 input 값으로 입력
    $(document).on("click", ".autocomplete-item", function () {
        const selectedName = $(this).data("name");
        const selectedPostcode = $(this).data("postcode");
        const selectedAddress = $(this).data("address");
        $("input[name='userAddressDetail']").val(selectedName);
        $("input[name='userPostcode']").val(selectedPostcode);
        $("input[name='userAddress']").val(selectedAddress);
        $("#autocomplete-list").hide();
    });

// 바깥 클릭 시 자동완성 닫기
    $(document).on("click", function (e) {
        if (!$(e.target).closest(".regChild-kinder-search-wrap").length) {
            $("#autocomplete-list").hide();
        }
    });


});