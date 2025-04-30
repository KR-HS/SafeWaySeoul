$(document).ready(function() {
    $('.comment-submit').on('click', function () {
        const commentText = $('.comment-input').val().trim();

        if (commentText === '') {
            alert('댓글을 입력해주세요.');
            return;
        }
        var postKey = $(".comment-input-area").data("post-key");
        $.ajax({
            url: '/community/commentWrite',       // 서버의 댓글 저장 엔드포인트
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                postKey: postKey,               // 글 번호 또는 식별자
                commentContent: commentText
            }),
            success: function (response) {
                window.location.reload();
                },
            error: function () {
                alert('댓글 등록 중 오류가 발생했습니다.');
            }
        });
    });

    $('.comment-input').on('keydown', function (e) {
        if(e.keyCode === 13) {
            const commentText = $('.comment-input').val().trim();

            if (commentText === '') {
                alert('댓글을 입력해주세요.');
                return;
            }
            var postKey = $(".comment-input-area").data("post-key");
            $.ajax({
                url: '/community/commentWrite',       // 서버의 댓글 저장 엔드포인트
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    postKey: postKey,               // 글 번호 또는 식별자
                    commentContent: commentText
                }),
                success: function (response) {
                    window.location.reload();
                },
                error: function () {
                    alert('댓글 등록 중 오류가 발생했습니다.');
                }
            });
        }
    });

    // 수정,삭제
    $('.post-menu').on('click', function(e) {
        e.stopPropagation();
        $('.post-menuList').fadeIn(400); //.fadeIn(400) > 0.4초동안 부드럽게 나타남
    });

    //바깥 클릭 시 닫기
    $(document).on('click', function(e) {
        if ($('.post-menuList').is(':visible')) {
            // 드로어와 ⋮ 버튼이 아닌 영역 클릭 시 닫기
            if (!$(e.target).closest('.post-menuList, .post-menu').length) {
                $('.post-menuList').slideUp(400); //.slideUp(200) > 부드럽게 내려감
            }
        }
    });

    // 닫기 버튼 클릭 시 닫기
    $('.closeBtn').on('click', function() {
        $('.post-menuList').slideUp(400); //.slideUp(200) > 부드럽게 내려감
    });

    // 수정/삭제 버튼
    $('.reBtn').on('click', function() {

        $('.post-menuList').slideUp(400);
    });
    $('.delBtn').on('click', function() {
        if(confirm("정말 삭제하시겠습니까?")) {
            $('.post-menuList').slideUp(400);
        }
    });


});