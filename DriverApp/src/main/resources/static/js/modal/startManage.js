$(document).ready(function() {
    // 모달 띄우기 + recordKey 저장
    let selectedRecordKey = null;
    let recordName;
    let recordCarName;

    $(".active, .scheduled").on("click", function() {
        selectedRecordKey = $(this).data("record-key");
        recordName = $(this).find(".subtitle").data("record-name");
        recordCarName = $(this).find(".title").data("record-car-name");
        $(".modal-title").text(recordName);
        $(".modal-subtitle").text(recordCarName);
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