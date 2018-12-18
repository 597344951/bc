window.onclose = function () {

  return true;
}

window.onFocus = function () {}

let routes = [{
  // 传递模版id, 子组件通过this.$route.params 获取
  path: '/category/:categoryId',
  name: '指定分类',
}]

let ins = new Vue({
  el: "#app",
  router: new VueRouter({
    routes: routes
  }),
  data: {
    shadowOpen: true,
    //当前播放进度
    progress: window.progress ? window.progress : {
      creditHours: 0,
      playProgress: 0,
      lessonId: window.lesson.lessonId,
      lessonUnitId: window.lesson.lessonUnitId,
    },
    rmJson: window.rmJson,
    //当前课程
    lesson: window.lesson ? window.lesson : {
      creditHours: 0
    },
    timer: null,
    timerLeaft: 15,
    tick: 15,
    timecount: 0,
    parts: [{
        src: 'http://192.168.1.8:3000/videos/b0c/d0c/b0cd0c944296e7d417336cbdfb668f71.mp4',
        poster: 'http://192.168.1.8:3000/images/1e3/4e5/1e34e5c053719c0f857916e8e9800548.jpg'
      },
      {
        src: 'http://192.168.1.8:3000/videos/0ea/ace/0eaacebe8ddf58f192ba2034668a6724.mp4',
        poster: 'http://192.168.1.8:3000/images/1e3/4e5/1e34e5c053719c0f857916e8e9800548.jpg'
      },
      {
        src: 'http://192.168.1.8:3000/videos/79b/5af/79b5af0c548fe315cb0bfa3949c30c70.mp4',
        poster: 'http://192.168.1.8:3000/images/1e3/4e5/1e34e5c053719c0f857916e8e9800548.jpg'
      }
    ],
    totalPlayTime: 0, //当前总播放时间    
    playStartTime: null, // 播放开始时间
    playEndTime: null, //播放结束时间
  },
  mounted() {
  },
  methods: {
    startTimer() {
      console.debug('启动定时器')
      this.timer = setInterval(() => {
        this.tick--
        if (this.tick <= 0) {
          this.tick = this.timerLeaft
          this.saveProgress()
        }
      }, 1000)
    },
    clearTimer() {
      if (this.timer) {
        console.debug('清除定时器')
        clearInterval(this.timer)
      }
    },
    saveProgress() {
      this.timecount += this.timerLeaft
      if (this.timecount >= 60) {
        if(this.lesson.creditHours > this.progress.creditHours) this.progress.creditHours++
        this.timecount = 0
      }

      console.debug('保存进度信息')
      let url = '/lesson/progress/progress'
      let data = JSON.parse(JSON.stringify(this.progress))
      data.endToSave = false
      if (this.lesson.sourceType == 0) {
        data.playProgress = Math.floor(this.$refs.player.getProcess())
      }

      data.creditHours = 1
      ajax_json_promise(url, 'post', data).then(result => {
        //this.$message('保存成功')
      })
    },
    saveEndProgress() {
      console.debug('保存进度')
      let url = '/lesson/progress/progress'
      let data = JSON.parse(JSON.stringify(this.progress))
      data.endToSave = true
      if (this.lesson.sourceType == 0) {
        data.playProgress = Math.floor(this.$refs.player.getProcess())
      }

      data.creditHours = 0
      ajax_json_promise(url, 'post', data).then(result => {
        //this.$message('保存成功')
      })
    },
    //play , pause , ended
    onPlayStateChange(statusText) {
      if (statusText == 'play') {
        this.startTimer()
      } else {
        this.clearTimer()
      }
      if (statusText == 'ended') {
        this.saveEndProgress()
      }
      console.debug('onPlayStateChange: ', arguments)
    },
    getDisplayParts() {
      let rm = this.rmJson
      return [{
        src: `/media-server/url?url=${rm.url}`,
        poster: `/media-server/url?url=${rm.coverUrl}`
      }]
    },
    otherResourceStart(){
      this.shadowOpen = false
      this.tick = 60
      this.timerLeaft = 60
      this.startTimer()
    }
  },
  destroyed() {
    this.clearTimer()
  },

});
window.appInstince = ins;