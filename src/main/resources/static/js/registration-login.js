$(document).ready(function(){
    $('#goRight').on('click', function(){
        $('#slideBox').animate({
            'marginLeft' : '0'
        });
        $('.topLayer').animate({
            'marginLeft' : '100%'
        });
    });
    $('#goLeft').on('click', function(){
        $('#slideBox').animate({
            'marginLeft' : '50%'
        });
        $('.topLayer').animate({
            'marginLeft': '0'
        });
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