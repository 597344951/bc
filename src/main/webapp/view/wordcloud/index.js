const app = new Vue({
  el: '#app',
  data: {
    wordcloud: {
      formated: false,
      type: '',
      title: '',
      text: '',
      textFile: '',
      width: '',
      height: '',
      imageShape: '',
      backgroundColor: '#fff'
    },
    view: {
      show: false,
      url: ''
    },
    shape: {
      show: false,
      list: []
    }
  },
  mounted() {
    this.loadShape()
  },
  methods: {
    loadShape() {
      AJAX.get(
        '/view/wordcloud/shape.json',
        resp => {
         this.shape.list = []
         resp.forEach(shape => {
          this.shape.list.push(mediaServe + shape)
         });
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    onTextUploadSuccess(resp) {
      this.wordcloud.textFile = mediaServe + resp.url
    },
    onTextRemove() {
      this.wordcloud.textFile = ''
    },
    onShapeRemove() {
      this.wordcloud.imageShape = ''
    },
    onShapeSelected(shape) {
      this.wordcloud.imageShape = shape
      this.shape.show =false
    },
    onView() {
      if (!this.wordcloud.title) {
        this.$message.error('请输入主题')
        return
      }
      if (!this.wordcloud.text && !this.wordcloud.textFile) {
        this.$message.error('请输入段落文本或上传文本文件')
        return
      }
      if (this.wordcloud.textFile) {
        this.wordcloud.type = 'file'
      } else {
        this.wordcloud.type = 'text'
      }
      AJAX.postJson(
        mediaServe + '/image/wordcloud',
        this.wordcloud,
        resp => {
          if (resp.state) {
            this.view.url = mediaServe + resp.url
            this.view.show = true
          } else {
            this.$message.error(resp.msg)
          }
        },
        err => {
          this.$message.error('系统错误，请联系管理员')
        }
      )
    },
    onShare() {
      if (this.view.url) {
        let share = [{ weburl: this.view.url, playtime: 60 }]
        let url =
          '/view/publish/new.jsp?title=' +
          this.wordcloud.title +
          '&startStep=2&url=' +
          encodeURIComponent(JSON.stringify(share))
        if (parent.addTab) {
          parent.addTab({ menuId: -7, name: this.wordcloud.title, url: url })
        } else {
          window.location.href = url
        }
      }
    }
  }
})
