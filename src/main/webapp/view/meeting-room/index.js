const app = new Vue({
  el: '#app',
  data: {
    rooms: {
      list: [],
      pageNum: 1,
      pageSize: 20,
      total: 0
    },
    room: {
      show: false,
      source: null,
      props: {
        park: '',
        building: '',
        floor: '',
        number: '',
      },
      rules: {
        park: [{ required: true, message: '请输入园区名称', trigger: 'blur' }],
        building: [{ required: true, message: '请输入楼宇名称', trigger: 'blur' }],
        floor: [{ required: true, message: '请输入楼层信息', trigger: 'blur' }],
        number: [{ required: true, message: '请输入会议室编号', trigger: 'blur' }],
      }
    },
    orders: {
      show: false,
      list: []
    }
  },
  mounted() {
    this.loadMeetingRoom(this.rooms.pageNum, this.rooms.pageSize)
  },
  methods: {
    onCurrentChangeAtRooms(num) { },
    loadMeetingRoom(pageNum, pageSize) {
      AJAX.get(
        `/meeting/room/list/${pageNum}/${pageSize}`,
        resp => {
          if (resp.status) {
            this.rooms = resp.data
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    emptyRoom() {
      this.room.props = {
        park: '',
        building: '',
        floor: '',
        number: '',
      }
    },
    addRoom() {
      this.room.source = null
      this.emptyRoom()
      this.room.show = true
    },
    deleteRoom(row) {
      this.$confirm('该操作将会删除选择会议室, 确认提交?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        AJAX.get(
          `/meeting/room/delete/${row.id}`,
          resp => {
            if (resp.status) {
              this.$message({
                message: '移除成功...',
                type: 'success'
              })
              this.loadMeetingRoom(this.rooms.pageNum, this.rooms.pageSize)
            } else {
              this.$message.error('移除失败： ' + resp.msg)
            }
          },
          err => {
            this.$message.error('系统错误， 请联系管理员')
          }
        )
      }).catch(() => { })
    },
    editRoom(row) {
      this.room.source = row
      this.room.props = JSON.parse(JSON.stringify(row))
      this.room.show = true
    },
    resetRoomForm() {
      this.emptyRoom()
    },
    submitRoomForm() {
      this.$refs['roomForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认数据填写无误, 确认提交?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            let url = this.room.source ? '/meeting/room/update' : '/meeting/room/add'
            AJAX.postJson(
              url,
              this.room.props,
              resp => {
                if (resp.status) {
                  this.$message({
                    message: '提交成功...',
                    type: 'success'
                  })
                  this.room.show = false
                  this.loadMeetingRoom(this.rooms.pageNum, this.rooms.pageSize)
                  this.emptyRoom()
                } else {
                  this.$message.error('提交失败：' + resp.msg)
                }
              },
              err => {
                this.$message.error('系统错误， 请联系管理员')
              }
            )
          }).catch(() => { })
        } else {
          return false;
        }
      })
    },
    listOrder(row) {
      AJAX.get(
        `/meeting/room/${row.id}/orders`,
        resp => {
          if (resp.status) {
            this.orders.list = []
            resp.data.forEach(o => {
              this.orders.list.push({
                date: o.timeString,
                meeting: o.meetingName
              })
            })
          }
          this.orders.show = true
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    }
  }
})
