    var page = 1;
    var size = 10;
    var start = 0; //시작페이지
    var end = 0; //끝페이지
$(document).ready(function(){

    // 자녀 관련 모달창 기능
    $(".viewInfo").on('click',function(){
        window.location.href="/tracing";
        // $(".infoModal").css("display","block");
    });


    // ---------------어린이집 리스트

    // 어린이집 유형 버튼 클릭
    $('.label-wrap').on('click', function(event) {
        event.preventDefault();

        $(this).siblings().removeClass('select');
        $(this).addClass('select');
        $(this).find("input[type=radio]").prop('checked', true);

        $(".searchBtn").click();

    });
    
    // 검색버튼
    $(".searchBtn").on('click',function(){
        page = 1;
        getList();
    })
    
    // 유치원 리스트
    function getList(){
        var kinderType = $("input[name=kinderType]:checked").val();
        var kinderLoc = $("select[name=kinder-loc]").val();
        var kinderName = $("input[name=kinder-name]").val();

        if(kinderType=='A'){
            if(kinderLoc=='전체'){
                if(kinderName=="" || kinderName==null){
                    fetch('/kinder/find?page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }else{
                    fetch('/kinder/find?name='+kinderName+'&page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }
            }else{
                if(kinderName=="" || kinderName==null){
                    fetch('/kinder/find?address='+kinderLoc+'&page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }else{
                    fetch('/kinder/find?address='+kinderLoc+'&name='+kinderName+'&page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }
            }
        }
        else if(kinderType=='N'){
            if(kinderLoc=='전체'){
                if(kinderName=="" || kinderName==null){
                    fetch('/kinder/find?night=Y&page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }else{
                    fetch('/kinder/find?night=Y&name='+kinderName+'&page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }
            }else{
                if(kinderName=="" || kinderName==null){
                    fetch('/kinder/find?night=Y&address='+kinderLoc+'&page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }else{
                    fetch('/kinder/find?night=Y&address='+kinderLoc+'&name='+kinderName+'&page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }
            }


        }
        else if(kinderType=='W'){
            if(kinderLoc=='전체'){
                if(kinderName=="" || kinderName==null){
                    fetch('/kinder/find?week=Y&page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }else{
                    fetch('/kinder/find?week=Y&name='+kinderName+'&page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }
            }else{
                if(kinderName=="" || kinderName==null){
                    fetch('/kinder/find?week=Y&address='+kinderLoc+'&page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }else{
                    fetch('/kinder/find?week=Y&address='+kinderLoc+'&name='+kinderName+'&page='+page+'&size='+size, {
                        method: 'get'
                    })
                        .then(response => response.json())  // 응답을 JSON으로 처리
                        .then(data => {
                            createKinder(data.pageData);
                            createPage(data);
                            console.log('Success:', data);  // 서버로부터 받은 응답 출력
                        })
                        .catch((error) => {
                            console.error("유치원목록을 불러오는 과정에서 오류가 발생했습니다.");
                        });
                }
            }
        }
        // ----------
    }


    // 바디 생성 함수
    // item에는 List<kinderVO>가 옴
    function createKinder(item){
        var str = "";
        item.forEach(function(data){
            str+="<div class='kinderResult'>";
                str+="<input type='checkbox' name='kinderKey' value='1'>"
                str+="<div class='kinderInfo-wrap'>";
                    str+="<div class='img-wrap'>"
                    str+="<img src='/img/allKinderType.png' alt='유치원이미지'>"
                    str+="</div>"
                    str+="<div class='kinderInfo'>";
                        str+="<div class='infoUpper'>";
                        str+="<p class='kinderTitle'>"+data.kinderName+"</p>";
                        str+="</div>"
                        str+="<div class='infoUnder'>";
                        str+="<p class='kinderLoc'>"+data.kinderAddress+"</p>";
                        str+="<p class='kinderCall'>"+data.kinderPhone+"</p>";
                        str+="</div>";
                    str+="</div>"
                str+="</div>";
            str+="</div>";
        })
        $(".kinderResult-wrap").html(str);
    }

    // 페이지네이션 생성함수
    function createPage(item){
        console.log(item);
        var pageList = item.pageList; // 페이지목록
        var next = item.next; // 다음
        var prev = item.prev; // 이전
        start = item.start; // 시작페이지
        end = item.end;


        str="";

        if(prev){
            str+="<li><a href='#' class='prev'>&lsaquo;</a></li>";
        }

        pageList.forEach(function(data){
            var activeClass= (data == page) ? "select" : "";
            str+="<li><a href='#' class='number "+activeClass+"'>"+data+"</a></li>";
        })

        if(next){
            str+="<li><a href='#' class='next'>&rsaquo;</a></li>";
        }

        $(".pagination").html(str);

    }

    // 페이지네이션 클릭 함수
    $(".pagination").on("click",function(){
        event.preventDefault();
        if(event.target.className=='pagination') return; // 버튼일때만 동작

        if(event.target.className=="prev"){
            if (start > 1) {
                page = start - 1;
                getList();
            }
        }
        else if(event.target.className=="next"){
            page=end + 1;
        }
        else if($(event.target).hasClass("number")){
            page=event.target.innerHTML;
            $(".pagination .number").removeClass("select");
            $(event.target).addClass("select");
        }
        getList(); // 데이터 가져오기 기능 호출
    });

    (function(){
        getList();
    })();




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