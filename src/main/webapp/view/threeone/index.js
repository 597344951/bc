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
    },
    label: {
      person: '三会一课参加记录',
      all: '三会一课会议记录'
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
    },
    participant: {
      show: false,
      list: []
    }
  },
  mounted() {
    if('life' == meetingType) {
      this.label.person = '组织生活会参加记录'
      this.label.all = '组织生活会会议记录'
    }
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
        return this.$message.error('请填写会议纪要或者上传会议纪要附件')
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
        return this.$message.error('请填写会议心得或者上传会议心得附件')
      }
      AJAX.postJson(
        '/threeone/learned',
        {
          scheduleId: this.learned.belongs.id,
          userId: this.learned.belongs.participantId,
          learned: this.learned.props.learned,
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
    },
    loadScheduleParticipant(row) {
      AJAX.get(
        `/threeone/participant/${row.id}`,
        resp => {
          if (resp.status) {
            this.participant.list = []
            this.participant.show = true
            resp.data.forEach(item => {
              item.isParticipate = '' + item.isParticipate
              this.participant.list.push(item)
            })
          } else {
            this.$message.error(resp.msg)
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    signAllScheduleParticipant() {
      this.participant.list.forEach(item => {
        item.isParticipate = '1'
      })
    },
    conmmitScheduleParticipantSign() {
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
            this.$message.error('签到信息更新失败：' + resp.msg)
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    }
  }
})
