window.onclose = function () {

  return true;
}

window.onFocus = function () {}

let routes = [{
  // 传递模版id, 子组件通过this.$route.params 获取
  path: '/category/:categoryId',
  name: '指定分类',
}]

let ins = new Vue({
  el: "#app",
  router: new VueRouter({
    routes: routes
  }),
  data: {
    categoryTree: [],
    categoryList: [],
    keyword: '',
    pager:{
      pageNum:1,
      pageSize:15,
      total:0,
    },
    lessons:[],
    currentLesson:{
      lessonList:[{creditHours:0}],
      lessonRegistration:[],
      lessonProgress:[],
    },
    lessonViewDialog:{
      visible:false
    },
    activeTabName:'descript',
  },
  mounted() {
    this.loadCategory()
    this.loadLessons()
  },
  computed: {
    nextCategory() {
      let route = this.$route
      //如果 第一次没加载 , 使用顶层
      if (route.path == '/') return this.categoryTree
      else {
        let category = this.currentCategory
        if (!category) return []
        let node = getDataNextItem(category, this.categoryTree, item => item.children, item => item.categoryId, item => item)
        //console.debug('下一级数据:', node)
        if (!node || !node.children) {
          //没有下一级数据,使用上一级
          node = getDataNextItem({
            categoryId: category.parent
          }, this.categoryTree, item => item.children, item => item.categoryId, item => item)
        }
        return node.children
      }
    },
    //分类面包导航
    categoryBreadPath() {
      let category = this.currentCategory
      if (!category) return []
      let bp = breadPath(category, this.categoryTree, item => item.children, item => item.parent, item => item.categoryId, item => item)
      //console.log('面包路径: ', bp)
      return bp
    },
    currentCategory() {
      let id = this.$route.params.categoryId
      let category = this.categoryList.find(ele => ele.categoryId == id)
      return category
    },
    labelDisName() {
      let category = this.currentCategory
      if (!category) return '学习方向'
      let node = getDataNextItem(category, this.categoryTree, item => item.children, item => item.categoryId, item => item)
      //console.debug('下一级数据:', node)
      if (!node || !node.children) {
        return '分类'
      } else {
        return '学习方向'
      }
    }
  },
  watch: {
    $route(val, old) {
      let category = this.categoryList.find(ele => ele.categoryId == val.params.categoryId)
      console.debug('目录变更为: ', category && category.name)
      this.loadLessons()
    }
  },
  methods: {
    loadCategory() {
      let url = '/lesson/category/category/tree'
      ajax_json_promise(url, 'get', null).then(result => {
        this.categoryTree = result.data
        this.categoryList = result.list
      })
    },
    //加载学习课题
    loadLessons() {
      let url = `/lesson/unit/unit/search/${this.pager.pageNum}-${this.pager.pageSize}`
      
      let data = {categoryId:this.$route.params.categoryId,keyword:this.keyword}
      ajax_json_promise(url,'post',data).then(result => {
        this.pager.total = result.pager.total
        this.lessons = result.data
      })
    },
    handleSizeChange(val){
      this.pager.pageSize = val
      this.loadLessons()
    },
    handleCurrentChange(val){
      this.pager.pageNum = val
      this.loadLessons()
    },
    sumTotalTime(lesson){
      if(!lesson.lessonList || lesson.lessonList.length == 0) return 0
      return lesson.lessonList.map(el=>el.creditHours).reduce((a,b)=>a+b)
    },
    openLessonUnit(lesson){
      this.currentLesson = lesson
      this.lessonViewDialog.visible = true
    },
    //报名课程
    joinLessonUnit(lesson){
      let url = `/lesson/regist/${lesson.lessonUnitId}`
      ajax_json_promise(url,'get','').then(result => {
        if(result.status){
          this.$message('报名成功')
          lesson.lessonRegistration.push({lessonUnitId:lesson.lessonUnitId})
        }
      })
    },
    //获取 学习进度
    getLearnProgress(lesson,progress){
      let p =  progress.find(obj => obj.lessonId == lesson.lessonId)
      if(p){
        let ps =  p.creditHours * 100 / lesson.creditHours
        return Math.ceil(ps) + '%'
      }
      return ''
    }
  }
});


window.appInstince = ins;