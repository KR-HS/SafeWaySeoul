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
                alert("댓글 작성을 완료했습니다.");
                window.location.reload();
                },
            error: function () {
                alert('댓글 등록 중 오류가 발생했습니다.');
            }
        });
    });
});