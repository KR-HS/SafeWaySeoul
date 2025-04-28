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

    // 수정 폼 제출
    $(".userInfoModiForm").on("submit", function (e) {
        e.preventDefault();

        const pw = $(".pass").val();
        const pwCheck = $(".pass-check").val();

        if (pw !== pwCheck) {
            alert("비밀번호가 일치하지 않습니다.");
            return; // 폼 제출 막기
        }

        // 일치하는 경우
        this.submit();
    });

    // 프로필 미리보기 기능
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

    // 프로필 이미지 클릭 시 파일 선택창 열기
    $(".profile-image-wrapper").on("click", function () {
        $("#profileUploadInput").click();
    });

    // 회원 탈퇴 버튼 클릭 이벤트
    $(".withdraw-btn").on("click", function () {
        if (confirm("정말로 회원 탈퇴하시겠습니까? 탈퇴 시 모든 정보가 삭제됩니다.")) {
            $.ajax({
                url: "/user/delete", // 이 경로는 컨트롤러에서 구현해야 함
                type: "POST",
                success: function () {
                    alert("회원 탈퇴가 완료되었습니다.");
                    // 세션/쿠키 제거 후 로그인 페이지로 이동
                    window.location.href = "/user/login";
                },
                error: function () {
                    alert("회원 탈퇴 중 오류가 발생했습니다. 다시 시도해주세요.");
                }
            });
        }
    });





});
