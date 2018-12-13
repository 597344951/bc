import Component from './src/publish-state-dialog.vue'

if (typeof window !== 'undefined' && window.Vue) {
    /* eslint-disable */
    Vue.component(Component.name, Component)
    window.app = new Vue({
        el: '#app',
        data: {
            visible: false,
            content: 9
        }
    })
}

export default Component
