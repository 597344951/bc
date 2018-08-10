/*
  wangqizheng 2018.03.12
 */
"use strict";var $play=$(".play"),$preAudio=$("audio")[0];$play.click(function(){var a=$(this),e=a.prev()[0];a.hasClass("active")?e.paused?(e.play(),$play.addClass("playing")):($play.removeClass("playing"),e.pause()):($play.removeClass("active"),a.addClass("active playing"),$preAudio.pause(),$preAudio.currentTime=0,e.play(),$preAudio=e)});var endPlay=function(){$play.removeClass("active")};