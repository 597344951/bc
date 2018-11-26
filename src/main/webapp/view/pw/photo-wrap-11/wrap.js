/* 
可用轮播特效：
    Press away：fxPressAway
    Side Swing：fxSideSwing
    Fortune wheel：fxFortuneWheel
    Swipe：fxSwipe
    Push reveal：fxPushReveal
    Snap in：fxSnapIn
    Let me in：fxLetMeIn
    Stick it：fxStickIt
    Archive me：fxArchiveMe
    Vertical growth：fxVGrowth
    Slide Behind：fxSlideBehind
    Soft Pulse：fxSoftPulse
    Earthquake：fxEarthquake
    Cliff diving：fxCliffDiving
*/
(function () {
    var support = { animations: Modernizr.cssanimations },
        animEndEventNames = {
            'WebkitAnimation': 'webkitAnimationEnd',
            'OAnimation': 'oAnimationEnd',
            'msAnimation': 'MSAnimationEnd',
            'animation': 'animationend'
        },
        animEndEventName = animEndEventNames[Modernizr.prefixed('animation')],
        component = document.getElementById('component'),
        items = component.querySelector('ul.itemwrap').children,
        current = 0,
        itemsCount = items.length;

    function init() {
        if(effect) {
            changeEffect(effect);
        } else {
            changeEffect('fxPushReveal');
        }
        
        //添加轮播定时器
        setInterval(function () {
            navigate('next');
        }, 5000);
    }

    function changeEffect(effectName) {
        component.className = component.className.replace(/\bfx.*?\b/g, '');
        classie.addClass(component, effectName);
    }

    function navigate(dir) {
        var cntAnims = 0;
        var currentItem = items[current];
        if (dir === 'next') {
            current = current < itemsCount - 1 ? current + 1 : 0;
        }
        else if (dir === 'prev') {
            current = current > 0 ? current - 1 : itemsCount - 1;
        }
        var nextItem = items[current];
        var onEndAnimationCurrentItem = function () {
            this.removeEventListener(animEndEventName, onEndAnimationCurrentItem);
            classie.removeClass(this, 'current');
            classie.removeClass(this, dir === 'next' ? 'navOutNext' : 'navOutPrev');
            ++cntAnims;
            if (cntAnims === 2) {
                isAnimating = false;
            }
        }
        var onEndAnimationNextItem = function () {
            this.removeEventListener(animEndEventName, onEndAnimationNextItem);
            classie.addClass(this, 'current');
            classie.removeClass(this, dir === 'next' ? 'navInNext' : 'navInPrev');
            ++cntAnims;
            if (cntAnims === 2) {
                isAnimating = false;
            }
        }
        if (support.animations) {
            currentItem.addEventListener(animEndEventName, onEndAnimationCurrentItem);
            nextItem.addEventListener(animEndEventName, onEndAnimationNextItem);
        }
        else {
            onEndAnimationCurrentItem();
            onEndAnimationNextItem();
        }
        classie.addClass(currentItem, dir === 'next' ? 'navOutNext' : 'navOutPrev');
        classie.addClass(nextItem, dir === 'next' ? 'navInNext' : 'navInPrev');
    }

    init();
})();