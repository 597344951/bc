import Component from './src/md5-check-dialog.vue'

if (typeof window !== 'undefined' && window.Vue) {
    /* eslint-disable */
    Vue.component(Component.name, Component)
    window.app = new Vue({
        el: '#app',
        data: {
            visible: false
        },
        methods: {
            finishedHandler() {
                console.log('Finished...')
            }
        }
    })
}

export default Component