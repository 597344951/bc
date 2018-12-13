let idx = -1
let mouseup = false
let photoW = 300, photoH = 300
let $container, photosSize, containerW, containerH, horizontalMax, verticalMax


const decorate = (render) => {
    $container = $(render)
    photosSize = $container.find('.pw_photo_box').length
    containerW = $container.width()
    containerH = $container.height()
    horizontalMax = containerW - photoW
    verticalMax = containerH - photoH

    let curPhoto = 0
    $container.find('.pw_photo_box').each(function (i) {
        let $photo = $(this);
        $('<img />').load(function () {
            ++curPhoto;
            let r = Math.floor(Math.random() * 201) - 100;
            let maxzidx = parseInt(findHighestZIndex()) + 1;
            let position = {
                'top': Math.floor(Math.random() * verticalMax) + 'px',
                'left': Math.floor(Math.random() * horizontalMax) + 'px',
                'z-index': maxzidx
            };

            $photo.css(position);
            $photo.transform({'rotate'	: r + 'deg'});
            $photo.show();
            if (curPhoto == photosSize) {
                bindEvents(render);
            }
        }).attr('src', $photo.find('img').attr('src'));
    })
}

const bindEvents = (render) => {
    $container.find('.pw_photo_box').each(function (i) {
        let $photo = $(this);
        $photo.draggable({
            containment: render
        })
    }).find('.pw_photo').unbind('mousedown').bind('mousedown', function (e) {
        let $photo = $(this).parent();
        photoMouseDown($photo);
        e.preventDefault();
    });
    $container.bind('mouseup', function (e) {
        if (mouseup) {
            mouseup = false;
            let $photo = $container.find('.pw_photo_box:nth-child(' + idx + ')');
            let r = Math.floor(Math.random() * 101) - 50;
            let $photoT = parseFloat($photo.css('top'), 10);
            let $photoL = parseFloat($photo.css('left'), 10);
            let newTop = $photoT + r;
            let newLeft = $photoL + r;
            let param = {
                'width': '-=40px',
                'height': '-=40px',
                'top': newTop + 'px',
                'left': newLeft + 'px',
                'rotate': r + 'deg',
                'shadow': '0 0 5px #000'
            };
            $photo.stop(true, true).animate(param, 200).find('img').stop(true, true).animate({
                'width': '-=40px',
                'height': '-=40px'
            }, 200);
        }
        e.preventDefault();
    });
}

const photoMouseDown = ($photo) => {
    mouseup = true;
    idx = $photo.index() + 1;
    let maxzidx = parseInt(findHighestZIndex()) + 1;
    $photo.css('z-index', maxzidx);
    let param = {
        'width': '+=40px',
        'height': '+=40px',
        'rotate': '0deg',
        'shadow': '5px 5px 15px #222'
    };
    $photo.stop(true, true).animate(param, 100).find('img').stop(true, true).animate({
        'width': '+=40px',
        'height': '+=40px'
    }, 100);
}

const findHighestZIndex = () => {
    let photos = $container.find('.pw_photo_box');
    let highest = 0;
    photos.each(function () {
        let $photo = $(this);
        let zindex = $photo.css('z-index');
        if (parseInt(zindex) > highest) {
            highest = zindex;
        }
    });
    return highest;
}


export default { decorate }