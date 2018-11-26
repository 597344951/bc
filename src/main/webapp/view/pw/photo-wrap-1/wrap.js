class Wrap {
    constructor() {
        this.documentHeight = $(document).height();
        this.documentWidth = $(document).width();
    }

    load(photo, callback) {
        let $wrap = this;
        let documentHeight = this.documentHeight, documentWidth = this.documentWidth;
        $('<img />').on('load', function () {
            let imageWidth = this.width, imageHeight = this.height;
            let rows = $wrap.getCloseDivisibleNumber(imageHeight, 100), cols = $wrap.getCloseDivisibleNumber(imageWidth, 100);
            $wrap.rows = rows;
            $wrap.cols = cols;
            //初始化容器
            let wrap = $('.wrap');
            wrap.html('');
            wrap.height(imageHeight).width(imageWidth);
            //加入图片
            let cellHeight = imageHeight / rows, cellWidth = imageWidth / cols;
            let bgLeft = 0, bgTop = 0;
            for (let r = 0; r < rows; r++) {
                for (let c = 0; c < cols; c++) {
                    $('<li><div class="image"></div></li>').width(cellWidth).height(cellHeight)
                        .css({
                            'opacity': 0,
                            'left': cellWidth * c + 'px',
                            'top': cellHeight * r + 'px',
                            'transform': 'scale(0.8) rotate(' + (Math.random() * 180 - 90) + 'deg) ' +
                                'translateX(' + (60 * c - 60) + 'px)' +
                                'translateY(' + (60 * r - 60) + 'px)'
                        })
                        .find('.image').css({
                            'background-image': 'url(' + photo + ')',
                            'transform': 'scale(0.8)',
                            'background-size': 'auto',
                            'backgroundPositionX': -bgLeft,
                            'backgroundPositionY': -bgTop,
                        })
                        .end()
                        .appendTo(wrap);
                    bgLeft += cellWidth;
                    if (bgLeft >= imageWidth) {
                        bgLeft = 0;
                        bgTop += cellHeight;
                    }
                }
            }
            //调节显示比例
            if (imageHeight > documentHeight * 0.8 || imageWidth > documentWidth * 0.8) {
                let widthZoom = documentWidth * 0.8 / imageWidth, heightZoom = documentHeight * 0.8 / imageHeight;
                let zoom = widthZoom > heightZoom ? heightZoom : widthZoom;
                wrap.css('zoom', zoom);
            }

            //回调
            if (callback) callback();

        }).attr('src', photo);
    }

    show() {
        $('.wrap li').each(function (index) {
            let $this = $(this);
            $(this).delay(500 * Math.random()).animate({ "opacity": 0.5 }, 200, function () {
                $this.css({
                    'transform': ' rotate(0deg) ' +
                        'translateX(0)' +
                        'translateY(0)'
                });
                $this.find('.image').css({
                    'background-size': 'auto',
                    'transform': 'scale(1)'
                });
                $this.animate({ "opacity": 1 }, 300);
            })
        });
    }

    hide() {
        let collums = this.cols;
        $('.wrap li').each(function (index) {
            let c = index % collums, r = parseInt(index / collums)
            let $this = $(this);
            $(this).delay(300 * Math.random()).animate({ "opacity": 0.5 }, 300, function () {
                $this.css({
                    'transform': 'rotate(' + (Math.random() * 40 - 20) + 'deg)' +
                        'translateX(' + (60 * c - 60) + 'px)' +
                        'translateY(' + (60 * r - 60) + 'px)'
                });
                $this.find('.image').css({
                    'transform': 'scale(0.9)'
                });
                $this.animate({ "opacity": 0 }, 500);
            })
        });
    }

    getCloseDivisibleNumber(number, divisor) {
        while (number % divisor != 0) {
            divisor++;
        }
        return number / divisor;
    }
}