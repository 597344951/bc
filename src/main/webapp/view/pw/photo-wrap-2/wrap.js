window.onload = function () {
    var oWrap = document.getElementsByClassName('wrap')[0];
    var oImg = oWrap.getElementsByTagName('img');
    var oImgLength = oImg.length;
    var preview = document.getElementsByClassName('preview')[0];
    var pMask = document.getElementsByClassName('mask')[0];
    var pImg = preview.getElementsByTagName('img')[0];
    var Deg = 360 / oImgLength;
    var nowX, nowY, lastX, lastY, minusX = 0, minusY = 0;
    var roY = 0, roX = -10;
    var timer;

    for (var i = 0; i < oImgLength; i++) {
        oImg[i].style.transform = 'rotateY(' + i * Deg + 'deg) translateZ(350px)';
        oImg[i].style.transition = 'transform 1s ' + (oImgLength - 1 - i) * 0.1 + 's';

        oImg[i].onclick = function (ev) {
            pImg.src = this.src;
            console.log(this.src);
            preview.style.display = 'block';
            pMask.style.display = 'block';
            oWrap.style.display = 'none';
        }

    }

    pMask.onclick = function (ev) {
        preview.style.display = 'none';
        pMask.style.display = 'none';
        oWrap.style.display = 'block';
    }

    preview.onclick = function (ev) {
        preview.style.display = 'none';
        pMask.style.display = 'none';
        oWrap.style.display = 'block';
    }

    mTop();

    window.onresize = mTop;

    function mTop() {
        var wH = document.documentElement.clientHeight;
        oWrap.style.marginTop = wH / 2 - 180 + 'px';
    }

    timer = setInterval(function () {
        roY += 360 / oImgLength / 300;
        if(roX == 360) roX = 0;
        oWrap.style.transform = 'rotateX(' + roX + 'deg) rotateY(' + roY + 'deg)';
    }, 20);

    // 拖拽：三个事件-按下 移动 抬起
    //按下
    document.onmousedown = function (ev) {
        ev = ev || window.event;
        clearInterval(timer);

        //鼠标按下的时候，给前一点坐标赋值，为了避免第一次相减的时候出错
        lastX = ev.clientX;
        lastY = ev.clientY;

        //移动
        this.onmousemove = function (ev) {
            ev = ev || window.event;

            nowX = ev.clientX; // clientX 鼠标距离页面左边的距离
            nowY = ev.clientY; // clientY ………………………………顶部………………

            //当前坐标和前一点坐标差值
            minusX = nowX - lastX;
            minusY = nowY - lastY;

            //更新wrap的旋转角度，拖拽越快-> minus变化大 -> roY变化大 -> 旋转快
            roY += minusX * 0.2; // roY = roY + minusX*0.2;
            roX -= minusY * 0.1;

            oWrap.style.transform = 'rotateX(' + roX + 'deg) rotateY(' + roY + 'deg)';

            //前一点的坐标
            lastX = nowX;
            lastY = nowY;

        }
        //抬起
        this.onmouseup = function () {
            this.onmousemove = null;
            timer = setInterval(function () {
                roY += 360 / oImgLength / 300;
                if(roX == 360) roX = 0;
                oWrap.style.transform = 'rotateX(' + roX + 'deg) rotateY(' + roY + 'deg)';
            }, 20);
        }
        return false;
    }
}
