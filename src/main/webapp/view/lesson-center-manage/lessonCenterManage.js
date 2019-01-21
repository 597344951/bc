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
        orderNum: 0,
      }
    },
    lessonUnitDialog: {
      title: '新增加课程',
      visible: false,
      mode: 'insert', //insert,update
      data: {
        name: '',
        testThreshold: 0,
        creditHoursPercent: 50,
        learnerLimit: 0,
      }
    },
    initBaseLessonUnitData: {
      name: '',
      testThreshold: 0,
      creditHoursPercent: 50,
      learnerLimit: 0,
    },
    initBaseCategoryData: {
      parent: 0,
      orderNum: 0,
    },
    lessons: [],
    pager: {
      pageNum: 1,
      pageSize: 15,
      total: 0,
    },
    orgTreeData: [],
    orgListData: [],
    orgTreeProps: {
      label: 'orgInfoName',
      children: 'children'
    },
    lessonCategoryTreeDefaultExpanded: [],
    orgLearnLimitDialog: {
      visible: false,
      defaultExpanded: [], //默认展开
      defaultCheckedKeys: [], //默认选择
    },
    lessonSectionDialog: {
      visible: false,
      lessonTree: [],
    },
    lessonSectionEditorDialog: {
      visible: false,
      title: '新增章节',
      mode: 'insert', //insert,update
      lessons: [],
      data: {
        sourceType: 2,
        sourceData: '',
        name: '',
        creditHours: 60,
        descript: '',
        sortNum: 0,
        parent: null,
      }
    },
    initBaseLessonSectionData: {
      sourceType: 2,
      sourceData: '',
      name: '',
      creditHours: 60,
      descript: '',
      sortNum: 0,
      parent: null,
    },
    materialExplorer: {
      visible: false,
      selecteds: []
    },
    lessonUnitTestDialog: {
      visible: false,
      testTypes: [],
    },
    lessonUnitTestEditorDialog: {
      visible: false,
      title: '新增章节',
      mode: 'insert', //insert,update
      lessons: [],
      data: {
        sourceType: 2,
        sourceData: '',
        name: '',
        creditHours: 60,
        descript: '',
        sortNum: 0,
        parent: null,
      }
    },
    initLessonUnitTestData: {
      testType: 1,
      name: '',
      descript: '',
    }
  },
  mounted() {
    this.loadCategory()
    this.loadLessons()
    this.loadOrgTree()
  },
  computed: {
    allCategoryTree() {
      return [{
        name: 'ROOT',
        categoryId: 0,
        children: this.categoryTree
      }]
    },
    parentLessonSections() {
      let lessons = this.lessonSectionEditorDialog.lessons
      if (this.lessonSectionEditorDialog.data.sourceType != 2) {
        //非包情况 列出第一级数据
        return lessons.map(e => {
          return {
            name: e.name,
            lessonId: e.lessonId
          }
        })
      }
      return lessons
    }
  },
  watch: {},
  methods: {
    lessonUnitDialogOpened() {
      if (!this._editor) {
        setTimeout(() => {
          var _editor = UE.getEditor("templateText", {})
          this._editor = _editor
        }, 0)
      }


    },
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
      this.loadLessons()
    },
    //加载学习课题
    loadLessons() {
      let url = `/lesson/unit/unit/manage/${this.pager.pageNum}-${this.pager.pageSize}`
      let node = this.$refs.tree.getCurrentNode()
      let categoryId = node ? node.categoryId : null
      let data = {
        categoryId: categoryId,
        keyword: this.keyword
      }
      ajax_json_promise(url, 'post', data).then(result => {
        this.pager.total = result.pager.total
        this.lessons = result.data

        this.reloadLessonSection()
        this.reloadLessonUnitTest()
      })
    },
    // 重新加载 当前编辑的课程章节信息
    reloadLessonSection() {
      let ls = this.lessons.find(e => e.lessonUnitId == this.lessonSectionDialog.lessonUnitId)
      if (!ls) return
      this.lessonSectionInit(ls,true)
    },
    reloadLessonUnitTest(){
      let ls = this.lessons.find(e => e.lessonUnitId == this.lessonUnitTestEditorDialog.lessonUnitId)
      if (!ls) return
      this.lessonUnitTestInit(ls,true)
    },
    handleSizeChange(val) {
      this.pager.pageSize = val
      this.loadLessons()
    },
    handleCurrentChange(val) {
      this.pager.pageNum = val
      this.loadLessons()
    },
    sumTotalTime(lesson) {
      if (lesson.lessonList.length == 0) return 0
      return lesson.lessonList.map(el => el.creditHours).reduce((a, b) => a + b)
    },
    loadOrgTree() {
      let url = '/org/ifmt/queryOrgInfosToTree'
      ajax_promise(url, 'post', {}).then(result => {
        console.log(result)
        this.orgTreeData = result.normalTree
        this.orgListData = result.list
      })
    },
    saveOrUpdateLessonUnit() {
      let mode = this.lessonUnitDialog.mode
      let data = this.lessonUnitDialog.data
      let node = this.$refs.lessonCategoryTree.getCurrentNode()
      if (!node) {
        this.$message({
          type: 'warning',
          message: '请选择所属目录'
        })
        return
      }
      data.categoryId = node.categoryId
      if (!this._editor.hasContents()) {
        this.$message({
          type: 'warning',
          message: '请输入课程描述'
        })
        return;
      }
      data.descript = this._editor.getContent()
      console.debug('lessonUnit: ', data)

      let url = '/lesson/unit/unit'
      if (mode == 'insert') {
        ajax_json_promise(url, 'post', data).then(result => {
          if (result.status) {
            this.$message.success({
              message: result.msg
            })
            this.resetLessonUnitInfo()
            this.loadLessons()
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
            this.resetLessonUnitInfo()
            this.loadLessons()
          } else {
            this.$message.error({
              message: result.msg
            })
          }
        })
      }
    },
    resetLessonUnitInfo() {
      this.lessonUnitDialog.visible = false
      this.lessonUnitDialog.data = this.initBaseLessonUnitData
    },
    editLessonUnit(data) {
      this.lessonUnitDialog.visible = true
      this.lessonUnitDialog.title = '修改课程信息'
      this.lessonUnitDialog.mode = 'update'
      this.lessonUnitDialog.data = data
      setTimeout(() => {
        this._editor.setContent(data.descript)
        this.$refs.lessonCategoryTree.setCurrentKey(data.categoryId)
        this.lessonCategoryTreeDefaultExpanded = [data.categoryId]
      }, 800);
    },
    deleteLessonUnit(data) {
      let url = `/lesson/unit/unit/${data.lessonUnitId}`
      this.$confirm('是否删除课程', '提示', {
        type: 'danger'
      }).then(() => {
        ajax_json_promise(url, 'delete').then(result => {
          if (result.status) {
            this.$message.success({
              message: result.msg
            })
            this.loadLessons()
          } else {
            this.$message.error({
              message: result.msg
            })
          }
        })
      })
    },
    orgLearnLimitInit(lesson) {
      console.debug('lesson: ', lesson)
      this.orgLearnLimitDialog.visible = true
      this.orgLearnLimitDialog.lessonUnitId = lesson.lessonUnitId
      setTimeout(() => {
        this.$refs.orgTree.setCheckedKeys(lesson.allowOrgIds.map(e => e.orgId))
      }, 0);

    },
    saveLearnLimit() {
      let lessonUnitId = this.orgLearnLimitDialog.lessonUnitId
      let keys = this.$refs.orgTree.getCheckedKeys()
      if (keys.length == 0) {
        this.$message({
          type: 'warning',
          message: '请选择限定组织'
        })
        return
      }
      let datas = keys.map(e => {
        return {
          lessonUnitId: lessonUnitId,
          orgId: e
        }
      })
      console.log('设置限定组织: ', datas)
      let url = '/lesson/learn-limit/config'
      ajax_json_promise(url, 'post', datas).then(result => {
        if (result.status) {
          this.$message.success({
            message: result.msg
          })
          this.loadLessons()
          this.orgLearnLimitDialog.visible = false
        }
      })
    },
    lessonSectionInit(lesson,ifnoreVisible) {
      if(!ifnoreVisible) this.lessonSectionDialog.visible = true
      this.lessonSectionDialog.lessonUnitId = lesson.lessonUnitId
      this.lessonSectionDialog.lessonTree = lesson.lessonTree
      this.lessonSectionDialog.lessonUnit = lesson
    },
    newSection(lessonUnit, lesson) {
      this.resetLessonSectionInfo()
      // 自动 推测相关参数
      // 如果是当前是目录，则设置添加默认也是目录
      if (lesson.sourceType == 2) {
        this.lessonSectionEditorDialog.data.sourceType = 2
        this.lessonSectionEditorDialog.data.parent = 0
        this.lessonSectionEditorDialog.data.sortNum = lessonUnit.lessonTree[lessonUnit.lessonTree.length - 1].sortNum + 1
      } else if (lesson.sourceType != 2) {
        // 不是目录
        this.lessonSectionEditorDialog.data.sourceType = lesson.sourceType
        this.lessonSectionEditorDialog.data.parent = lesson.parent
        this.lessonSectionEditorDialog.data.sortNum = lesson.sortNum + 1
      }

      this.lessonSectionEditorDialog.visible = true
      this.lessonSectionEditorDialog.mode = 'insert'
      this.lessonSectionEditorDialog.title = '新增课程章节'
      this.lessonSectionEditorDialog.lessons = lessonUnit.lessonTree
      this.lessonSectionEditorDialog.lessonUnitId = lessonUnit.lessonUnitId
    },
    updateSection(lessonUnit, lesson) {
      this.resetLessonSectionInfo()
      this.lessonSectionEditorDialog.visible = true
      this.lessonSectionEditorDialog.mode = 'update'
      this.lessonSectionEditorDialog.title = '修改课程章节'
      this.lessonSectionEditorDialog.lessons = lessonUnit.lessonTree
      this.lessonSectionEditorDialog.lessonUnitId = lessonUnit.lessonUnitId
      this.lessonSectionEditorDialog.data = lesson
    },
    saveOrUpdateLessonSection() {
      let mode = this.lessonSectionEditorDialog.mode
      let data = this.lessonSectionEditorDialog.data
      data.lessonUnitId = this.lessonSectionEditorDialog.lessonUnitId
      if (data.sourceType == 2) {
        data.creditHours = 0
        data.parent = 0
      }
      if (data.sourceType == 0) {
        let ss = this.materialExplorer.selecteds
        if (!ss || ss.length == 0) {
          this.$message({
            type: 'warning',
            message: '请选择素材'
          })
          return
        }
        data.sourceData = ss[0].materialId
      }

      console.log(mode, '', data)
      let url = '/lesson/section/section'
      if (mode == 'insert') {
        ajax_json_promise(url, 'post', data).then(result => {
          if (result.status) {
            this.$message.success({
              message: result.msg
            })
            this.resetLessonSectionInfo()
            this.loadLessons()
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
            this.resetLessonSectionInfo()
            this.loadLessons()
          } else {
            this.$message.error({
              message: result.msg
            })
          }
        })
      }
    },
    resetLessonSectionInfo() {
      this.lessonSectionEditorDialog.visible = false
      this.lessonSectionEditorDialog.data = this.initBaseLessonSectionData
    },
    deleteSection(lesson) {
      this.$confirm('是否删除此章节', '提示', {
        type: 'danger'
      }).then(() => {
        let url = `/lesson/section/section/${lesson.lessonId}`
        ajax_json_promise(url, 'delete').then(result => {
          if (result.status) {
            this.$message.success({
              message: result.msg
            })
            this.loadLessons()
          }
        })
      })
    },
    materialSelected() {

    },
    lessonUnitTestInit(lesson,ifnoreVisible) {
      if(!ifnoreVisible) this.lessonUnitTestDialog.visible = true
      this.lessonUnitTestDialog.testTypes = lesson.testTypes
      this.lessonUnitTestDialog.lessonUnitId = lesson.lessonUnitId

    },
    //新考核
    newLessonUnitTest() {
      this.resetLessonUnitTest()
      this.lessonUnitTestEditorDialog.visible = true
      this.lessonUnitTestEditorDialog.mode = 'insert'
      this.lessonUnitTestEditorDialog.title = '新增考核方式'
      this.lessonUnitTestEditorDialog.lessonUnitId = this.lessonUnitTestDialog.lessonUnitId
    },
    updateLessonUnitTest(test) {
      this.lessonUnitTestEditorDialog.visible = true
      this.lessonUnitTestEditorDialog.mode = 'update'
      this.lessonUnitTestEditorDialog.title = '修改考核方式'
      this.lessonUnitTestEditorDialog.lessonUnitId = test.lessonUnitId
      this.lessonUnitTestEditorDialog.data = test
    },
    saveOrUpdateLessonUnitTest() {
      let mode = this.lessonUnitTestEditorDialog.mode
      let data = this.lessonUnitTestEditorDialog.data
      data.lessonUnitId = this.lessonUnitTestEditorDialog.lessonUnitId

      console.log('test type: ', data)
      let url = '/lesson/test/test'
      if (mode == 'insert') {
        ajax_json_promise(url, 'post', data).then(result => {
          if (result.status) {
            this.$message.success({
              message: result.msg
            })
            this.resetLessonUnitTest()
            this.loadLessons()
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
            this.resetLessonUnitTest()
            this.loadLessons()
          } else {
            this.$message.error({
              message: result.msg
            })
          }
        })
      }
    },
    deleteLessonUnitTest(test) {
      this.$confirm('是否删除此考核方式', '提示', {
        type: 'danger'
      }).then(() => {
        let url = `/lesson/test/test/${test.testId}`
        ajax_json_promise(url, 'delete').then(result => {
          if (result.status) {
            this.$message.success({
              message: result.msg
            })
            this.loadLessons()
          }
        })
      })
    },
    resetLessonUnitTest() {
      this.lessonUnitTestEditorDialog.data = this.initLessonUnitTestData
      this.lessonUnitTestEditorDialog.visible = false
    }


  }
});


window.appInstince = ins;