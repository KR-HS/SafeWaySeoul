$(function () {
    // 자녀 정보가 아예 없는 경우
    if ($('.child-list .child-card').length === 0) {
        $('.child-list').hide();
        $('.child-register-box').show();
    }

    // 성별 아이콘 전환
    $('.child-card').each(function () {
        var $card = $(this);
        var profileSrc = $card.find('.child-image img').attr('src');
        var isFemale = profileSrc && profileSrc.includes('female');
        if (!isFemale) {
            $card.find('.child-name img').attr('src', '/img/man.png');
        }
    });

    // 자녀 삭제 관련 변수
    var selectedChildKey = null;
    var selectedCard = null;

    // 삭제 버튼 클릭 시 모달창 열기
    $('.delete-child-btn').on('click', function () {
        selectedChildKey = $(this).data('child-id');
        selectedCard = $(this).closest('.child-card');
        $('#delete-confirm-modal').fadeIn();
        $('#delete-confirm-modal').css('display', 'flex');
    });

    // 삭제 확인 버튼 클릭 시
    $('#confirm-delete-btn').on('click', function () {
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
                        .off('click') // 이전 삭제 이벤트 제거
                        .on('click', function () {
                            selectedCard.remove();
                            $('#delete-confirm-modal').fadeOut(); // 모달 닫기
                        });
                } else {
                    alert('삭제에 실패했습니다.');
                }
            },
            error: function () {
                alert('요청 처리 중 오류가 발생했습니다.');
            }
        });
    });

    // 취소 버튼 또는 바깥 클릭 시 모달창 닫기
    $('#cancel-delete-btn, #delete-confirm-modal').on('click', function (e) {
        if (e.target.id === 'cancel-delete-btn' || e.target.id === 'delete-confirm-modal') {
            $('#delete-confirm-modal').fadeOut();
        }
    });
});
