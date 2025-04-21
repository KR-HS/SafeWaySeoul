$(function () {
    // 자녀 정보가 아예 없는 경우
    if ($('.child-list .child-card').length === 0) {
        $('.child-list').hide();
        $('.child-register-box').show();
    }

    // 성별 아이콘 전환
    $('.child-card').each(function () {
        var $card = $(this);
        var profileSrc = $card.find('.child-image img').attr('src');
        var isFemale = profileSrc && profileSrc.includes('female');
        if (!isFemale) {
            $card.find('.child-name img').attr('src', '/img/man.png');
        }
    });
});