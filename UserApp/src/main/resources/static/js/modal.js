$(document).ready(function(){
    $(".addChild, .register-link").on('click',function(){
        $(".addModal").css("display","block");
    });

    // 자녀 수정 버튼 클릭 시 모달 띄우기 및 정보 채우기
    $(".edit-child-btn").on('click', function() {
        const childKey = $(this).data("child-id");
        console.log("수정할 자녀 childKey:", childKey);

        $(".addModal").css("display", "block");

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

                if (child.profileImageUrl) {
                    $(".photo-upload").css("background-image", "url('" + child.profileImageUrl + "')");
                }

                // ✅ KM_PICKUP 스위치 값 반영
                if (child.kmPickup === 'Y') {
                    $("input[name='kmPickup']").prop("checked", true);
                } else {
                    $("input[name='kmPickup']").prop("checked", false);
                }
            },
            error: function () {
                alert("자녀 정보를 불러오지 못했습니다.");
            }
        });
    });

    // 초기화 함수
    function resetChildModal() {
        $("input[name='childKey']").val("");
        $("input[name='childName']").val("");
        $("input[name='childBirth']").val("");
        $("input[name='childGender']").prop("checked", false);
        $("input[name='kmPickup']").prop("checked", true); // ✅ 기본값은 true로 초기화

        $(".girl").removeClass("selected");
        $(".boy").removeClass("selected");

        $("form[name='childForm']").attr("action", "/child/regist");
        $(".submit-btn").text("등록하기");
        $(".modalWrap h2").text("아이의 첫걸음");
        $(".upload-section p:first").text("내 아이 사진 등록하기");
        $(".photo-upload").css("background-image", "none");
    }

    // 닫기 버튼 누를 때 초기화
    $(".close").on('click', function () {
        $(".modal").css("display", "none");
        resetChildModal();
    });

    // 모달 외부 클릭해서 닫힐 때도 초기화
    $(".addModal").on("click", function (e) {
        if (e.target.classList.contains("addModal")) {
            $(".addModal").fadeOut();
            resetChildModal();
        }
    });
});