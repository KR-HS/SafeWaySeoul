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

        // Ajax 호출 추가
        $.ajax({
            url: "/getRemainPassengerCount",
            type: "GET",
            data: { recordKey: selectedRecordKey },
            success: function (count) {
                $(".modal-leftCount").text(count + "명");
            },
            error: function () {
                $(".modal-leftCount").text("조회 실패");
            }
        });

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