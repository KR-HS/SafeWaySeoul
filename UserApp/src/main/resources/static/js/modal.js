$(document).ready(function() {
    $(".addChild, .register-link").on('click', function() {
        $(".addModal").css("display", "block");
    });

    // 자녀 수정 버튼 클릭 시 모달 띄우기 + DB 데이터 가져오기
    $(".edit-child-btn").on('click', function() {
        const childKey = $(this).data("child-id");
        console.log("수정할 자녀 childKey:", childKey);

        $(".addModal").css("display", "block");

        // 스위치 기본 초기화 + 숨기기
        $(".pickup-switch-section").hide();
        $("input[name='kmPickup']").prop("checked", false);
        $("input[name='kmPickup']").prop("disabled", true);

        $.ajax({
            url: "/child/detail",
            method: "GET",
            data: { childKey: childKey },
            success: function(child) {
                $("input[name='childKey']").val(child.childKey);
                $("input[name='childName']").val(child.childName);
                $("input[name='childBirth']").val(child.childBirth);
                $("input[name='regChild-kinder-name']").val(child.kinderVO.kinderName);

                $("form[name='childForm']").attr("action", "/child/update");
                $(".submit-btn").text("수정하기");
                $(".modalWrap h2").text("아이 정보 수정하기");
                $(".upload-section p:first").text("내 아이 사진 수정하기");

                if (child.childGender === 'F') {
                    $("#girl").prop("checked", true);
                    $(".girl").addClass("selected");
                    $(".boy").removeClass("selected");
                } else {
                    $("#boy").prop("checked", true);
                    $(".boy").addClass("selected");
                    $(".girl").removeClass("selected");
                }

                // 스위치 상태 세팅 (DB km_pickup 값에 따라)
                if (child.kmPickup === 'Y') {
                    $("input[name='kmPickup']").prop("checked", true);
                } else {
                    $("input[name='kmPickup']").prop("checked", false);
                }
                $("input[name='kmPickup']").prop("disabled", false);

                // Ajax 성공 후 스위치 영역 보여주기
                $(".pickup-switch-section").show();

                if (child.profileImageUrl) {
                    $(".photo-upload").css("background-image", "url('" + child.profileImageUrl + "')");
                }
            },
            error: function() {
                alert("자녀 정보를 불러오지 못했습니다.");
            }
        });
    });

    // 모달 닫기 및 초기화
    $(".close").on('click', function() {
        $(".modal").css("display", "none");
        resetChildModal();
    });

    $(".addModal").on("click", function(e) {
        if (e.target.classList.contains("addModal")) {
            $(".addModal").fadeOut();
            resetChildModal();
        }
    });

    // 초기화 함수
    function resetChildModal() {
        $("input[name='childKey']").val("");
        $("input[name='childName']").val("");
        $("input[name='childBirth']").val("");
        $("input[name='childGender']").prop("checked", false);

        $("input[name='kmPickup']").prop("checked", false);
        $("input[name='kmPickup']").prop("disabled", true);
        $(".pickup-switch-section").hide(); // 스위치 영역 숨기기

        $(".girl").removeClass("selected");
        $(".boy").removeClass("selected");

        $("form[name='childForm']").attr("action", "/child/regist");
        $(".submit-btn").text("등록하기");
        $(".modalWrap h2").text("아이의 첫걸음");
        $(".upload-section p:first").text("내 아이 사진 등록하기");

        $(".photo-upload").css("background-image", "none");
    }
});