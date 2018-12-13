const wall = (function () {
    let $pw_thumbs, $pw_ribbon, $pw_ribbon_close, $pw_ribbon_zoom;

    // window width and height
    let w_dim,
        // index of current image
        current = -1,
        isRibbonShown = false,
        isFullMode = false,
        // ribbon / images animation settings
        ribbonAnim = { speed: 500, easing: 'easeOutExpo' },
        imgAnim = { speed: 400, easing: 'jswing' },
        // init function : call masonry, calculate window dimentions, initialize some events
        decorate = function (render) {
            let pw_container_hide = window.document.querySelector(render)
            let showId = pw_container_hide.id + "Show"
            let old = window.document.getElementById(showId)
            if(old) {
                old.parentNode.removeChild(old)
            }
            let pw_container = pw_container_hide.cloneNode(true)
            pw_container.id = showId
            pw_container.classList.remove('hide')
            pw_container_hide.parentNode.insertBefore(pw_container, pw_container_hide.nextSibling)

            let $pw_container = $(pw_container)
            $pw_thumbs = $pw_container.find('.pw_thumbs');
            $pw_ribbon = $pw_container.find('.pw_ribbon');
            $pw_ribbon_close = $pw_ribbon.children('span.pw_close');
            $pw_ribbon_zoom = $pw_ribbon.children('span.pw_zoom');
            
            $pw_thumbs.imagesLoaded(function () {
                $pw_thumbs.masonry({
                    isAnimated: true
                });
            });
            setWindowsDim();
            initEventsHandler();
        },
        // calculate window dimentions
        setWindowsDim = function () {
            w_dim = {
                width: $(window).width(),
                height: $(window).height()
            };
        },
        // initialize some events
        initEventsHandler = function () {
            // click on a image
            $pw_thumbs.delegate('li', 'click', function () {
                if ($pw_ribbon.is(':animated')) return false;
                let $el = $(this);
                if ($el.data('ribbon')) {
                    showFullImage($el);
                }
                else if (!isRibbonShown) {
                    isRibbonShown = true;
                    $el.data('ribbon', true);
                    // set the current
                    current = $el.index();
                    showRibbon($el);
                    setTimeout(() => {
                        showFullImage($el);
                    }, 500)
                }
            });
            // click ribbon close
            $pw_ribbon_close.bind('click', closeRibbon);
            // on window resize we need to recalculate the window dimentions
            $(window).bind('resize', function () {
                setWindowsDim();
                if ($pw_ribbon.is(':animated'))
                    return false;
                closeRibbon();
            }).bind('scroll', function () {
                if ($pw_ribbon.is(':animated'))
                    return false;
                closeRibbon();
            });
        },
        showRibbon = function ($el) {
            let $img = $el.children('img'),
                $descrp = $img.next();
            // fadeOut all the other images
            $pw_thumbs.children('li').not($el).animate({ opacity: 0.2 }, imgAnim.speed);
            // increase the image z-index, and set the height to 100px (default height)
            $img.css('z-index', 100)
                .data('originalHeight', $img.height())
                .stop()
                .animate({
                    height: '100px'
                }, imgAnim.speed, imgAnim.easing);
            // the ribbon will animate from the left or right
            // depending on the position of the image
            let ribbonCssParam = {
                top: $el.offset().top - $(window).scrollTop() - 6 + 'px'
            }
            let descriptionCssParam, dir;
            if ($el.offset().left < (w_dim.width / 2)) {
                dir = 'left';
                ribbonCssParam.left = 0;
                ribbonCssParam.right = 'auto';
            } else {
                dir = 'right';
                ribbonCssParam.right = 0;
                ribbonCssParam.left = 'auto';
            }
            $pw_ribbon.css(ribbonCssParam)
                .show()
                .stop()
                .animate({ width: '100%' }, ribbonAnim.speed, ribbonAnim.easing, function () {
                    switch (dir) {
                        case 'left':
                            descriptionCssParam = {
                                'left': $img.outerWidth(true) + 'px',
                                'text-align': 'left'
                            };
                            break;
                        case 'right':
                            descriptionCssParam = {
                                'left': '-300px',
                                'text-align': 'right'
                            };
                            break;
                    };
                    $descrp.css(descriptionCssParam).fadeIn();
                    // show close button and zoom
                    $pw_ribbon_close.show();
                    $pw_ribbon_zoom.show();
                });
        },
        // close the ribbon
        // when in full mode slides in the middle of the page
        // when not slides left
        closeRibbon = function () {
            isRibbonShown = false
            $pw_ribbon_close.hide();
            $pw_ribbon_zoom.hide();
            if (!isFullMode) {
                // current wall image
                let $el = $pw_thumbs.children('li').eq(current);
                resetWall($el);
                // slide out ribbon
                $pw_ribbon.stop().animate({ width: '0%' }, ribbonAnim.speed, ribbonAnim.easing);

            }
            else {
                $pw_ribbon.stop().animate({
                    opacity: 0.8,
                    height: '0px',
                    marginTop: w_dim.height / 2 + 'px' // half of window height
                }, ribbonAnim.speed, function () {
                    $pw_ribbon.css({
                        'width': '0%',
                        'height': '126px',
                        'margin-top': '0px'
                    }).children('img').remove();
                });
                isFullMode = false;
            }
        },
        resetWall = function ($el) {
            let $img = $el.children('img'), $descrp = $img.next();
            $el.data('ribbon', false);
            // reset the image z-index and height
            $img.css('z-index', 1).stop().animate({
                height: $img.data('originalHeight')
            }, imgAnim.speed, imgAnim.easing);
            // fadeOut the description
            $descrp.fadeOut();
            // fadeIn all the other images
            $pw_thumbs.children('li').not($el).animate({ opacity: 1 }, imgAnim.speed);
        },
        showFullImage = function ($el) {
            isFullMode = true;
            $pw_ribbon_close.hide();
            let $img = $el.children('img'),
                large = $img.data('img'),
                // add a loading image on top of the image
                $loading = $('<span class="pw_loading"></span>');
            $el.append($loading);
            // preload large image
            $('<img/>').load(function () {
                let $largeImage = $(this);
                $loading.remove();
                $pw_ribbon_zoom.hide();
                resizeImage($largeImage);
                // reset the current image in the wall
                resetWall($el);
                // animate ribbon in and out
                $pw_ribbon.stop().animate({
                    opacity: 1,
                    height: '0px',
                    marginTop: '63px' // half of ribbons height
                }, ribbonAnim.speed, function () {
                    // add the large image to the DOM
                    $pw_ribbon.prepend($largeImage);
                    $pw_ribbon_close.show();
                    $pw_ribbon.animate({
                        height: '100%',
                        marginTop: '0px',
                        top: '0px'
                    }, ribbonAnim.speed);
                });
            }).attr('src', large);
        },
        resizeImage = function ($image) {
            let widthMargin = 100,
                heightMargin = 100,
                windowH = w_dim.height - heightMargin,
                windowW = w_dim.width - widthMargin,
                theImage = new Image();
            theImage.src = $image.attr("src");
            let imgwidth = theImage.width,
                imgheight = theImage.height;
            if ((imgwidth > windowW) || (imgheight > windowH)) {
                if (imgwidth > imgheight) {
                    let newwidth = windowW,
                        ratio = imgwidth / windowW,
                        newheight = imgheight / ratio;
                    theImage.height = newheight;
                    theImage.width = newwidth;
                    if (newheight > windowH) {
                        let newnewheight = windowH,
                            newratio = newheight / windowH,
                            newnewwidth = newwidth / newratio;
                        theImage.width = newnewwidth;
                        theImage.height = newnewheight;
                    }
                }
                else {
                    let newheight = windowH,
                        ratio = imgheight / windowH,
                        newwidth = imgwidth / ratio;
                    theImage.height = newheight;
                    theImage.width = newwidth;
                    if (newwidth > windowW) {
                        let newnewwidth = windowW,
                            newratio = newwidth / windowW,
                            newnewheight = newheight / newratio;
                        theImage.height = newnewheight;
                        theImage.width = newnewwidth;
                    }
                }
            }
            $image.css({
                'width': theImage.width + 'px',
                'height': theImage.height + 'px',
                'margin-left': -theImage.width / 2 + 'px',
                'margin-top': -theImage.height / 2 + 'px',
                'position': 'absolute',
                'top': '50%',
                'left': '50%',
                'border': '2px solid #fff'
            });
        };
    return { decorate: decorate };
})();

export default wall;