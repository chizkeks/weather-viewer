// Кастомная функция анимации transform: translateX
function animatePanel(targetPercent) {
    $('.topLayer').stop().animate(
        { x: targetPercent },          // виртуальное свойство для анимации
        {
            duration: 700,             // плавнее и медленнее
            easing: 'easeInOutCubic',  // красивая плавная кривая
            step: function (value) {
                $(this).css('transform', 'translateX(' + value + '%)');
            }
        }
    );
}

$(document).ready(function(){
    /*if ($('body').hasClass('login') || $('body').hasClass('registration')) {
        return;
    }*/
    $('#goRight').on('click', function(){
        $('#slideBox').animate({
            'marginLeft' : '0'
        });
        $('.topLayer').animate({
            'marginLeft' : '100%'
        });
        $("body").removeClass("login").addClass("registration");
    });
    $('#goLeft').on('click', function(){
        $('#slideBox').animate({
            'marginLeft' : '50%'
        });
        $('.topLayer').animate({
            'marginLeft': '0'
        });
        $("body").removeClass("registration").addClass("login");
    });
});


$(document).on('click', '.password-control', function(){
    if ($('#password-input').attr('type') == 'password'){
        $(this).addClass('view');
        $('#password-input').attr('type', 'text');
    } else {
        $(this).removeClass('view');
        $('#password-input').attr('type', 'password');
    }
    return false;
});