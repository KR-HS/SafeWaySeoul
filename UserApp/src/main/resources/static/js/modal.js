$(document).ready(function(){
    $(".addChild, .register-link").on('click',function(){
        // window.location.href="/regChild"
        $(".addModal").css("display","block");
    })

    // 자녀 수정 버튼 클릭 시 모달 띄우기
    $(".edit-child-btn").on('click', function() {
        const childKey = $(this).data("child-id");
        console.log("수정할 자녀 childKey:", childKey);

        $(".addModal").css("display", "block");
    });

    $(".close").on('click',function(){
        $(".modal").css("display","none");
    })

    // $(".modal").on('click',function(){
    //     if(event.target.classList.contains('modal')){
    //         $(this).css("display","none");
    //     }
    // });

    // 아이 등록 모달 바깥 클릭 시 닫기
    $(".addModal").on("click", function (e) {
        // e.target이 정확히 addModal 자체일 때만 (자식 요소 클릭 시 무시)
        if (e.target.classList.contains("addModal")) {
            $(".addModal").fadeOut();
        }
    });

    // 아이 정보 수정 모달 창에 DB 데이터 값 미리 가져오기
    $(".edit-child-btn").on('click', function() {
        const childKey = $(this).data("child-id");
        $(".addModal").css("display", "block");

        $.ajax({
            url: "/child/detail",
            method: "GET",
            data: { childKey: childKey },
            success: function(child) {
                $("input[name='childKey']").val(child.childKey);

                $("input[name='childName']").val(child.childName);
                $("input[name='childBirth']").val(child.childBirth);
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
            },
            error: function () {
                alert("자녀 정보를 불러오지 못했습니다.");
            }
        });
    });

    //초기화 함수
    function resetChildModal() {

        $("input[name='childKey']").val("");
        $("input[name='childName']").val("");
        $("input[name='childBirth']").val("");
        $("input[name='childGender']").prop("checked", false);

        $(".girl").removeClass("selected");
        $(".boy").removeClass("selected");

        $("form[name='childForm']").attr("action", "/child/regist");

        $(".submit-btn").text("등록하기");

        $(".modalWrap h2").text("아이의 첫걸음");
        $(".upload-section p:first").text("내 아이 사진 등록하기");

        $(".photo-upload").css("background-image", "none");
    }

    //닫기 버튼 누를 때 초기화
    $(".close").on('click', function () {
        $(".modal").css("display", "none");
        resetChildModal();
    });

    //모달 외부 클릭해서 커질 때도 초기화
    $(".addModal").on("click", function (e) {
        if (e.target.classList.contains("addModal")) {
            $(".addModal").fadeOut();
            resetChildModal();
        }
    });


});
