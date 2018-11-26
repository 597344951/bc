window.onFocus = function () {
	console.debug(' 页面获取得焦点');
}

let ins = new Vue({
	el: "#app",
	components: {
		'waterfall': Waterfall.waterfall,
		'waterfall-slot': Waterfall.waterfallSlot
	},
	data: {
		posterBaseInfo: {
			visible: false,
			data: {
				title: '',
				useCategorys: [],
			}
		},
		tpager: {
			total: 0,
			current: 1,
			size: 30
		},
		posterSizes: [],
		filter: {
			h_v: 'all',
			sizeType: -1,
			title: 'all',
			keyword: '',
			category: -1,
			useCategory: 0, //过滤用途分类
			useCategory2: 0
		},
		posterInfos: [],
		category: [], //行业分类
		useCategory: [], //用途分类
		totalCategory: [], // 总分类数据
		categoryTree: [], // 分类树
		defaultProps: {
			children: 'children',
			label: 'name'
		},
		posterCategoryDialog: {
			title: '',
			visible: false,
			mode: '', //insert,update
			data: {}
		},
		editPosterCategoryVisible: false,
		replaceMetaDataDialog:{
			visible:false,
			data:{
				search:'',rep:''
			}
		},
		searchedMetaData:[]
	},
	mounted() {
		this.loadPosterSize()
		this.loadPosterInfo()
		this.loadCategory()
	},
	computed: {
		filterPosterSize() {
			if (this.filter.h_v == 'all') {
				return this.posterSizes
			} else {
				this.filter.sizeType = -1
				return this.posterSizes.filter(e => e.horVer == this.filter.h_v)
			}
		},
		displayType() {
			let r = 'v';
			let sizeType = this.filter.sizeType;
			let h_v = this.filter.h_v;
			if (sizeType == -1) {
				if (h_v != 'all') return h_v
			} else {
				let dt = this.posterSizes.find(el => el.id == sizeType).horVer
				return dt || r
			}
			console.log(this.filter.sizeType, this.filter.h_v)
			return r;
		},
		secondCategory() {
			let category = this.totalCategory.find(Element => Element.categoryId == this.filter.useCategory)
			if (!category || this.filter.useCategory == 0) return []
			let ay = this.totalCategory.filter(Element => Element.parent == category.categoryId && Element.display)
			return ay
		}
	},
	watch: {
		"filter.sizeType": function (val) {
			if (val == -1) return;
			let dt = this.filterPosterSize.find(el => el.id == val)
			this.filter.h_v = dt.horVer
		},
		"filter.useCategory": function (val) {
			if (this.secondCategory && this.secondCategory.length > 0) {
				this.filter.useCategory2 = this.secondCategory[0].categoryId
			}
		}
	},
	methods: {
		loadCategory() {
			let url = '/poster/category/search'
			let data = {}
			ajax_json_promise(url, 'post', data).then(result => {
				let data = result.data
				this.categoryTree = FormatInputData(result.treeData)
				this.totalCategory = data
				this.useCategory = data.filter(el => el.type == 2 && el.display)
			})
		},
		loadPosterSize() {
			let url = '/poster/postersize/search';
			let data = {}
			ajax_json_promise(url, 'post', data).then(result => {
				this.posterSizes = result.data
			})
		},
		loadPosterInfo() {
			let pageNum = this.tpager.current
			let pageSize = this.tpager.size
			let url = `/poster/posterinfo/search/${pageNum}-${pageSize}`
			let data = {
				sizeType: this.filter.sizeType != -1 ? this.filter.sizeType : null,
				keyword: this.filter.keyword ? this.filter.keyword : null,
				category: this.filter.category != -1 ? this.filter.category : null,
			}
			if (this.filter.useCategory != 0) {
				if (this.filter.useCategory != -1 && this.filter.useCategory2 != 0) {
					data.useCategory = this.filter.useCategory2
				} else {
					data.useCategory = this.filter.useCategory
				}
			} else {
				delete data.useCategory
			}

			if (this.filter.h_v != 'all') {
				data.posterSize = {}
				data.posterSize.horVer = this.filter.h_v
				if (this.filter.sizeType != -1) {
					data.posterSize.id = this.filter.sizeType
				}
			}
			ajax_json_promise(url, 'post', data).then(result => {
				let pager = result.pager
				this.posterInfos = result.data
				this.tpager.total = pager.total
			})
		},
		handleSizeChange(val) {
			this.tpager.size = val
			this.tpager.current = 1
			this.loadPosterInfo()
		},
		handleCurrentChange(val) {
			this.tpager.current = val
			this.loadPosterInfo()
		},
		v_or_h(tp) {
			return tp.width > tp.height ? true : false
		},
		viewPT(tp) {
			let url = '/media-server/poster/view/' + tp.templateId
			//openwindow(url,tp.title)
			window.open(url, '_blank');
		},
		editPT(tp) {
			let url = '/media-server/poster/edit/' + tp.templateId
			//openwindow(url,tp.title)
			window.open(url, '_blank');
		},
		fromPT(tp) {
			let url = '/media-server/poster/from/' + tp.templateId
			window.open(url, '_blank');
		},
		editBaseInfo(tp) {
			this.posterBaseInfo.data = tp
			this.posterBaseInfo.visible = true
			if (!this.posterBaseInfo.data.useCategorys) this.posterBaseInfo.data.useCategorys = []
			setTimeout(() => {
				this.$refs.tree.setCheckedKeys(this.posterBaseInfo.data.useCategorys)
			}, 200);
		},
		saveBaseInfo() {
			if (!this.posterBaseInfo.data.templateId) return
			let url = '/poster/posterinfo'
			delete this.posterBaseInfo.data.layouts
			this.posterBaseInfo.data.useCategorys = this.$refs.tree.getCheckedKeys()
			ajax_json_promise(url, 'put', this.posterBaseInfo.data).then(result => {
				if (result.status) {
					this.$message.success({
						message: result.msg
					})
					this.posterBaseInfo.visible = false
				} else {
					this.$message.error({
						message: result.msg
					})
				}
			})
		},
		deletePT(tp) {
			console.debug('delete', tp)
			this.$confirm('是否删除该模版?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				let templateId = tp.templateId
				let url = `/poster/posterinfo/${templateId}`
				ajax_json_promise(url, 'delete').then(result => {
					if (result.status) {
						this.$message.success({
							message: result.msg
						})
						this.filterDelete(templateId)
					} else {
						this.$message.error({
							message: result.msg
						})
					}
				})
			})
		},
		/**过滤已删除数据**/
		filterDelete(templateId) {
			this.posterInfos = this.posterInfos.filter(e => e.templateId != templateId);
			if (this.posterInfos.length == 0)
				this.loadPosterInfo()
		},
		doSearch() {
			this.tpager.current = 1
			this.loadPosterInfo()
		},
		newPoster() {
			let url = '/media-server/poster/new/'
			//openwindow(url,tp.title)
			window.open(url, '_blank');
		},
		newPosterCategory(category) {
			event.stopPropagation()
			this.posterCategoryDialog.title = '新增海报分类'
			this.posterCategoryDialog.mode = 'insert'
			this.posterCategoryDialog.visible = true
			if(category.parent != 0){
				this.posterCategoryDialog.data.parent = category.parent
			}else{
				this.posterCategoryDialog.data.parent = category.categoryId
			}
		},
		editPosterCategory(category) {
			event.stopPropagation()
			this.posterCategoryDialog.title = '修改海报分类'
			this.posterCategoryDialog.mode = 'update'
			this.posterCategoryDialog.visible = true
			this.posterCategoryDialog.data = category

		},
		deletePosterCategory(category) {
			event.stopPropagation()
			if(categoryv.children && category.children.length > 0){
				this.$message({
					message: '请先删除子分类信息',
					type: 'warning'
				  });
				return
			}
			this.$confirm('是否删除分类信息', '提示', {
				type: 'danger'
			}).then(() => {
				let url = '/poster/category/' + category.categoryId
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
		resetCategoryInfo() {
			this.posterCategoryDialog.visible = false
			this.posterCategoryDialog.data = {}
		},
		saveOrUpdateCategory() {
			let mode = this.posterCategoryDialog.mode
			let data = this.posterCategoryDialog.data
			if (data.parent == 0) {
				data.type = 2
			} else {
				data.type = 3
			}
			let url = '/poster/category'
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
		searchMetaData(){
			let url = '/poster/posterinfo/searchMetaData/1-99';
			let data = this.replaceMetaDataDialog.data
			ajax_json_promise(url,'post',data).then(result => {
				this.searchedMetaData = result.data
			})
		},
		replaceMetaData(){
			let url = '/poster/posterinfo/replaceMetaData'
			let data = this.replaceMetaDataDialog.data
			ajax_json_promise(url,'post',data).then(result => {
				this.$message({message:result.msg})
			})
		}
	}
});

window.appInstince = ins;