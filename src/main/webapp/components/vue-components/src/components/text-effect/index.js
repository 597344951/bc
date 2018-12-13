import Component from './src/text-effect.vue'

if (typeof window !== 'undefined' && window.Vue) {
    /* eslint-disable */
    Vue.component(Component.name, Component)
    window.app = new Vue({
        el: '#app',
        data: {
            id: 'e001',
            text: '中文ENGLISHにほんご',
            type: 'hop'
        }
    })
}

export default Component