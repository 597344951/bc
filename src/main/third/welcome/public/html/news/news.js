const app = new Vue({
  el: '#app',
  data: {
    newses: {
      list: [],
      pageNum: 1,
      pageSize: 20,
      total: 0
    },
    news: {
      show: false,
      paragraphAddShow: false,
      props: {
        title: '',
        text: '',
        paragraph: []
      },
      preview: {
        show: false,
        title: '',
        paragraph: []
      }
    }
  },
  mounted() {
    this.loadNews(this.newses.pageNum, this.newses.pageSize)
  },
  methods: {
    changePage(page) {
      this.newses.pageNum = page
      this.loadNews(this.newses.pageNum, this.newses.pageSize)
    },
    addText() {
      if (this.news.props.text) {
        this.news.props.paragraph.push({
          type: 'text',
          text: this.news.props.text
        })
      }
      this.news.props.text = ''
      this.news.paragraphAddShow = false
    },
    addImage(response, file, fileList) {
      if ('SUCCESS' == response.state) {
        this.news.props.paragraph.push({
          type: 'image',
          src: response.path
        })
      }
    },
    deleteParagraph(index) {
      this.news.props.paragraph.splice(index, 1)
    },
    addNews() {
      if (this.news.props.title && this.news.props.paragraph.length > 0) {
        this.$confirm('确认添加该动态新闻 ?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            $.ajax({
              type: 'POST',
              url: '/news',
              dataType: 'json',
              data: {
                title: this.news.props.title,
                content: JSON.stringify(this.news.props.paragraph)
              },
              success: function(reps) {
                if ('SUCCESS' == reps.state) {
                  app.$message({
                    message: '添加成功',
                    type: 'success'
                  })
                  app.news.show = false
                  app.emptyNews()
                  app.loadNews(app.newses.pageNum, app.newses.pageSize)
                }
              },
              error: function(err) {
                app.$message.error('系统错误，请联系管理员。')
              }
            })
          })
          .catch(() => {})
      } else {
        this.$message.error('参数填写不全，请完整填写后在提交。')
      }
    },
    emptyNews() {
      this.news.props = {
        title: '',
        text: '',
        paragraph: []
      }
    },
    loadNews(pageNum, pageSize) {
      $.ajax({
        type: 'GET',
        url: `/news?page=${pageNum}&size=${pageSize}`,
        dataType: 'json',
        success: function(resp) {
          if ('SUCCESS' == resp.state) {
            ;(app.newses.total = resp.newses.total),
              (app.newses.list = resp.newses.rows)
          }
        },
        error: function(err) {
          app.$message.error('系统错误，请联系管理员。')
        }
      })
    },
    preview(row) {
      this.news.preview.title = row.title
      this.news.preview.paragraph = JSON.parse(row.content)
      this.news.preview.show = true
    }
  }
})
