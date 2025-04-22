// 전역 변수로 선언하여 모든 함수에서 접근 가능하게 함
var map;
var recordKey;

$(document).ready(function(){

    var urlParams = new URLSearchParams(window.location.search);
    recordKey = urlParams.get('recordKey');

    // 지도 초기화 - document.ready 바로 아래에 위치시킴
    var mapContainer = document.getElementById('map');
    var mapOption = {
        center: new kakao.maps.LatLng(37.5665, 126.9780),
        level: 5
    };

    map = new kakao.maps.Map(mapContainer, mapOption); // 전역 변수 사용

    /////////////////////기존코드
    $(".app-content>.content-header>.backBtn").on('click',function(){
       window.location.href="/home";
   })

    function updateBoardingStatus() {
        var total = $('.list-time').length;
        var done = $('.list-time.done').length;
        var remain = total - done;
        $('.boarding-status').text(remain + "명 남음 - " + done + "명 완료");
    }
    updateBoardingStatus();

    $(document).on('click', '.list-time', function() {
        var $this = $(this);
        var $card = $this.closest('.list-card');
        var childKey = $card.data('child-key');
        var newState = $this.hasClass('done') ? '하차' : '하차완료';

        // 서버에 상태 업데이트 요청
        $.ajax({
            url: '/updateDropState',
            type: 'POST',
            data: {
                recordKey: recordKey,
                childKey: childKey,
                dropState: newState
            },
            success: function(response) {
                if (response === 'success') {
                    // UI 업데이트
                    if (newState === '하차완료') {
                        $this.addClass('done').text('하차완료');
                        $this.closest('.list-box').addClass('done');
                    } else {
                        $this.removeClass('done').text('하차');
                        $this.closest('.list-box').removeClass('done');
                    }
                    updateBoardingStatus();
                } else {
                    alert('상태 업데이트에 실패했습니다.');
                }
            },
            error: function() {
                alert('서버와 통신 중 오류가 발생했습니다.');
            }
        });
    });

    $(document).on('click', '.list-img', function() {
        //아이 상세 보기 모달창 띄우기
        let $card = $(this).closest(".list-card");
        let childName = $card.data("child-name");
        let parentName = $card.data("parent-name");
        let address = $card.data("address");
        let kinderName = $card.data("kinder-name");
        let parentPhone = $card.data("parent-phone");
        let childGender = $card.data("child-gender");

        $(".name").text(childName);
        if(childGender=='F') {
            $(".childGender").text("여");
        } else {
            $(".childGender").text("남");
        }
        $(".parentName").text(parentName);
        $(".address").text(address);
        $(".parentPhone").text(parentPhone);
        $(".kinder").text(kinderName);

        $(".addModal").css("display","block");
    });

    $(".close").on('click',function(){
        $(".modal").css("display","none");
    })

    $(".modal").on('click', function () {
        if (event.target.className.includes('modal')) {
            $(this).css("display", "none");
        }

///////////////////////////////////////////////////////경로관련

        // 경로 그리기 버튼 이벤트 (한 번만)
        $('#drawRouteBtn').on('click', function() {
            const startAddr = $('#startAddress').val();
            const endAddr = $('#endAddress').val();
            if (!startAddr || !endAddr) {
                alert("출발지와 도착지를 모두 입력하세요.");
                return;
            }

            const geocoder = new kakao.maps.services.Geocoder();

            // 1. 출발지 주소 → 좌표 변환
            geocoder.addressSearch(startAddr, function (startResult, status1) {
                if (status1 !== kakao.maps.services.Status.OK) {
                    alert("출발지 주소를 찾을 수 없습니다.");
                    return;
                }
                const startX = startResult[0].x;
                const startY = startResult[0].y;

                // 2. 도착지 주소 → 좌표 변환
                geocoder.addressSearch(endAddr, function (endResult, status2) {
                    if (status2 !== kakao.maps.services.Status.OK) {
                        alert("도착지 주소를 찾을 수 없습니다.");
                        return;
                    }
                    const endX = endResult[0].x;
                    const endY = endResult[0].y;

                    // 3. 백엔드에 경로 요청
                    fetch(`/api/map/route?startX=${startX}&startY=${startY}&endX=${endX}&endY=${endY}`)
                        .then(res => res.json())
                        .then(coordinates => {
                            // 지도에 경로 그리기
                            const path = [];
                            for (let i = 0; i < coordinates.length; i += 2) {
                                path.push(new kakao.maps.LatLng(coordinates[i + 1], coordinates[i]));
                            }
                            // 기존 폴리라인/마커 지우기(선택)
                            if (window.routeLine) window.routeLine.setMap(null);
                            window.routeLine = new kakao.maps.Polyline({
                                map: map,
                                path: path,
                                strokeWeight: 5,
                                strokeColor: '#FF0000',
                                strokeOpacity: 0.7
                            });
                            // 출발/도착 마커 표시
                            if (window.startMarker) window.startMarker.setMap(null);
                            if (window.endMarker) window.endMarker.setMap(null);
                            window.startMarker = new kakao.maps.Marker({
                                map: map,
                                position: path[0]
                            });
                            window.endMarker = new kakao.maps.Marker({
                                map: map,
                                position: path[path.length - 1]
                            });
                            // 지도 영역 조정
                            const bounds = new kakao.maps.LatLngBounds();
                            path.forEach(p => bounds.extend(p));
                            map.setBounds(bounds);
                        });
                });
            });
        });

    });
});





