// import 'material-design-icons/iconfont/material-icons.css'
import MultiVideoPlayer from './src/multi-video-player.vue'

if (typeof window !== 'undefined' && window.Vue) {
  /* eslint-disable */
  Vue.component(MultiVideoPlayer.name, MultiVideoPlayer)
  window.app = new Vue({
    el: '#app',
    data: {
      show: true,
      line: 60,
      parts: [
        {
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
      ]
    },
    methods: {
      onPlayStateChange(state) {
        console.log(state)
      }
    }
  })
}

export default MultiVideoPlayer
