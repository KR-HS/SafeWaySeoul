var page = 1;
var size = 10;
var start = 0; //시작페이지
var end = 0; //끝페이지
var locations = []; // 좌표 찍을 리스트
var markers = [];
var map = null;
var currentInfoWindow = null;
var childKey= null;

$(document).ready(function () {


    // 자녀 관련 모달창 기능
    $(".viewInfo").on('click', function () {

        childKey = $(this).data("childkey"); //이거 tracing화면 넘어갈떄, childkey넘기기 위한 작업
        recordKey = $(this).data("recordkey");
        window.location.href = "/tracing?childKey="+childKey+"&recordKey="+recordKey;
        console.log(childKey+"/"+recordKey);
        // $(".infoModal").css("display","block");
    });


    // ---------------어린이집 리스트

    // 어린이집 유형 버튼 클릭
    $('.label-wrap').on('click', function (event) {
        event.preventDefault();

        $(this).siblings().removeClass('select');
        $(this).addClass('select');
        $(this).find("input[type=radio]").prop('checked', true);

        $(".searchBtn").click();

    });

    // 검색버튼
    $(".searchBtn").on('click', function () {
        page = 1;
        getList();
    })

    // 유치원 리스트
    function getList() {
        var kinderType = $("input[name=kinderType]:checked").val();
        var kinderLoc = $("select[name=kinder-loc]").val();
        var kinderName = $("input[name=kinder-name]").val();

        if (kinderType == 'A') {
            if (kinderLoc == '전체') {
                if (kinderName == "" || kinderName == null) {
                    fetch('/kinder/find?page=' + page + '&size=' + size, {
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
                } else {
                    fetch('/kinder/find?name=' + kinderName + '&page=' + page + '&size=' + size, {
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
            } else {
                if (kinderName == "" || kinderName == null) {
                    fetch('/kinder/find?address=' + kinderLoc + '&page=' + page + '&size=' + size, {
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
                } else {
                    fetch('/kinder/find?address=' + kinderLoc + '&name=' + kinderName + '&page=' + page + '&size=' + size, {
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
        } else if (kinderType == 'N') {
            if (kinderLoc == '전체') {
                if (kinderName == "" || kinderName == null) {
                    fetch('/kinder/find?night=Y&page=' + page + '&size=' + size, {
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
                } else {
                    fetch('/kinder/find?night=Y&name=' + kinderName + '&page=' + page + '&size=' + size, {
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
            } else {
                if (kinderName == "" || kinderName == null) {
                    fetch('/kinder/find?night=Y&address=' + kinderLoc + '&page=' + page + '&size=' + size, {
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
                } else {
                    fetch('/kinder/find?night=Y&address=' + kinderLoc + '&name=' + kinderName + '&page=' + page + '&size=' + size, {
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


        } else if (kinderType == 'W') {
            if (kinderLoc == '전체') {
                if (kinderName == "" || kinderName == null) {
                    fetch('/kinder/find?week=Y&page=' + page + '&size=' + size, {
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
                } else {
                    fetch('/kinder/find?week=Y&name=' + kinderName + '&page=' + page + '&size=' + size, {
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
            } else {
                if (kinderName == "" || kinderName == null) {
                    fetch('/kinder/find?week=Y&address=' + kinderLoc + '&page=' + page + '&size=' + size, {
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
                } else {
                    fetch('/kinder/find?week=Y&address=' + kinderLoc + '&name=' + kinderName + '&page=' + page + '&size=' + size, {
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
    function createKinder(item) {
        var str = "";
        locations = [];
        item.forEach(function (data) {

            // // 좌표 추가
            // locations.push({
            //     lat: data.location.latitude,
            //     lng: data.location.longitude}
            // );

            str += "<div class='kinderResult' data-lat='" + data.location.latitude + "' data-lng='" + data.location.longitude + "'>";
            str += "<input type='checkbox' name='kinderKey' value='" + data.kinderKey + "'>";
            str += "<div class='kinderInfo-wrap'>";
            str += "<div class='img-wrap'>"
            str += "<img src='/img/allKinderType.png' alt='유치원이미지'>"
            str += "</div>"
            str += "<div class='kinderInfo'>";
            str += "<div class='infoUpper'>";
            str += "<p class='kinderTitle'>" + data.kinderName + "</p>";
            str += "</div>"
            str += "<div class='infoUnder'>";
            str += "<p class='kinderLoc'>" + data.kinderAddress + "</p>";
            str += "<p class='kinderCall'>" + data.kinderPhone + "</p>";
            str += "</div>";
            str += "</div>"
            str += "</div>";
            str += "</div>";
        })
        initMap();
        $(".kinderResult-wrap").html(str);
    }

    // 페이지네이션 생성함수
    function createPage(item) {
        console.log(item);
        var pageList = item.pageList; // 페이지목록
        var next = item.next; // 다음
        var prev = item.prev; // 이전
        start = item.start; // 시작페이지
        end = item.end;


        str = "";

        if (prev) {
            str += "<li><a href='#' class='prev'>&lsaquo;</a></li>";
        }

        pageList.forEach(function (data) {
            var activeClass = (data == page) ? "select" : "";
            str += "<li><a href='#' class='number " + activeClass + "'>" + data + "</a></li>";
        })

        if (next) {
            str += "<li><a href='#' class='next'>&rsaquo;</a></li>";
        }

        $(".pagination").html(str);

    }

    // 페이지네이션 클릭 함수
    $(".pagination").on("click", function () {
        event.preventDefault();
        if (event.target.className == 'pagination') return; // 버튼일때만 동작

        if (event.target.className == "prev") {
            if (start > 1) {
                page = start - 1;
                getList();
            }
        } else if (event.target.className == "next") {
            page = end + 1;
        } else if ($(event.target).hasClass("number")) {
            page = event.target.innerHTML;
            $(".pagination .number").removeClass("select");
            $(event.target).addClass("select");
        }
        getList(); // 데이터 가져오기 기능 호출
    });

    (function () {
        initMap();
        getList();
    })();


    function initMap() {
        const container = document.getElementById('map');
        const center = new kakao.maps.LatLng(37.5665, 126.9780); // 서울 중심

        const options = {
            center: center,
            level: 10
        };

        map = new kakao.maps.Map(container, options);

        // 줌 컨트롤 추가
        const zoomControl = new kakao.maps.ZoomControl();
        map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
    }


    function updateMarkers(locations) {
        // 기존 마커 제거
        markers.forEach(marker => {
            marker.setMap(null);
            if (marker.infoWindow) {
                marker.infoWindow.close(); // 💡 infoWindow도 함께 제거
            }
        });
        markers = [];

        var places = new kakao.maps.services.Places();


        locations.forEach(loc => {
            var markerPosition = new kakao.maps.LatLng(loc.lat, loc.lng);
            var marker = new kakao.maps.Marker({
                map: map,
                position: markerPosition
            });

            markers.push(marker);

            // 마커 클릭시 뜨는 정보창 내용
            var content = `
                    <div class="info-window">
                        <strong>${loc.name}</strong><br>
                         <div class="address">${loc.address}</div>
                         <div class="phoneIcon"><span class="material-symbols-outlined" style="padding-top: 3px; font-size: 12px; color: #666;">call</span> <div class="phone">${loc.phone}</div></div>`;

            var infowindow = new kakao.maps.InfoWindow({
                content: content,
                removable: true
            });

            marker.infoWindow = infowindow;
            kakao.maps.event.addListener(marker, 'click', function () {
                // 이미 열린 창을 다시 클릭했을 때 닫기
                if (currentInfoWindow && currentInfoWindow === infowindow) {
                    currentInfoWindow.close();
                    currentInfoWindow = null;
                    return;
                }

                if (currentInfoWindow) currentInfoWindow.close();
                infowindow.open(map, marker);
                currentInfoWindow = infowindow;
                $(".info-window").parent().parent().css("border","none");
            });
        });


        // 첫 번째 위치로 중심 이동
        if (locations.length > 0) {
            map.setCenter(new kakao.maps.LatLng(locations[0].lat, locations[0].lng));
        }
    }


    $(".kinderLoc").on("change",function(){
        $(".searchBtn").click();
    })

    // 유치원 리스트 체크박스 기능
    $(document).on('click', ".kinderResult", function () {

        var checkbox = $(this).find("input[type=checkbox]");
        var isChecked = checkbox.prop("checked");

        // 체크박스를 클릭한 경우: 토글하지 않고 상태에 따라 처리만 함
        if ($(event.target).is("input[type=checkbox]")) {
            isChecked = checkbox.prop("checked");
        } else {
            checkbox.prop("checked", !isChecked);
            isChecked = !isChecked;
        }

        if (isChecked) {
            $(this).addClass('select');
        } else {
            $(this).removeClass('select');
        }

        // 좌표 정보 처리
        var lat = parseFloat($(this).data("lat"));
        var lng = parseFloat($(this).data("lng"));
        var kinderName = $(this).find(".kinderTitle").text();
        var kinderAddress = $(this).find(".kinderLoc").text();
        var kinderPhone = $(this).find(".kinderCall").text();
        if (lat && lng) {
            if (isChecked) {
                // 중복 좌표 확인 후 추가
                if (!locations.some(loc => loc.lat === lat && loc.lng === lng)) {
                    locations.push({
                        lat: lat,
                        lng: lng,
                        name: kinderName,
                        address: kinderAddress,
                        phone: kinderPhone
                    });
                }
            } else {
                // 체크 해제 시 정확히 일치하는 항목 제거
                locations = locations.filter(loc => !(loc.lat === lat && loc.lng === lng));
            }

            updateMarkers(locations);  // 현재 체크된 것만 지도에 표시
        } else {
            alert("해당 유치원의 좌표 정보가 없습니다.");
        }
    })


})