$(document).ready(function() {

    $(".post-write").on("click",function () {
        console.log("click");
        window.location.href="/community/postWrite";
    });

    $(".post-card").on("click",function () {
        var postKey = $(this).data("post-key");

        window.location.href="/community/postDetail?postKey=" + postKey;
    });

    $(".my-page").on("click", function() {
        var userKey = $(".search-icon").data("user-key");
        window.location.href="/community/myPostList?userKey=" + userKey;
    })

    let searchClicked = false;

    $(".search-icon").on("click", function () {
        const $searchInput = $(".search");
        var userKey = $(this).data("user-key");
        if (!searchClicked) {
            $searchInput.css("display", "inline-block").focus();
            searchClicked = true;
        } else {
            const keyword = $searchInput.val().trim();
            if (keyword.length > 0) {
                window.location.href = "/community/myPostList?search=" + encodeURIComponent(keyword) + "&&userKey=" + encodeURIComponent(userKey);
                console.log(keyword);
            } else {
                alert("검색어를 입력해주세요.");
            }
        }
    });

    $(".search").on("keydown", function(e) {
        if (e.keyCode === 13) {
            const keyword = $(this).val().trim();
            var userKey = $(".search-icon").data("user-key");
            if (keyword.length > 0) {
                window.location.href = "/community/myPostList?search=" + encodeURIComponent(keyword) + "&&userKey=" + encodeURIComponent(userKey);
            } else {
                alert("검색어를 입력해주세요.");
            }
        }
    });
});