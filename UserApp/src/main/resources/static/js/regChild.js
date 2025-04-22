$(document).ready(function() {
    const myDatepicker = new AirDatepicker('.birth', {
        autoClose: true,
        dateFormat: 'yyyy-MM-dd',
        startView: 'years',
        view: 'days',
        maxDate: new Date(),
        locale: {
            days: ['일', '월', '화', '수', '목', '금', '토'],
            daysShort: ['일', '월', '화', '수', '목', '금', '토'],
            daysMin: ['일', '월', '화', '수', '목', '금', '토'],
            months: ['1월', '2월', '3월', '4월', '5월', '6월',
                '7월', '8월', '9월', '10월', '11월', '12월'],
            monthsShort: ['1월', '2월', '3월', '4월', '5월', '6월',
                '7월', '8월', '9월', '10월', '11월', '12월'],
            today: '오늘',
            clear: '지우기',
            dateFormat: 'yyyy-MM-dd',
            firstDay: 0
        }
    });

    // 날짜 입력란에 포커스가 가면 flatpickr 날짜 선택기 열기
    $(".birth").on("click", function() {
        myDatepicker.show();  // input에 focus 시 날짜 선택기가 열리도록
    });

    $('.girl').click(function() {
        if($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            if($(".boy").hasClass("selected")) {
                $(".boy").removeClass("selected");
            }
            $(this).addClass("selected");
        }
    });

    $(".boy").click(function() {
        if($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            if($(".girl").hasClass("selected")) {
                $(".girl").removeClass("selected");
            }
            $(this).addClass("selected");
        }
    });

    document.getElementById("child-photo").addEventListener("change", function (event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                document.querySelector(".photo-upload").style.backgroundImage = `url('${e.target.result}')`;
            };
            reader.readAsDataURL(file); // file을 읽어서 브라우저가 사용할 수 있는 Data URL (Base64) 형식으로 변환
        }
    });

    $(".close-btn").click(function(){
        window.history.back();
    });

    // 성별 라디오 버튼 유효성 검사 기능
    $(".submit-btn").on("click", function(e) {
        const genderRadios = $("input[name='childGender']");
        const isSelected = genderRadios.is(":checked");

        if (!isSelected) {
            e.preventDefault(); // 폼 제출 막기
            alert("성별을 선택해주세요."); // 사용자 피드백
            return false;
        }
    });
});

