$(document).ready(function() {
    // 모달 띄우기 + recordKey 저장
    let selectedRecordKey = null;

    $(".active, .scheduled").on("click", function() {
        selectedRecordKey = $(this).data("record-key");
        $(".modal-overlay").css("display", "block");
    });

    // 운행시작 버튼 누르면 recordKey를 URL로 넘김
    $(".start").on("click", function() {
        if(selectedRecordKey) {
            window.location.href = "/manage?recordKey=" + selectedRecordKey;
        }
    });

    // 모달 닫기
    $(".cancel, .modal-overlay").on("click", function() {
        $(".modal-overlay").css("display", "none");
    });
});