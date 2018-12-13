import PhotoWrap1 from './src/photo-wrap-1.vue'

if (typeof window !== 'undefined' && window.Vue) {
  /* eslint-disable */
  Vue.component(PhotoWrap1.name, PhotoWrap1)
  new Vue({
    el: '#app',
    data: {
      photos: [{
        src: 'http://192.168.1.8:3000/images/103/121/103121ab31695868ee8026370be015ca.jpg',
        alt: 'boom',
        name: 'pupuki',
        desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
      }, {
        src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
        alt: 'boom',
        name: 'pupuki',
        desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
      }, {
        src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
        alt: 'boom',
        name: 'pupuki',
        desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
      }, {
        src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
        alt: 'boom',
        name: 'pupuki',
        desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
      }, {
        src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
        alt: 'boom',
        name: 'pupuki',
        desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
      }]
    }
  })
}

export default PhotoWrap1
