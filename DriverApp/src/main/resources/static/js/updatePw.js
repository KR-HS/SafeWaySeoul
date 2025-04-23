$(document).ready(function() {

    //재설정 버튼
    $(".btn").on("click", function () {
        var pw = $(".pw").val();
        var pwCheck = $(".pwCheck").val();

        if(pw === '' || pwCheck === '') {
            alert("비밀번호를 입력해주세요");
            return;

        } else {
            if (pw === pwCheck) {
                document.updatePwForm.submit();
            } else {
                alert("비밀번호가 일치하지 않습니다.");
                $(".pw").focus();
            }
        }
    });

});