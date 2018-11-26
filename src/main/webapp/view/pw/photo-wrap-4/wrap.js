(function () {
    //初始化
    var first = true;
    var length = $('.bg').length;
    var curr = 0;
    $('.bg').each(function (index) {
        var img = $(this).children('img').attr('src');
        $(this).css('background', 'url(' + img + ') no-repeat center/cover');
        $('#wrap li:eq(' + index + ')').css('background', 'url(' + img + ') no-repeat center/cover');
        if (first) {
            $('#wrap li:eq(' + index + ')').css('width', '700px');
            $('#wrap li:eq(' + index + ')').addClass('curr');
            first = false;
        }
    })

    //添加点击事件
    $('#wrap li').click(function () {
        force($(this));
    });

    //添加轮播定时器
    setInterval(function () {
        force($('#wrap li:eq(' + curr + ')'));
        curr ++;
        if(curr == length) {
            curr = 0;
        }
    }, delay);

    function force(element) {
        if (!element.hasClass('curr')) {
            $('#wrap li').removeClass('curr');
            element.addClass('curr');

            // 切换背景
            $('#wrap li').each(function (index) {
                if (element.hasClass('curr')) {
                    $('.bg').fadeOut(300);
                    $('.bg:eq(' + index + ')').fadeIn(500);
                }
            });

            $('.curr').stop().animate({
                width: 700
            }, 500, 'linear');
            $('#wrap li').not('.curr').stop().animate({
                width: 100
            }, 500, 'linear');
        }
    }
})()