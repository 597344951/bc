import { _info } from './components/index'

if (typeof window !== 'undefined' && window.Vue) {
  /* eslint-disable */
  new Vue({
    el: '#app',
    data: {
      selectedComponent: '',
      components: _info
    },
    methods: {
      onMenuSelect(index, indexPath) {
        this.selectedComponent = `${index}.html`
      }
    }
  })
}
