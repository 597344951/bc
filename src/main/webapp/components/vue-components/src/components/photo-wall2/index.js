import Component from './src/photo-wall2.vue'

if (typeof window !== 'undefined' && window.Vue) {
    /* eslint-disable */
    Vue.component(Component.name, Component)
    window.app = new Vue({
        el: '#app',
        data: {
            id: 'pw2001',
            photos: [{
                src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                alt: 'boom',
                name: 'pupuki',
                desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
            },{
                src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                alt: 'boom',
                name: 'pupuki',
                desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
            },{
                src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                alt: 'boom',
                name: 'pupuki',
                desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
            },{
                src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                alt: 'boom',
                name: 'pupuki',
                desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
            },{
                src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                alt: 'boom',
                name: 'pupuki',
                desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
            }]
        },
        methods: {
            add() {
                this.photos.push({
                    src: 'http://192.168.1.8:3000/images/254/e66/254e668a23ed7ae46e3dc0781c27d010.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                })
            },
            new() {
                this.photos = [{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                },{
                    src: 'http://192.168.1.8:3000/images/72e/819/72e819ac2ae4b236a24dce834743d067.jpg',
                    alt: 'boom',
                    name: 'pupuki',
                    desc: '37d12f2eb9389b50bd4c59068335e5dde7116e37.37d12f2eb9389b50bd4c59068335e5dde7116e37'
                }]
            }
        }
    })
}

export default Component