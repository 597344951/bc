const app = new Vue({
  el: '#app',
  data: {
    activeTab: 'person',
    label: {
      person: '三会一课参加记录',
      all: '三会一课会议记录'
    },
    schedule: {
      list: [],
      pageSize: 20,
      total: 0
    },
    participant: {
      list: [],
      pageSize: 20,
      total: 0
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
    learned: {
      show: false,
      current: {
        show: false,
        data: {}
      },
      belongs: '',
      props: {
        learned: '',
        annex: []
      }
    }
  },
  mounted() {
    if('life' == meetingType) {
      this.label.person = '组织生活会参加记录'
      this.label.all = '组织生活会会议记录'
    }
    this.loadParticipantSchedule(1, this.participant.pageSize)
  },
  methods: {
    onCurrentChange(num) {
      this.loadSchedule(num, this.schedule.pageSize)
    },
    onCurrentChangeParticipant(num) {
      this.loadParticipantSchedule(num, this.participant.pageSize)
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
    loadParticipantSchedule(pageNum, pageSize) {
      AJAX.get(
        `/threeone/participant/${pageNum}/${pageSize}?meetingType=${meetingType}`,
        resp => {
          if (resp.status) {
            this.participant = resp.data
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    onTabClick(tab, e) {
      if ('person' == tab.name && this.participant.list.length <= 0) {
        this.loadParticipantSchedule(1, this.participant.pageSize)
      }
      if ('all' == tab.name && this.schedule.list.length <= 0) {
        this.loadSchedule(1, this.schedule.pageSize)
      }
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
      if (!this.summary.props.summary && this.summary.props.annex <= 0) {
        this.$message.error('请填写会议纪要或者上传会议纪要附件')
      }
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
            this.$message.error('提交会议纪要失败：' + resp.msg)
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    onLearnedAnnexUploadSuccess(response, file, fileList) {
      this.learned.props.annex.push({
        uid: file.uid,
        name: response.original,
        url: response.url,
        type: response.type
      })
    },
    onLearnedAnnexRemove(file, fileList) {
      for (let i in this.learned.props.annex) {
        if (file.uid == this.learned.props.annex[i].uid) {
          this.learned.props.annex.splice(i, 1)
          break
        }
      }
    },
    resetLearned() {
      this.learned.props.learned = ''
      this.learned.props.annex = []
      this.$refs.learnedAnnexUpload.clearFiles()
    },
    conmmitLearned() {
      if (!this.learned.props.learned && this.learned.props.annex <= 0) {
        this.$message.error('请填写会议心得或者上传会议心得附件')
      }
      AJAX.postJson(
        '/threeone/learned',
        {
          scheduleId: this.learned.belongs.id,
          userId: this.learned.belongs.userId,
          summary: this.learned.props.learned,
          annex: JSON.stringify(this.learned.props.annex)
        },
        resp => {
          if (resp.status) {
            this.$message({
              message: '提交成功...',
              type: 'success'
            })
            this.learned.show = false
            this.resetLearned()
          } else {
            this.$message.error('提交会议纪要失败：' + resp.msg)
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    onSummaryView(row) {
      if (row.summary) {
        this.summary.current.data = row.summary
        this.summary.current.show = true
        return
      }
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
    onLearnedView(row) {
      if (row.learned) {
        this.learned.current.data = row.learned
        this.learned.current.show = true
        return
      }
      AJAX.get(
        `/threeone/learned/${row.id}/${row.participantId}`,
        resp => {
          if (resp.status) {
            row.learned = resp.data
            this.learned.current.data = resp.data
            this.learned.current.show = true
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
    }
  }
})
