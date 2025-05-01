$(document).ready(function () {

    const koreanLocale = {
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
    };

    new AirDatepicker('.birth', {
        autoClose: true,
        dateFormat: 'yyyy-MM-dd',
        startView: 'years',
        view: 'days',
        maxDate: new Date(),
        locale: koreanLocale
    });

    $(".address-find-btn, .address-number, .address-road").on("click", function () {
        new daum.Postcode({
            oncomplete: function (data) {
                $(".address-number").val(data.zonecode);
                $(".address-road").val(data.roadAddress);
            }
        }).open({ autoClose: true });
    });

    // 회원 정보 수정 Ajax 전송
    $(".userInfoModiForm").on("submit", function (e) {
        e.preventDefault();

        const pw = $(".pass").val();
        const pwCheck = $(".pass-check").val();

        if (pw !== pwCheck) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        const formData = new FormData(this);

        $.ajax({
            url: "/user/update-ajax",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function () {
                $("#modi-success-modal").show();
                window.location.href="/child"
            },
            error: function () {
                alert("정보 수정 중 오류가 발생했습니다.");
            }
        });

    });

    // 프로필 미리보기
    $("#profileUploadInput").on("change", function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $("#profilePreview").attr("src", e.target.result);
            };
            reader.readAsDataURL(file);
        }
    });

    // 비밀번호 변경 체크박스 제어
    $('.changePw').on('change', function () {
        const enabled = $(this).is(':checked');
        const pwField = $(document.modiForm.userPw);
        const pwCheckField = $(document.modiForm.userPwCheck);

        pwField.add(pwCheckField).prop('disabled', !enabled)
            .css('backgroundColor', enabled ? '#fff' : '#eee')
            .prop('placeholder', enabled ? '비밀번호를 입력해주세요' : '');
        pwCheckField.prop('placeholder', enabled ? '비밀번호를 한 번 더 입력해주세요' : '');
    });

    // 비밀번호 확인 메시지
    $(".pass, .pass-check").on("keyup change", function () {
        const pw = $(".pass").val();
        const pwCheck = $(".pass-check").val();

        if (pw && pwCheck) {
            if (pw === pwCheck) {
                $(".passMsg").text("비밀번호가 일치합니다").css("color", "#4CAF50");
            } else {
                $(".passMsg").text("비밀번호가 일치하지 않습니다").css("color", "red");
            }
        } else {
            $(".passMsg").text("");
        }
    });

    // 프로필 이미지 클릭 시 파일 업로드
    $(".profile-image-wrapper").on("click", function () {
        $("#profileUploadInput").click();
    });

    // 🔄 탈퇴 버튼 → 커스텀 확인 모달 표시
    $(".withdraw-btn").on("click", function () {
        $("#withdraw-confirm-modal").show();
    });

    // 🔁 확인 클릭 시 Ajax 탈퇴
    $("#withdraw-confirm-yes").on("click", function () {
        $("#withdraw-confirm-modal").hide();
        $.ajax({
            url: "/user/delete",
            type: "POST",
            success: function () {
                $("#withdraw-success-modal").show();
            },
            error: function () {
                alert("회원 탈퇴 중 오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    });

    // ❌ 취소 클릭 시 모달 닫기
    $("#withdraw-confirm-cancel").on("click", function () {
        $("#withdraw-confirm-modal").hide();
    });

    // 수정 성공 모달 확인 클릭 시 닫기
    $("#modi-success-confirm-btn").on("click", function () {
        $("#modi-success-modal").hide();
    });

    // 탈퇴 성공 후 로그인 페이지로 이동
    $("#withdraw-success-confirm-btn").on("click", function () {
        $("#withdraw-success-modal").hide();
        window.location.href = "/user/login";
    });
});