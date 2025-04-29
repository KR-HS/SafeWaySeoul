$(function () {
    // 자녀 정보가 아예 없는 경우
    event.stopPropagation(); //이벤트 버블링으로 인해서 부모상자 이벤트가 먼저실행되는데 이거 막음..
    if ($('.child-list .child-card').length === 0) {
        $('.child-list').hide();
        $('.child-register-box').show();
    }



    // 자녀 삭제 관련 변수
    var selectedChildKey = null;
    var selectedCard = null;

    // 삭제 버튼 클릭 시 모달창 열기
    $('.delete-child-btn').on('click', function () {

        // 모달 초기화
        resetDeleteModal();

        selectedChildKey = $(this).data('child-id');
        selectedCard = $(this).closest('.child-card');
        $('#delete-confirm-modal').fadeIn().css('display', 'flex');
    });

    // 삭제 확인 기본 처리 함수 (재사용 가능)
    function defaultDeleteHandler() {
        $.ajax({
            url: '/child/delete',
            method: 'POST',
            data: { childKey: selectedChildKey },
            success: function (res) {
                if (res === 'success') {
                    $('.modal-message').text('정상적으로 처리되었습니다');
                    $('#cancel-delete-btn').hide();
                    $('#confirm-delete-btn')
                        .text('확인')
                        .off('click')
                        .on('click', function () {
                            selectedCard.remove();
                            $('#delete-confirm-modal').fadeOut();
                            resetDeleteModal();

                            // 다시 기본 삭제 핸들러 바인딩
                            $('#confirm-delete-btn').off('click').on('click', defaultDeleteHandler);
                        });
                } else {
                    alert('삭제에 실패했습니다.');
                }
            },
            error: function () {
                alert('요청 처리 중 오류가 발생했습니다.');
            }
        });
    }

    // 삭제 확인 버튼에 기본 핸들러 바인딩
    $('#confirm-delete-btn').on('click', defaultDeleteHandler);

    // 모달 상태 초기화 함수
    function resetDeleteModal() {
        $('.modal-message').text('선택하신 카드를 삭제하시겠습니까?');
        $('#cancel-delete-btn').show();
        $('#confirm-delete-btn').text('확인').off('click').on('click', defaultDeleteHandler);
    }

    // 취소 버튼 또는 바깥 클릭 시 모달창 닫기 + 리다이렉트 처리
    $('#cancel-delete-btn, #delete-confirm-modal').on('click', function (e) {
        if (e.target.id === 'cancel-delete-btn' || e.target.id === 'delete-confirm-modal') {
            const isSuccess = $('.modal-message').text().includes('정상적으로 처리되었습니다');
            $('#delete-confirm-modal').fadeOut();
            resetDeleteModal();

            if (isSuccess) {
                window.location.href = '/child'; // 리다이렉트
            }
        }
    });

    // 등록 성공 시 모달 표시
    if ($("body").data("reg-success") === true) {
        $("#child-success-modal").fadeIn().css("display", "flex");
    }

// 확인 버튼 클릭 시 모달 닫기
    $("#child-success-confirm-btn").on("click", function () {
        $("#child-success-modal").fadeOut();
    });

    // 등록 성공 모달 바깥 클릭 시 해당 모달만 닫기
    $("#child-success-modal").on("click", function (e) {
        if (e.target.id === "child-success-modal") {
            $("#child-success-modal").fadeOut();
        }
    });

    $(".edit-user-btn").on("click", function () {

        location.href = "/user/userInfoModi";
    });

    /*자녀카드 클릭시 해당아이의 tracing 화면으로 넘어가기*/

    $(".child-card").on("click",function(){

        //이벤트 버블링으로 인해서 부모상자 이벤트가 먼저실행되는데 이거 막음..
        if ($(event.target).closest('.edit-child-btn, .delete-child-btn').length > 0) return; //수정 삭제버튼 안먹는 방지

        if ($(this).hasClass('child-register-box')) {
            return;
        }

        var childKey = $(this).data('childId');
        var recordKey = $(this).data('childRecordKey');

        if (recordKey == null || recordKey === "" || recordKey == 0) {
            alert("운행예정정보가 없습니다");
        } else {
            window.location.href = "/tracing?childKey="+childKey+"&recordKey="+recordKey;
        }
        console.log(childKey, recordKey);


    })

});