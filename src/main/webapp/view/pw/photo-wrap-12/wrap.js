$(function () {
    /* $(".heartPic li").hover(function () {
        $(this).addClass("on")
        $(this).find("img").animate({ "width": "100%", "height": "100%" })
        $(this).find("div").animate({ "width": "60%", "height": "60%" })
    }, function () {
        $(this).animate({ height: "100px" }, 100).removeClass("on")
        $(this).find("img").stop(true, true).animate({ "width": "100px", "height": "100px" })
        $(this).find("div").stop(true, true).animate({ "width": "100px", "height": "100px" })
    }); */

    let imgs = $(".heartPic li"), index = 0, curImg = imgs[index], preImg
    setInterval(() => {
        if (preImg) {
            $(preImg).animate({ height: "100px" }, 100).removeClass("on")
            $(preImg).find("img").stop(true, true).animate({ "width": "100px", "height": "100px" })
            $(preImg).find("div").stop(true, true).animate({ "width": "100px", "height": "100px" })
        }
        $(curImg).addClass("on")
        $(curImg).find("img").animate({ "width": "100%", "height": "100%" })
        $(curImg).find("div").animate({ "width": "60%", "height": "80%" })

        index++
        if (index == imgs.length) {
            index = 0
        }
        preImg = curImg
        curImg = imgs[index]
    }, 5000)
})