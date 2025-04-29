$(document).ready(function() {
    // 모달 띄우기 + recordKey 저장
    let selectedRecordKey = null;
    let recordName;
    let recordCarName;
    let selectedDriveInfoName;
    let intervalId = null;
    let recordKey = null;

    $(".active, .scheduled").on("click", function() {
        selectedRecordKey = $(this).data("record-key");
        recordName = $(this).find(".subtitle").data("record-name");
        recordCarName = $(this).find(".title").data("record-car-name");
        selectedDriveInfoName = $(this).data("driveinfo-name")
        $(".modal-title").text(recordName);
        $(".modal-subtitle").text(recordCarName);
        console.log(selectedDriveInfoName);

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
            window.location.href = "/manage?recordKey=" + selectedRecordKey + "&&driveInfoName=" + selectedDriveInfoName;
            // 실시간 좌표 넘겨주는 함수 실행
            // startDriving(selectedRecordKey);
        }
    });

    // 모달 닫기
    $(".cancel, .modal-overlay").on("click", function() {
        $(".modal-overlay").css("display", "none");
    });


    // 실시간 websocket 좌표 넘겨주는 함수
    function startDriving(key) {
        recordKey = key;

        fetch(`/driver/location/start?recordKey=${recordKey}`, {
            method: "POST"
        }).then(() => {
            alert("운행 시작됨");

            // 2.5초마다 위치 전송
            intervalId = setInterval(() => {
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(position => {
                        const latitude = position.coords.latitude;
                        const longitude = position.coords.longitude;

                        try{
                        fetch("/driver/location/send", {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify({
                                recordKey: recordKey,
                                latitude: latitude.toString(),
                                longitude: longitude.toString()
                            })
                        })
                            .then()
                            .catch(function(err){
                                alert(err);
                            });
                        }catch(e){
                            alert(e);
                        }


                    });
                }
            }, 2500); // 2.5초마다 실행
        });
    }

    function stopDriving() {
        fetch("/driver/location/stop", {
            method: "POST"
        }).then(() => {
            alert("운행 종료됨");
            if (intervalId !== null) {
                clearInterval(intervalId); // 인터벌 중단
                intervalId = null;
            }
        });
    }
});