const app = new Vue({
  el: '#app',
  data: {
    groupItem: {
      show: false,
      props: { label: '', descripton: '' },
      rules: {
        label: [{ required: true, message: '请输入分组名称', trigger: 'blur' }],
        descripton: [
          { required: true, message: '请输入分组描述', trigger: 'blur' }
        ]
      },
      operateNode: null,
      isRoot: false
    },
    group: [],
    terminals: {
      screenType: {
        '1': '一体机',
        '2': '播放盒+显示屏',
        '3': '播放盒+投影仪'
      },
      addSelectShow: false,
      selected: [],
      all: [],
      piece: [],
      orgListData:[],
    }
  },
  mounted() {
    this.loadGroup()
    this.loadTerminals()
    this.loadOrgTree()
  },
  methods: {
    loadOrgTree() {
      let url = '/org/ifmt/queryOrgInfosToTree'
      ajax_promise(url, 'post', {}).then(result => {
        console.log(result)
        this.orgListData = result.list
      })
    },
    addGroupItem() {
      this.$refs.groupItemForm.validate(valid => {
        if (valid) {
          let parent = this.groupItem.isRoot ? null : this.groupItem.operateNode
          let item = {
            label: this.groupItem.props.label,
            description: this.groupItem.props.descripton,
            parentId: parent ? parent.data.id : 0
          }
          AJAX.postJson(
            '/terminal/m/group/add',
            item,
            resp => {
              if (resp.status) {
                this.$message({
                  message: '添加成功...',
                  type: 'success'
                })
                if (parent) {
                  if (!parent.data.children) {
                    this.$set(parent.data, 'children', [])
                  }
                  parent.data.children.push(resp.data)
                } else {
                  this.group.push(resp.data)
                }
                this.groupItem.show = false
                this.resetForm('groupItemForm')
                this.groupItem.operateNode = null
                this.isRoot = false
              } else {
                this.$message.error('添加失败：' + resp.msg)
              }
            },
            err => {
              this.$message.error('系统错误， 请联系管理员')
            }
          )
        } else {
          this.$message.error('参数填写有误, 请检查填写参数.')
          return false
        }
      })
    },
    deleteGroupItem(node, data) {
      AJAX.postJson(
        '/terminal/m/group/delete',
        this.getChildIds(data),
        resp => {
          if (resp.status) {
            this.$message({
              message: '移除成功...',
              type: 'success'
            })
            let parent = node.parent
            let children = parent.data.children || parent.data
            let index = children.findIndex(d => d.id === data.id)
            children.splice(index, 1)
            this.groupItem.operateNode = null
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    loadGroup() {
      AJAX.get(
        '/terminal/m/group/tree',
        resp => {
          if (resp.status) {
            this.group = resp.data
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    loadTerminals() {
      AJAX.postJson(
        '/terminal/basic/queryInfo/1-2000000000',
        {},
        resp => {
          if (resp.status) {
            this.terminals.all = resp.data.list
          } else {
            this.$message.error(msg)
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    onTerminalSelected(selected) {
      this.terminals.selected = selected
    },
    addTerminalToGroup() {
      if(!this.groupItem.operateNode) this.$message.error('没有选择进行添加的分组!!!')
      if(this.terminals.selected.length <= 0) this.$message.error('没有选择需要添加的终端!!!')
      let postData = {
        group: this.groupItem.operateNode.data,
        terminals: this.terminals.selected
      }
      AJAX.postJson(
        '/terminal/m/group/addTerminals',
        postData,
        resp => {
          if (resp.status) {
            this.$message({
              message: '添加成功...',
              type: 'success'
            })
            this.terminals.addSelectShow = false
            this.terminals.selected = []
            this.$refs.terminalSelectTable.clearSelection()
            this.loadGroupTerminals(this.groupItem.operateNode.data)
          } else {
            this.$message.error('添加失败：' + resp.msg)
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    deleteTerminalFromGroup(terminal) {
      AJAX.get(
        `/terminal/m/group/removeTerminal/${terminal.groupId}/${terminal.oid}`,
        resp => {
          if (resp.status) {
            this.$message({
              message: '移除成功...',
              type: 'success'
            })
            this.loadGroupTerminals(this.groupItem.operateNode.data)
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    onNodeClick(data, node, component) {
      this.loadGroupTerminals(data)
      this.groupItem.operateNode = node
    },
    loadGroupTerminals(group) {
      let ids = this.getChildIds(group)
      AJAX.postJson(
        '/terminal/m/group/terminals',
        ids,
        resp => {
          if (resp.status) {
            this.terminals.piece = resp.data
          } else {
            this.$message.error(msg)
          }
        },
        err => {
          this.$message.error('系统错误， 请联系管理员')
        }
      )
    },
    getChildIds(group) {
      let ids = []
      ids.push(group.id)
      if(group.children && group.children.length > 0) {
        for(let i in group.children) {
          ids = ids.concat(this.getChildIds(group.children[i]))
        }
      }
      return ids
    },
    getOrgInfo(t){
       let oi = this.orgListData.find(ele => ele.orgInfoId == t.org_id)
       if(oi)return oi.orgInfoName
       return '未配置所属组织'
    }

  }
})
