$(document).ready(function() {

    $(".write-finish-btn").on("click",function () {
        var title = $("input[name='postTitle']").val().trim();
        var content = $("textarea[name='postContent']").val().trim();

        if(title === "") {
            alert("제목을 입력해 주세요");
            return false;
        } else if(content === "") {
            alert("내용을 입력해 주세요");
            return false;
        }

    });
});