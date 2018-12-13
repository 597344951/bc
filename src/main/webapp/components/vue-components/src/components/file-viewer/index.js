import FileViewer from './src/file-viewer.vue'

if (typeof window !== 'undefined' && window.Vue) {
  /* eslint-disable */
  Vue.component(FileViewer.name, FileViewer)
  new Vue({
    el: '#app'
  })
}

export default FileViewer
