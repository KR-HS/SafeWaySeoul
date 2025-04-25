var page = 1;
var size = 10;

$(document).ready(function () {
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

    $(".birth").on("click", function () {
        myDatepicker.show();
    });

    $('.girl').click(function () {
        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            if ($(".boy").hasClass("selected")) {
                $(".boy").removeClass("selected");
            }
            $(this).addClass("selected");
        }
    });

    $(".boy").click(function () {
        if ($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            if ($(".girl").hasClass("selected")) {
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
            reader.readAsDataURL(file);
        }
    });

    $(".close-btn").click(function () {
        window.history.back();
    });

    $(".submit-btn").on("click", function (e) {
        const genderRadios = $("input[name='childGender']");
        const isSelected = genderRadios.is(":checked");

        if (!isSelected) {
            e.preventDefault();
            $('#delete-confirm-modal').hide();
            $('#gender-alert-modal').fadeIn().css("display", "flex");
            return false;
        }

        // KM_PICKUP 체크박스 값을 hidden 필드로 변환 (Y/N)
        let pickupValue = $("input[name='kmPickup']").is(":checked") ? "Y" : "N";

        // 이미 있으면 값만 수정, 없으면 새로 생성
        if ($("input[name='pickupHidden']").length > 0) {
            $("input[name='pickupHidden']").val(pickupValue);
        } else {
            $("<input>").attr({
                type: "hidden",
                name: "pickupHidden",
                value: pickupValue
            }).appendTo("form[name='childForm']");
        }
    });

    $("#gender-alert-confirm-btn").on("click", function () {
        $("#gender-alert-modal").fadeOut();
    });

    $("#gender-alert-modal").on("click", function (e) {
        if (e.target.id === "gender-alert-modal") {
            $("#gender-alert-modal").fadeOut();
        }
    });

    $("input[name='regChild-kinder-name']").on("input click", function () {
        const keyword = $(this).val();
        if (keyword.length < 1) {
            $("#autocomplete-list").hide();
            return;
        }

        fetch(`/kinder/find?name=${encodeURIComponent(keyword)}&page=1&size=20`, { method: 'get' })
            .then(res => res.json())
            .then(data => {
                const list = data.pageData || [];
                let html = "";

                if (list.length > 0) {
                    list.forEach(kinder => {
                        html += `<div class="autocomplete-item" data-name="${kinder.kinderName}" data-key="${kinder.kinderKey}">${kinder.kinderName}<br>${kinder.kinderAddress}</div>`;
                    });
                } else {
                    html = `<div class="autocomplete-item" style="color:#999;">검색 결과 없음</div>`;
                }

                $("#autocomplete-list").html(html).show();
            })
            .catch(err => {
                console.error("자동완성 요청 실패", err);
                $("#autocomplete-list").hide();
            });
    });

    $(document).on("click", ".autocomplete-item", function () {
        const selectedName = $(this).data("name");
        const selectedKey = $(this).data("key");
        $("input[name='regChild-kinder-name']").val(selectedName);
        $("input[name='regChild-kinder-key']").val(selectedKey);
        $("#autocomplete-list").hide();
    });

    $(document).on("click", function (e) {
        if (!$(e.target).closest(".regChild-kinder-search-wrap").length) {
            $("#autocomplete-list").hide();
        }
    });
});