const app = new Vue({
  el: '#app',
  data: {
    meetingType: meetingType,
    scheduleList: [],
    scheduleAddFormShow: false,
    memberSelectShow: false,
    schedule: {
      startTime: '',
      endTime: '',
      name: '',
      description: '',
      place: '',
      type: '',
      state: 0,
      members: []
    },
    scheduleRule: {
      name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
      type: [{ required: true, message: '请选择会议类型', trigger: 'blur' }],
      startTime: [
        { required: true, message: '请输入开始时间', trigger: 'blur' }
      ],
      endTime: [{ required: true, message: '请输入结束时间', trigger: 'blur' }],
      description: [
        { required: true, message: '请输入描述信息', trigger: 'blur' }
      ],
      place: [{ required: true, message: '请输入场地信息', trigger: 'blur' }],
      members: [{ required: true, message: '请添加参加人员', trigger: 'blur' }]
    },
    operate: 'add',
    member: {
      list: []
    }
  },
  mounted() {
    this.loadEnableSchedule()
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let url =
            'update' == this.operate
              ? '/threeone/schedule/update'
              : '/threeone/schedule/add'
          AJAX.postJson(
            url,
            this.schedule,
            resp => {
              if (resp.status) {
                this.$message({
                  message: '提交成功...',
                  type: 'success'
                })
                this.scheduleAddFormShow = false
                this.$refs[formName].resetFields()
                this.loadEnableSchedule()
              } else {
                this.$message.error('提交日程失败：' + resp.msg)
              }
            },
            err => {
              this.$message.error('系统错误， 请联系管理员')
            }
          )
        } else {
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.schedule.members = []
    },
    addSchedule(_schedule) {
      let startTime = new Date(_schedule.startTime)
      let year = startTime.getFullYear()
      let month = startTime.getMonth() + 1
      let day = startTime.getDate()

      let yHad = false,
        dHad = false
      if (!this.scheduleList) this.scheduleList = []
      for (let i in this.scheduleList) {
        let schedule = this.scheduleList[i]
        if (!schedule.eventList) schedule.eventList = []
        if (schedule.year == year) {
          yHad = true
          for (let j in schedule.eventList) {
            let event = schedule.eventList[j]
            if (!event.eventList) event.eventList = []
            if (event.month == month && event.day == day) {
              dHad = true
              event.eventList.push(_schedule)
              break
            }
          }
          if (!dHad) {
            schedule.eventList.push({
              month: month,
              day: day,
              eventList: [_schedule]
            })
          }
          break
        }
      }
      if (!yHad) {
        this.scheduleList.push({
          year: year,
          eventList: [
            {
              month: month,
              day: day,
              eventList: [_schedule]
            }
          ]
        })
      }
    },
    onChoose(schedule) {
      let share = [{weburl: '/threeone/schedule/notice/' + schedule.event.id, playtime: 60}]
      let url = '/view/publish/new.jsp?title=三会一课通知&startStep=2&url=' + encodeURIComponent(JSON.stringify(share))
      if (parent.addTab) {
        parent.addTab({ menuId: -6, name: '三会一课通知', url: url })
      } else {
        window.location.href = url
      }
    },
    onEdit(schedule) {
      this.operate = 'update'
      this.schedule = schedule.event
      this.scheduleAddFormShow = true
    },
    onDelete(schedule) {
      AJAX.get(
        '/threeone/schedule/delete/' + schedule.event.id,
        resp => {
          if (resp.status) {
            this.$message({
              message: '移除成功...',
              type: 'success'
            })
            this.loadEnableSchedule()
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    loadEnableSchedule() {
      AJAX.get(
        `/threeone/schedule/?meetingType=${this.meetingType}`,
        resp => {
          if (resp.status) {
            this.scheduleList = []
            resp.data.forEach(item => {
              this.addSchedule(item)
            })
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    onMemberSelect(members) {
      this.schedule.members = members
    },
    onMemberRemove(member) {
      this.schedule.members.splice(this.schedule.members.indexOf(member), 1)
      this.$refs.memberSelectTable.toggleRowSelection(member)
    },
    loadOrgMembers() {
      if(this.member.list && this.member.list.length > 0) {
        this.memberSelectShow = true
        return
      }
      AJAX.get(
        '/threeone/schedule/member/org',
        resp => {
          if (resp.status) {
            this.member.list = resp.data
            this.memberSelectShow = true
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    }
  }
})
