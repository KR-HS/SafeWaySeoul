$(document).ready(function() {
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

});

