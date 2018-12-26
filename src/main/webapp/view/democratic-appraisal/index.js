const app = new Vue({
  el: '#app',
  data: {
    activeTab: 'person',
    type: {
      '1': '党员小组会',
      '2': '支部党员大会',
      '3': '支部委员会',
      '4': '党课',
      '5': '组织生活会',
      '6': '民主评议会'
    },
    appraisal: {
      '0': '未评议',
      '1': '不合格',
      '2': '合格',
      '3': '优秀',
    },
    label: {
      person: '民主评议会记录',
      all: '民主评议会参加记录'
    },
    schedule: {
      list: [],
      pageSize: 20,
      total: 0
    },
    participated: {
      list: [],
      pageSize: 20,
      total: 0
    },
    participant: {
      show: false,
      list: [],
      countStr: ''

    },
    summary: {
      show: false,
      current: {
        show: false,
        data: {}
      },
      belongs: '',
      props: {
        summary: '',
        annex: []
      }
    },
  },
  mounted() {
    this.loadParticipatedSchedule(1, this.participated.pageSize)
  },
  methods: {
    onCurrentChange(num) {
      this.loadSchedule(num, this.schedule.pageSize)
    },
    onCurrentChangeParticipated(num) {
      this.loadParticipatedSchedule(num, this.participated.pageSize)
    },
    loadSchedule(pageNum, pageSize) {
      AJAX.get(
        `/threeone/${pageNum}/${pageSize}?meetingType=${meetingType}`,
        resp => {
          if (resp.status) {
            this.schedule = resp.data
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    loadParticipatedSchedule(pageNum, pageSize) {
      AJAX.get(
        `/threeone/participated/${pageNum}/${pageSize}?meetingType=${meetingType}`,
        resp => {
          if (resp.status) {
            this.participated = resp.data
            this.participated.list.forEach(item => {
              item.typeName = this.type[item.type]
              item.isParticipate = '' + item.isParticipate
              item.startTimeString = `${new Date(item.startTime).toLocaleDateString()} ${new Date(item.startTime).toLocaleTimeString()}`
              item.endTimeString = `${new Date(item.endTime).toLocaleDateString()} ${new Date(item.endTime).toLocaleTimeString()}`
            })
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    onTabClick(tab, e) {
      if ('person' == tab.name && this.participated.list.length <= 0) {
        this.loadParticipatedSchedule(1, this.participated.pageSize)
      }
      if ('all' == tab.name && this.schedule.list.length <= 0) {
        this.loadSchedule(1, this.schedule.pageSize)
      }
    },
    loadScheduleParticipant(row) {
      AJAX.get(
        `/threeone/participant/${row.id}`,
        resp => {
          if (resp.status) {
            this.participant.list = []
            this.participant.show = true
            let total = type_0 = type_1 = type_2 = type_3 = 0
            resp.data.forEach(item => {
              total ++
              item.isParticipate = '' + item.isParticipate
              switch(item.isParticipate) {
                case '0':
                  type_0 ++
                  break
                case '1':
                  type_1 ++
                  break
                case '2':
                  type_2 ++
                  break
                case '3':
                  type_3 ++
                  break
                default:
                  break
              }
              this.participant.list.push(item)
            })
            this.participant.countStr = `评议总人数：${total}，评议率：${this.toDecimal((type_1 + type_2 + type_3) * 100 / total) + '%'}，不合格率：${this.toDecimal(type_1 * 100 / total) + '%'}，合格率：${this.toDecimal(type_2 * 100 / total) + '%'}，优秀率：${this.toDecimal(type_3 * 100 / total) + '%'}`
          } else {
            this.$message.error(resp.msg)
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    conmmitScheduleParticipantSign() {
      this.$confirm('确认提交当前修改?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        AJAX.postJson(
          '/threeone/participant',
          this.participant.list,
          resp => {
            if (resp.status) {
              this.$message({
                message: '提交成功...',
                type: 'success'
              })
              this.participant.show = false
            } else {
              this.$message.error(resp.msg)
            }
          },
          err => {
            this.$message.error('系统错误， 请联系管理员')
          }
        )
      }).catch(() => { })
    },
    scheduleSign(row) {
      this.$confirm('确认当前评议 ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.participant.list = [{
          scheduleId: row.id,
          userId: row.participantId,
          id: row.participantId,
          isParticipate: row.isParticipate
        }]
        this.conmmitScheduleParticipantSign()
      }).catch(() => {
        row.isParticipate = '0'
      })
    },
    onSummaryAnnexUploadSuccess(response, file, fileList) {
      this.summary.props.annex.push({
        uid: file.uid,
        name: response.original,
        url: response.url,
        type: response.type
      })
    },
    onSummaryAnnexRemove(file, fileList) {
      for (let i in this.summary.props.annex) {
        if (file.uid == this.summary.props.annex[i].uid) {
          this.summary.props.annex.splice(i, 1)
          break
        }
      }
    },
    resetSummary() {
      this.summary.props.summary = ''
      this.summary.props.annex = []
      this.$refs.summaryAnnexUpload.clearFiles()
    },
    conmmitSummary() {
      if (this.summary.props.annex <= 0) {
        return this.$message.error('请上传附件文件！！！')
      }
      this.$confirm('确认提交 ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        AJAX.postJson(
          '/threeone/summary',
          {
            scheduleId: this.summary.belongs.id,
            summary: this.summary.props.summary,
            annex: JSON.stringify(this.summary.props.annex)
          },
          resp => {
            if (resp.status) {
              this.$message({
                message: '提交成功...',
                type: 'success'
              })
              this.summary.show = false
              this.resetSummary()
            } else {
              this.$message.error('提交失败：' + resp.msg)
            }
          },
          err => {
            this.$message.error('系统错误， 请联系管理员')
          }
        )
      }).catch(() => { })
    },
    onSummaryView(row) {
      AJAX.get(
        `/threeone/summary/${row.id}`,
        resp => {
          if (resp.status) {
            row.summary = resp.data
            this.summary.current.data = resp.data
            this.summary.current.show = true
          } else {
            this.$message.error(resp.msg)
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    onAnnexView(annex) {
      window.open(`${mediaServe}${annex.url}`, '_blank')
    },
    toDecimal(num) {
      let f = parseFloat(num);
      if (isNaN(f)) {
        return num
      }
      f = Math.round(num * 100) / 100;
      return f
    }
  }
})
