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