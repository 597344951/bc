const app = new Vue({
  el: '#app',
  data: {
    schedule: {
      list: [],
      pageSize: 20,
      total: 0
    }
  },
  mounted() {
    this.loadSchedule(1, this.schedule.pageSize)
  },
  methods: {
    onCurrentChange(num) {
      this.loadSchedule(num, this.schedule.pageSize)
    },
    loadSchedule(pageNum, pageSize) {
      AJAX.get(
        `/threeone/${pageNum}/${pageSize}`,
        resp => {
          if (resp.status) {
            this.schedule = resp.data
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    }
  }
})
