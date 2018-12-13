<template>
  <div class="player-container">
    <div class="player-videos">
      <transition-group name="animate-classes-transition" :enter-active-class="enter" :leave-active-class="leave">
        <video muted="muted" v-show="index == videoIndex" v-for="(part, index) in parts" :id="'video-part-' + index" :src="part.src" :poster="part.poster" preload :key="index" :index="index">
          您的浏览器不支持该视频播放，请下载最新谷歌浏览器。
        </video>
      </transition-group>
    </div>
    <div class="player-controls">
      <i v-if="playState != 'playing'" @click="play" class="material-icons player-controls-play">play_circle_outline</i>
      <i v-if="playState == 'playing'" @click="pause" class="material-icons player-controls-pause">pause_circle_outline</i>
      <div class="player-controls-played-time">{{format(processBar.process)}}</div>

      <el-slider class="player-controls-process" v-model="processBar.process" :show-tooltip="false" :min="processBar.min" :max="processBar.max" :step="processBar.step" @change="onProcessChange"></el-slider>

      <i v-if="!isFullscreen" class="material-icons player-controls-fullscreen" @click="fullscreen">fullscreen</i>
      <i v-if="isFullscreen" class="material-icons player-controls-fullscreen" @click="exitFullscreen">fullscreen_exit</i>
      <div class="player-controls-total-time">{{format(processBar.max)}}</div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'multi-video-player',
  label: 'H5视频播放器（可多段视频）',
  props: ['parts', 'loop', 'autoplay', 'enter', 'leave', 'seeker'],
  data() {
    return {
      processBar: {
        process: 0,
        min: 0,
        max: 0,
        step: 1
      },
      playState: 'paused',
      isFullscreen: false,
      videoIndex: 0,
      video: null,
      videos: [],
      processTimer: null,
      controlsTimer: null,
      secondIncrement: 0
    }
  },
  mounted() {
    window.onresize = () => {
      if (
        window.screen.width ==
        document.getElementsByClassName('player-container')[0].clientWidth
      ) {
        this.isFullscreen = true
      } else {
        this.isFullscreen = false
      }
      this.resizePlayerControls()
    }
    window.onload = () => {
      this.resizePlayerControls()
      this.video = document.getElementById('video-part-0')
      this.videos = document
        .getElementsByClassName('player-container')[0]
        .getElementsByTagName('video')

      this.initPlayerControls(this.videos)
    }
  },
  methods: {
    initPlayerControls(videos) {
      let duration = 0
      for (let i = 0; i < videos.length; i++) {
        let video = videos[i]
        duration += video.duration
        video.onended = e => {
          let index = Number.parseInt(e.currentTarget.getAttribute('index'))
          index++
          if (index == this.videos.length) {
            //播放结束, 切到第一个分片
            //重新加载释放内存
            for (let i = 0; i < this.videos.length; i++) {
              this.videos[i].load()
            }
            this.switch(0)
            this.over()
            //播放结束
            this.$emit('play-state-change', "ended")
          } else {
            this.switch(index)
            this.play()
          }
        }

        video.onmousemove = () => {
          clearTimeout(this.controlsTimer)
          this.showControls()
          this.controlsTimer = setTimeout(() => {
            this.hideControls()
          }, 5000)
        }
      }
      this.processBar.max = duration
      this.video = videos[0]
      //添加定时器获取播放时间
      this.newProcessTimer()
      //切到第一段
      this.switch(0)
      //是否有跳转
      if(this.seeker) {
        this.seek(Number.parseInt(this.seeker))
        this.processBar.process = Number.parseInt(this.seeker)
      }
      //自动播放
      if ('' === this.autoplay || 'true' == this.autoplay) {
        this.play()
      }
    },
    resizePlayerControls() {
      let container = document.getElementsByClassName('player-container')[0]
      let processBar = document.getElementsByClassName(
        'player-controls-process'
      )[0]
      let containerWidth = container.clientWidth
      let processBarWidth = containerWidth - 320
      processBar.style.width = processBarWidth + 'px'
    },
    play() {
      this.video.play()
      this.playState = 'playing'
      this.newProcessTimer()
      //开始事件
      this.$emit('play-state-change', "play")
    },
    pause() {
      this.playState = 'paused'
      this.video.pause()
      clearInterval(this.processTimer)
      //开始事件
      this.$emit('play-state-change', "pause")
    },
    fullscreen() {
      document
        .getElementsByClassName('player-container')[0]
        .webkitRequestFullscreen()
      this.isFullscreen = true
    },
    exitFullscreen() {
      document.webkitExitFullscreen()
      this.isFullscreen = false
    },
    onProcessChange() {
      this.seek(this.processBar.process)
    },
    format(second) {
      let h = this.leftPadZero((second / 60 / 60) % 60, 2)
      let m = this.leftPadZero((second / 60) % 60, 2)
      let s = this.leftPadZero(second % 60, 2)
      return h == '00' ? `${m}:${s}` : `${h}:${m}:${s}`
    },
    leftPadZero(num, length) {
      return (Array(length).join('0') + Math.floor(num)).slice(-length)
    },
    over() {
      clearInterval(this.processTimer)
      this.playState = 'over'
      //连续播放
      if ('' === this.loop || 'true' == this.loop) {
        this.play()
      }
    },
    newProcessTimer() {
      this.processTimer = setInterval(() => {
        this.processBar.process = this.video.currentTime + this.secondIncrement
        if (this.video.currentTime == this.processBar.max) {
          this.over()
        }
      }, 1000)
    },
    switch(index, seconds) {
      this.pause()
      this.video = this.videos[0]
      this.secondIncrement = 0
      let addAble = true
      for (let i = 0; i < this.videos.length; i++) {
        let video = this.videos[i]
        if (index == i) {
          addAble = false
          //video.style.opacity = 1;
          this.videoIndex = index
          this.video = video
        } else {
          //video.style.opacity = 0;
        }
        if (addAble) this.secondIncrement += video.duration
      }
      this.video.currentTime = seconds ? seconds : 0
    },
    seek(seconds) {
      let playState = this.playState
      for (let i = 0; i < this.videos.length; i++) {
        let video = this.videos[i]
        if (seconds > video.duration) {
          seconds = seconds - video.duration
        } else {
          this.switch(i, seconds)
          if('playing' == playState) this.play()
          break
        }
      }
    },
    showControls() {
      document.getElementsByClassName('player-controls')[0].style.opacity = '1'
    },
    hideControls() {
      document.getElementsByClassName('player-controls')[0].style.opacity = '0'
    },
    getProcess() {
      return this.processBar.process
    }
  },
  watch: {
    seeker(newVal, oldVal) {
      if(newVal) {
        this.processBar.process = Number.parseInt(newVal)
        this.seek(this.processBar.process)
      }
    }
  }
}
</script>
<style scoped>
video::-webkit-media-controls {
  display: none !important;
}
.player-container {
  height: 100%;
  width: 100%;
  background-color: #000;
  position: relative;
  user-select: none;
}

.player-controls {
  background-color: #fff;
  width: 100%;
  opacity: 0.85;
  position: absolute;
  bottom: 0px;
  opacity: 1;
}

.player-controls-play,
.player-controls-pause,
.player-controls-played-time,
.player-controls-process,
.player-controls-total-time,
.player-controls-fullscreen {
  float: left;
  display: block;
  box-sizing: border-box;
}
.player-controls-total-time,
.player-controls-fullscreen {
  float: right;
}
.player-controls-process {
  margin: 0px 20px;
  width: 0px;
}
.player-controls-play,
.player-controls-pause,
.player-controls-fullscreen {
  margin: 5px 5px 0 5px;
  font-size: 30px;
  width: 30px;
  cursor: pointer;
}
.player-controls-played-time,
.player-controls-total-time {
  width: 80px;
  min-width: 40px;
  border: 1px solid rgba(221, 221, 221, 0.5);
  padding: 3px;
  text-align: center;
  border-radius: 5px;
  margin: 5px 10px 0 10px;
}
.player-controls-total-time {
  margin-right: 0;
}
.player-videos {
  height: 100%;
  width: 100%;
}
.player-videos video {
  height: 100%;
  width: 100%;
  background-color: #000;
  /* opacity: 0; */
  position: absolute;
  left: 0px;
  top: 0px;
}
</style>
