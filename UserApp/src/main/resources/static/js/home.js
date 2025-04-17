$(document).ready(function(){

    // 자녀 관련 모달창 기능
    $(".viewInfo").on('click',function(){
        window.location.href="/tracing";
        // $(".infoModal").css("display","block");
    });



    // 어린이집 운영형태 버튼
    $('.label-wrap').on('click',function(){
        event.stopPropagation(); // 이벤트 버블링 방지
        event.preventDefault(); // label의 기본동작 막기
        $(this).siblings().removeClass('select');
        $(this).addClass('select');
        $(this).find("input[type=radio]").prop('checked',true);

        // 유치원 운영형태에 따른 리스트 출력 (db관련 기능 추가 필요)
        // -----------
        var kinderType = $("input[name=kinderType]:checked").val();
        alert(kinderType);
    })

    // 검색버튼
    $(".searchBtn").on('click',function(){
        var kinderType = $("input[name=kinderType]:checked").val();
        var kinderLoc = $("select[name=kinder-loc]").val();
        var kinderName = $("input[name=kinder-name]").val();

        alert(kinderType+"\n"+kinderLoc+"\n"+kinderName);
        // 검색 결과에 따른 리스트 출력 기능 추가 필요
        // ----------
    })

    // 카카오맵 로드후 실행
    kakao.maps.load(function () {
        //마커 좌표들
        var locations = [
            {lat: 37.5665, lng: 126.9780}
            ,{ lat: 37.5651, lng: 126.9895 }
            ,{ lat: 37.5700, lng: 126.9820 }
        ];

        // 이 코드는 script 태그 밑에 바로 두세요!
        var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
        var options = { //지도를 생성할 때 필요한 기본 옵션
            center: new kakao.maps.LatLng(37.5665, 126.9780), //지도의 중심좌표.
            level: 9 //지도의 레벨(확대, 축소 정도)
        };

        var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

        // --------------지도 확대 축소 기능-------------
        // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
        var zoomControl = new kakao.maps.ZoomControl();
        map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

        // // 지도가 확대 또는 축소되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
        // kakao.maps.event.addListener(map, 'zoom_changed', function() {
        //
        //     // 지도의 현재 레벨을 얻어옵니다
        //     var level = map.getLevel();
        //
        //     var message = '현재 지도 레벨은 ' + level + ' 입니다';
        //     var resultDiv = document.getElementById('result');
        //     resultDiv.innerHTML = message;
        //
        // });

        // 마커 찍고 해당 마커 클릭시 해당 정보 뜨는 기능
        const places = new kakao.maps.services.Places();

        // 열린 정보창을 추적할 변수
        let currentInfoWindow = null;

        locations.forEach(loc => {
            const markerPosition = new kakao.maps.LatLng(loc.lat, loc.lng);

            const marker = new kakao.maps.Marker({
                map: map,
                position: markerPosition
            });

            // 해당 좌표 근처에서 가장 가까운 장소를 검색
            places.keywordSearch('식당', function (result, status) {
                if (status === kakao.maps.services.Status.OK && result.length > 0) {
                    var place = result[0]; // 가장 가까운 장소

                    const content = `
                            <div class="info-window">
                                <strong>${place.place_name}</strong><br>
                                <div class="address">📍 ${place.road_address_name || place.address_name}</div>
                                <div class="phone">☎ ${place.phone}</div>
                                <a href="${place.place_url}" target="_blank">카카오맵에서 보기</a>
                            </div>
                            `;

                    const infowindow = new kakao.maps.InfoWindow({
                        content: content,
                        removable: true
                    });


                    // 마커 클릭 시, 이전에 열린 정보창을 닫고 새로운 정보창을 엽니다
                    kakao.maps.event.addListener(marker, 'click', function () {
                        // 이전 정보창이 열려 있다면 닫기
                        if (currentInfoWindow) {
                            currentInfoWindow.close();
                        }
                        // 새로운 정보창 열기
                        infowindow.open(map, marker);
                        // 열린 정보창 추적
                        currentInfoWindow = infowindow;
                    });
                }
            }, {
                location: markerPosition,
                radius: 100,      // 반경 100m 내 검색
                sort: 'distance'  // 거리순
            });
        });
    });

    // 유치원 리스트 체크박스 기능
    $(".kinderResult").on('click',function(){

        if ($(event.target).is("input[type=checkbox]")){
            var checkbox = $(this);
            var isChecked = checkbox.prop("checked");
            checkbox.prop("checked",!isChecked);
            if(!isChecked){
                $(this).addClass('select');
            }else{
                $(this).removeClass('select');
            }

            var checkValues = $("input[type=checkbox]:checked").map(function(){
                return $(this).val();
            }).get();
            alert(checkValues);
            return;
        }

        var checkbox = $(this).find("input[type=checkbox]");
        var isChecked = checkbox.prop("checked");
        checkbox.prop("checked",!isChecked);
        if(!isChecked){
            $(this).addClass('select');
        }else{
            $(this).removeClass('select');
        }



        // .map()은 jQuery 객체의 각 요소에 대해 함수를 실행,
        // .get()을 붙이면 jQuery 객체 → 일반 배열로 변환
        var checkValues = $("input[type=checkbox]:checked").map(function(){
            return $(this).val();
        }).get();
        alert(checkValues);
    })

    // 페이지네이션 부분
    $(".pagination>li>a").on('click',function(){
        event.preventDefault();
        if (!$(this).hasClass("prev-page") && !$(this).hasClass("next-page")){
            $(this).parent().siblings().children().removeClass("select");
            $(this).addClass("select");
        }

        // 페이지 가져오기 기능 추가필요
        //----------------------
    });
    // 페이지네이션 - 화살표 부분

    $("a[class=prev-page]").on('click',function(){

    })
    $("a[class=next-page]").on('click',function(){

    })



})