$(document).ready(function(){



    $(".backBtn").on('click',function(){
        history.back();
    })


    $(".mylocation").on('click',function(){
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var lat = position.coords.latitude;
                var lng = position.coords.longitude;
                var locPosition = new kakao.maps.LatLng(lat, lng);

                console.log(lat);
                console.log(lng);

                // 지도 중심 이동
                map.setCenter(locPosition);

                // 마커 표시(선택)
                new kakao.maps.Marker({
                    map: map,
                    position: locPosition
                });
            }, function(error) {
                alert('위치 정보를 가져올 수 없습니다.');
            }, );
        } else {
            alert('이 브라우저에서는 Geolocation을 지원하지 않습니다.');
        }
    })

    $(".phonecall").on('click',function(){
        alert('통화하기버튼 작동됨');
    })


})