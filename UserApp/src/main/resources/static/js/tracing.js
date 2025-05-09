var recordKey;

$(document).ready(function(){

    recordKey = $(".timeline-section").data("recordkey");

    $(".backBtn").on('click',function(){
        history.back();
    })
    // $(".mylocation").on('click',function(){
    //         if (navigator.geolocation) {
    //             navigator.geolocation.getCurrentPosition(function(position) {
    //                 var lat = position.coords.latitude;
    //                 var lng = position.coords.longitude;
    //                 var locPosition = new kakao.maps.LatLng(lat, lng);
    //
    //                 console.log(lat);
    //                 console.log(lng);
    //
    //                 // 지도 중심 이동
    //                 map.setCenter(locPosition);
    //
    //                 // 마커 표시(선택)
    //                 new kakao.maps.Marker({
    //                     map: map,
    //                     position: locPosition
    //                 });
    //             }, function(error) {
    //                 alert('위치 정보를 가져올 수 없습니다.');
    //             }, );
    //         } else {
    //             alert('이 브라우저에서는 Geolocation을 지원하지 않습니다.');
    //         }
    //     }
    // )
    $(".phonecall").on('click',function(){
        alert('통화하기버튼 작동됨');
    })


    //지도 관련 (자녀 기반 경로깔아주는거)
    let currentPolyline = null; // 현재 지도에 그려진 경로선(Polyline) 객체

    // 지도 컨테이너와 옵션 설정
    const mapContainer = document.getElementById('map'); // 지도를 표시할 div
    const mapOption = {
        center: new kakao.maps.LatLng(37.5665, 126.9780), // 지도의 중심좌표(서울 시청)
        level: 5 // 지도 확대/축소 레벨 (숫자가 작을수록 확대)
    };
    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도 객체 생성

    // 마커와 오버레이를 저장할 배열
    let markers = []; // 지도에 표시된 모든 마커 저장
    let overlays = []; // 경유지 번호 오버레이 저장

    // 마커 생성 함수 (출발/도착/경유지 구분)
    function addMarker(lat, lng, type) {
        let imageSrc, imageSize, markerImage;
        if (type === 'start') { // 출발지 마커
            imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
        } else {
            imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png';
        }
        imageSize = new kakao.maps.Size(24, 35);
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        const marker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng(lat, lng),
            image: markerImage,
            map: map
        });
        markers.push(marker); // 배열에 추가
    }



    // 경유지 번호 오버레이 생성 함수
    function addWaypointOverlay(lat, lng, number) {
        const overlayContent = `
                        <div style="
                            background: #fff;
                            border: 2px solid #777777;
                            border-radius: 50%;
                            width: 28px;
                            height: 28px;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            font-weight: bold;
                            font-size: 15px;
                            color: #777777;  
                            box-shadow: 0 1px 2px rgba(0,0,0,0.15);
                        ">
                            ${number}
                        </div>
                    `;
        const overlay = new kakao.maps.CustomOverlay({
            position: new kakao.maps.LatLng(lat, lng),
            content: overlayContent,
            yAnchor: 1.3
        });
        overlay.setMap(map);
        overlays.push(overlay);
    }

    // 출발지, 도착지, 경유지 주소 -> 좌표변환 함수
    function addressToCoords(address) {
        return new Promise((resolve, reject) => {
            const geocoder = new kakao.maps.services.Geocoder();
            geocoder.addressSearch(address, function(result, status) {
                if (status === kakao.maps.services.Status.OK && result.length > 0) {
                    resolve({ x: parseFloat(result[0].x), y: parseFloat(result[0].y) });
                } else {
                    // 주소 변환 실패 시 키워드(장소명) 검색 시도
                    const places = new kakao.maps.services.Places();
                    places.keywordSearch(address, function(result, status) {
                        if (status === kakao.maps.services.Status.OK && result.length > 0) {
                            resolve({ x: parseFloat(result[0].x), y: parseFloat(result[0].y) });
                        } else {
                            reject('주소 변환 실패: ' + address);
                        }
                    });
                }
            });
        });
    }

    // 경유지 포함 경로 그리기 함수
    async function drawRouteWithWaypoints() {
        const startAddr = document.getElementById('startAddress').value;
        const endAddr = document.getElementById('endAddress').value;
        const waypoints = Array.from(document.querySelectorAll('.waypoint-input'))
            .map(input => input.value.trim())
            .filter(v => v);

        console.log("startAddr:"+startAddr);
        console.log("endAddr:"+endAddr);

        if (!startAddr || !endAddr) {
            console.warn('주소 정보가 없습니다.');
            return;
        }

        try {
            // 주소 → 좌표 변환
            const start = await addressToCoords(startAddr);
            const end = await addressToCoords(endAddr);
            const waypointCoords = await Promise.all(
                waypoints.map(addr => addressToCoords(addr))
            );

            // 백엔드 API 호출
            const response = await fetch('/api/map/route', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    startX: start.x,
                    startY: start.y,
                    endX: end.x,
                    endY: end.y,
                    waypoints: waypointCoords.map(c => ({ x: c.x, y: c.y }))
                })
            });
            const coordinates = await response.json();

            const path = [];
            for (let i = 0; i < coordinates.length; i += 2) {
                path.push(new kakao.maps.LatLng(coordinates[i + 1], coordinates[i]));
            }
            currentPolyline = new kakao.maps.Polyline({
                map: map,
                path: path,
                strokeWeight: 5,
                strokeColor: '#FF00FF',
                strokeOpacity: 0.7
            });
            const bounds = new kakao.maps.LatLngBounds();
            path.forEach(coord => bounds.extend(coord));
            map.setBounds(bounds, 10, 10, 10, 10);

            // 출발, 경유지, 도착 마커 및 번호 오버레이 표시
            if (path.length > 0) {

                addMarker(path[0].getLat(), path[0].getLng(), 'start'); // 출발지

                waypointCoords.forEach((wp, idx) => {

                    addMarker(wp.y, wp.x, 'way'); // 경유지 마커
                    addWaypointOverlay(wp.y, wp.x, idx + 1); // 경유지 번호 오버레이
                });

                // 도착지 마커+오버레이 (경유지가 있을 때만)
                if (waypointCoords.length > 0) {
                    // 도착지 마커
                    addMarker(end.y, end.x, 'way');
                    // 마지막 번호 (경유지 개수 + 1)
                    addWaypointOverlay(end.y, end.x, waypointCoords.length + 1);
                } else {
                    // 경유지 없으면 도착지 마커만
                    addMarker(path[path.length - 1].getLat(), path[path.length - 1].getLng(), 'end');
                }
            }
            //화면 최상단으로 이동
            document.querySelector('.app-content')
                .scrollTo({top:0, left:0, behavior:'smooth'});

        } catch (error) {
            console.error('경로 생성 오류:', error);
            alert('경로 생성에 실패했습니다.');
        }
    }



    drawRouteWithWaypoints();




    // 실시간 경로 찍어주는 맵
    map = new kakao.maps.Map(document.getElementById('map'), {
        // center: new kakao.maps.LatLng(37.5665, 126.9780),
        center: new kakao.maps.LatLng(37.49953928976487, 127.03043069202396),
        level: 3
    });
    let linePath = [];
    let polyline;

    // db에 저장되어있는 위도,경도 받아와서 locations(실시간위치저장되어있는 맵) 저장하는 함수
    fetch(`/api/locations/${recordKey}`)
        .then(res => res.json())
        .then(data => {
            linePath = data.map(p => new kakao.maps.LatLng(parseFloat(p.latitude), parseFloat(p.longitude)));
            polyline = new kakao.maps.Polyline({
                path: linePath,
                strokeWeight: 5,
                strokeColor: '#FF0000',
                strokeOpacity: 0.8,
                strokeStyle: 'solid'
            });
            polyline.setMap(map);
        });

    // websocket에서 실시간으로 좌표 받아서 추가해주는 곳
    const socket = new SockJS("http://localhost:8082/ws");
    // const socket = new SockJS("https://1166-218-153-162-9.ngrok-free.app/ws");
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
        console.log("WebSocket connected");
        stompClient.subscribe(`/topic/location/update/${recordKey}`, message => {

            const data = JSON.parse(message.body);
            const latlng = new kakao.maps.LatLng(parseFloat(data.latitude), parseFloat(data.longitude));
            console.log("WebSocket message received:", data);

            map.setCenter(latlng);

            // 경로 추가
            linePath.push(latlng);
            polyline.setPath(linePath);
        });
    });


})