window.onclose = function () {

  return true;
}

window.onFocus = function () {}



let ins = new Vue({
  el: "#app",
  data: {
    editCategoryVisible: false,
    categoryTree: [], // 分类树
    defaultProps: {
      children: 'children',
      label: 'name'
    },
    posterCategoryDialog: {
      title: '',
      visible: false,
      mode: '', //insert,update
      data: {
        parent: 0,
        orderNum:0,
      }
    },
    initBaseCategoryData:{
      parent:0,
      orderNum:0,
		},

  },
  mounted() {
    this.loadCategory()
  },
  computed: {
    allCategoryTree() {
      return [{
        name: 'ROOT',
        categoryId: 0,
        children: this.categoryTree
      }]
    }
  },
  watch: {},
  methods: {
    loadCategory() {
      ajax_promise('/lesson/category/category/tree', 'get').then(result => {
        this.categoryTree = result.data
      })
    },
    newCategory(category) {
      event.stopPropagation()
      this.posterCategoryDialog.title = '新增分类'
      this.posterCategoryDialog.mode = 'insert'
      this.posterCategoryDialog.visible = true
      this.posterCategoryDialog.data = {}
      if (category.parent != 0) {
        this.posterCategoryDialog.data.parent = category.parent
      } else {
        this.posterCategoryDialog.data.parent = category.categoryId
      }
    },
    editCategory(category) {
      event.stopPropagation()
      this.posterCategoryDialog.title = '修改分类'
      this.posterCategoryDialog.mode = 'update'
      this.posterCategoryDialog.visible = true
      this.posterCategoryDialog.data = category

    },
    deleteCategory(category) {
      event.stopPropagation()
      if (category.children && category.children.length > 0) {
        this.$message({
          message: '请先删除子分类信息',
          type: 'warning'
        });
        return
      }
      this.$confirm('是否删除分类信息', '提示', {
        type: 'danger'
      }).then(() => {
        let url = '/lesson/category/category/' + category.categoryId
        ajax_json_promise(url, 'delete', {}).then(result => {
          if (result.status) {
            this.$message.success({
              message: result.msg
            })
            this.resetCategoryInfo()
            this.loadCategory()
          } else {
            this.$message.error({
              message: result.msg
            })
          }
        })
      })
    },
    saveOrUpdateCategory() {
      let mode = this.posterCategoryDialog.mode
      let data = this.posterCategoryDialog.data
      let node = this.$refs.categoryTree.getCurrentNode()
      if (!node) {
        this.$message({
          type: 'warning',
          message: '请选择上一级节点'
        })
        return
      }
      data.parent = node.categoryId
      let url = '/lesson/category/category'
      if (mode == 'insert') {
        ajax_json_promise(url, 'post', data).then(result => {
          if (result.status) {
            this.$message.success({
              message: result.msg
            })
            this.resetCategoryInfo()
            this.loadCategory()
          } else {
            this.$message.error({
              message: result.msg
            })
          }
        })
      } else if (mode == 'update') {
        ajax_json_promise(url, 'put', data).then(result => {
          if (result.status) {
            this.$message.success({
              message: result.msg
            })
            this.resetCategoryInfo()
            this.loadCategory()
          } else {
            this.$message.error({
              message: result.msg
            })
          }
        })
      }
    },
    resetCategoryInfo() {
			this.posterCategoryDialog.visible = false
			this.posterCategoryDialog.data = this.initBaseCategoryData
    },
     // 类别点击
     tptTreeClick: function (_data, node) {
    },
  }
});


window.appInstince = ins;