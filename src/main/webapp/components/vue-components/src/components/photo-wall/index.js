import Component from './src/photo-wall.vue'

if (typeof window !== 'undefined' && window.Vue) {
    /* eslint-disable */
    Vue.component(Component.name, Component)
    window.app = new Vue({
        el: '#app',
        data: {
            id: 'pw001',
            photos: [{
                src: 'http://c.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=b59e3576cefcc3ceb495c137a775fabe/37d12f2eb9389b50bd4c59068335e5dde7116e37.jpg',
                alt: 'boom'
            }]
        },
        methods: {
            add() {
                this.photos.push({
                    src: 'http://c.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=b59e3576cefcc3ceb495c137a775fabe/37d12f2eb9389b50bd4c59068335e5dde7116e37.jpg',
                    alt: 'boom'
                })
            }
        }
    })
}

export default Component