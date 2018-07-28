<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">

<title>菜单管理  </title>
<%@include file="/include/head.jsp"%>
<style>
    .padding-0 {
        padding-left:5px;
    }

    .padding-1 {
        padding-left: 30px !important;
    }

    .padding-2 {
        padding-left: 55px !important;
    }

    .hand {
        cursor: pointer;
    }
	.mytable>thead>tr>:nth-child(5),.mytable>thead>tr>:nth-child(6),.mytable>thead>tr>:nth-child(7),
	.mytable>tbody>tr>:nth-child(5),.mytable>tbody>tr>:nth-child(6),.mytable>tbody>tr>:nth-child(7){
		text-align: center;
	}
	.row-type-0{
		 
	}
	.row-type-1{
		 
	}
	.row-type-2{
		 
	}
</style>
</head>

<body>
    <el-container id="app">
        <el-header>
            <el-row class="toolbar">
                <el-col :span="20">
                    <div class="grid-content bg-purple">
                        <el-button type="success" icon="el-icon-plus" @click="addMenu">新增菜单</el-button>
                        <el-button type="primary" icon="el-icon-edit" @click="updateMenu">修改菜单</el-button>
                        <el-button type="danger" icon="el-icon-delete" @click="deleteMenuItem">删除菜单</el-button>
                    </div>
                </el-col>
            </el-row>
        </el-header>
        <el-main>
            <table class="mytable table table-hover treegrid-table table-bordered table-striped" id="menuTable" data-click-to-select="true" data-mobile-responsive="true">
                <thead class="treegrid-thead">
                    <tr>
                        <th style="width:36px"></th>
                        <th style="padding:10px;width:80px">菜单ID</th>
                        <th style="padding:10px;width:300px">菜单名称</th>
                        <th style="padding:10px;width:100px">上级菜单</th>
                        <th style="padding:10px;width:50px">图标</th>
                        <th style="padding:10px;width:50px">类型</th>
                        <th style="padding:10px;width:50px">排序</th>
                        <th style="padding:10px;">菜单URL</th>
						<th style="padding:10px;">授权标识</th>
						<th style="padding:10px;width:200px;">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <!--一级目录-->
                    <tr v-for="(item,index) in menuData" v-show="item.open || item.data.type == 0" :class="'row-type-'+item.data.type" v-on:click="menuItemClick(item)">
                        <td style="text-align:center;width:36px">
                            <input name="select_item" type="radio" v-model="selectMenu" :value="item.data">
                        </td>
                        <td style="width:80px">{{item.data.menuId}}</td>
                        <td style="width:180px" v-bind:class="'padding-'+item.data.type">
                            <span class="hand glyphicon" v-bind:class="item.children ? ( !item.openNext ? 'glyphicon-chevron-right':'glyphicon-chevron-down') :''"
                                v-on:click="menuItemOpenClose(item,index)">
                            </span>{{item.data.name}}
                        </td>
                        <td style="width:100px">{{item.data.parentId}}</td>
                        <td style="width:80px">
                            <i v-bind:class="item.data.icon"></i>
                        </td>
                        <td style="width:100px">
                            <span class="label label-primary" v-if="item.data.type == 0">目录</span>
                            <span class="label label-success" v-if="item.data.type == 1">菜单</span>
                            <span class="label label-warning" v-if="item.data.type == 2">按钮</span>
                        </td>
                        <td style="width:100px">{{item.data.orderNum}}</td>
                        <td style="width:160px">{{item.data.url}}</td>
						<td>{{item.data.perms}}</td>
						<td>
							<el-button type="success" icon="el-icon-plus" @click="addMenu(this,item)" size="small"></el-button>
                        	<el-button type="primary" icon="el-icon-edit" @click="updateMenu(this,item)" size="small"></el-button>
                        	<el-button type="danger" icon="el-icon-delete" @click="deleteMenuItem(this,item)" size="small"></el-button>
						</td>
                    </tr>

                </tbody>
            </table>
        </el-main>
        <el-dialog :title="menuWindow.title" :visible.sync="menuWindow.visible">
            <el-form ref="form" :model="form" label-width="100px" >
                <el-form-item label="菜单名称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>

                <el-row>
                    <el-col :span="5">
                        <el-form-item label="菜单图标">
                            <el-select v-model="form.icon" placeholder="请选择">
                                <el-option v-for="item in icon_chose" :key="item.icon" :label="item.label" :value="item.icon">
                                    <span :class="item.icon"></span>
                                    <span>{{ item.label }}</span>
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="10">
                        <el-form-item label="输入">
                            <el-input v-model="form.icon"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5">
                        <el-form-item label="菜单序号">
                            <el-input-number v-model="form.orderNum" controls-position="right" :min="1"></el-input-number>
                        </el-form-item>
                    </el-col>
                    <el-col :span="14">
                    </el-col>
                </el-row>
                <el-form-item label="菜单类型">
                    <el-radio-group v-model="form.type">
                        <el-radio :label="0" border>目录</el-radio>
                        <el-radio :label="1" border>菜单</el-radio>
                        <el-radio :label="2" border>按钮</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="菜单地址">
                    <el-input v-model="form.url"></el-input>
                </el-form-item>
                <el-form-item label="菜单权限">
                    <el-input type="textarea" v-model="form.perms"></el-input>
                </el-form-item>
                <el-form-item label="上一级目录">
                    <el-cascader
                        :options="menuDataAll"
                        v-model="form.parentIds"
                        :props="parent_props" :show-all-levels="true"
                        filterable change-on-select
                         >
                    </el-cascader>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveOrUpdateMenu">立即创建</el-button>
                    <el-button @click="menuWindow.visible=false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </el-container>

</body>

</html>
<script>
var appInstince = new Vue({
		el: '#app',
		data: {
			parent_props: {
				label: "name",
				value: "menuId",
				children: 'children'
			},
			selectMenu: {
				menuId: 0,
				name: '根目录'
			},
			treeMenuData: [],
			menuDataNormal: [],
			menuData: [],
			menuWindow: {
				title: '窗口',
				visible: false,
				update: false
			},
			form: {
				name: '',
				icon: '',
				type: '',
				orderNum: '',
				url: '',
				perms: '',
				parentId: 0
			},
			icon_chose: [{
					label: '查询',
					icon: 'fa fa-search'
				}, {
					label: '增加',
					icon: 'fa fa-plus'
				}, {
					label: '修改',
					icon: 'fa fa-pencil'
				}, {
					label: '删除',
					icon: 'fa fa-trash-o'
				}, {
					label: '审核',
					icon: 'fa fa-lock'
				}
			]

		},
		mounted() {
			this.loadTreeData();
		},
		computed: {
			menuDataAll() {
				let ay = clone(this.menuDataNormal);
				ay.push({
					menuId: 0,
					name: '根目录'
				});
				return ay;
			},
			parentMenuData() {
				let ay = [];
				let temp = breadPath(this.selectMenu, this.menuDataNormal, item => item.children, item => item.parentId, self => self.menuId, item => item.data);
				console.log('面包路径', temp)
				if (temp && temp.length > 0) {
					temp.forEach(el => {
						if (el.type != 2) {
							ay.push(el);
						}
					});
				}

				if (!this.selectMenu || this.selectMenu.parentId == 0) {
					ay.push({
						menuId: 0,
						name: '根目录'
					});
				}

				this.parentDirs = ay;
				return ay;
			}
		},
		methods: {
			//过滤权限
			filterPermsButton(data) {
				function next(obj, chi) {
					obj.children = null;
					if (obj.type == 2)
						return;
					if (chi) {
						let ay = [];
						chi.forEach(v => {
							let o = v;
							if (o.type == 2)
								return;
							ay.push(o);
							next(o, v.children);
						});
						obj.children = ay;
					} else {
						return;
					}
				}
				let ret = [];
				data.map((v) => {
					let obj = v;
					ret.push(obj);
					next(obj, v.children);
				});
				return ret;
			},
			parentIdChange(val) {
				console.log('change: ', arguments);
				this.form.parentId = val;
			},
			addMenu(target,item) {
				if(item)this.selectMenu = item.data;
				this.menuWindow.title = '增加菜单';
				this.menuWindow.visible = true;
				this.menuWindow.update = false;
				this.form = {};
				let selectMenu = this.selectMenu;
				this.form.parentId = selectMenu.menuId;
				if (selectMenu.type == 0) {
					this.form.type = 1;
				} else if (selectMenu.type == 1 || selectMenu.type == 2) {
					this.form.type = 2;
				}
				let temp = breadPath(this.selectMenu, this.menuDataNormal, item => item.children, item => item.parentId, self => self.menuId, item => item);
				this.form.parentIds = temp.filter(item => item.type != 2).map(item => item.menuId);
				console.log('parendIds ', this.form.parentIds);
			},
			updateMenu(target,item) {
				if(item)this.selectMenu = item.data;
				this.menuWindow.title = '修改菜单';
				this.menuWindow.visible = true;
				this.menuWindow.update = true;
				this.form = this.selectMenu;
				let temp = breadPath(this.selectMenu, this.menuDataNormal, item => item.children, item => item.parentId, self => self.menuId, item => item);
				this.form.parentIds = temp.map(item => item.menuId);
				console.log('parendIds ', this.form.parentIds);
			},
			deleteMenuItem(target,item) {
				if(item)this.selectMenu = item.data;
				if (!this.selectMenu.menuId)
					return;
				let menuName = this.selectMenu.name;
				this.$confirm('是否删除: '+menuName+' ?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					let menuId = this.selectMenu.menuId;
					let url = '/sys/menu/menu/' + menuId;
					ajax_promise(url, 'delete', {}).then(result => {
						if (result.status) {
							$message('删除菜单成功', 'success', this);
							this.loadTreeData();
						}
					});
				})
			},
			deleteMenu() {
				//if (!this.selectMenu.menuId) return;
				this.$confirm('此操作将永久该菜单, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					let menuId = this.selectMenu.menuId;
					let url = '/sys/menu/menu/' + menuId;
					ajax_promise(url, 'delete', {}).then(result => {
						if (result.status) {
							$message('删除菜单成功', 'success', this);
							this.loadTreeData();
						}
					});
				})

			},
			loadTreeData() {
				var ins = this;
				var url = 'sys/menu/tree-node';
				ajax_json(url, 'get', null, function (result) {
					ins.treeMenuData = result.data;
					ins.menuData = toArray(result.data);
					ins.toNormalData(result.data);
				});
			},
			//转化为普通数据
			toNormalData(data) {
				function next(obj, chi) {
					if (chi) {
						let ay = [];
						for (let i = 0; i < chi.length; i++) {
							let v = chi[i];
							let o = v.data;
							ay.push(o);
							if (v.children && v.children.length > 0)
								next(o, v.children);
						}
						if (ay.length > 0)
							obj.children = ay;
					}
				}

				let ret = [];
				data.map((v) => {
					let obj = v.data;
					ret.push(obj);
					next(obj, v.children);
				});
				this.menuDataNormal = ret;
				return ret;
			},
			saveOrUpdateMenu() {
				let data = this.form;
				console.log('增加/编辑菜单数据: ', data);
				let url = '/sys/menu/menu';
				//如果是目录, 则默认为顶级
				if(data.type == 0){
					data.parentId = 0;
				}else if(data.type == 1){
					// 菜单
					if (data.parentIds && data.parentIds.length >0 ) {
						data.parentId = data.parentIds[0];
					}else{
						$message('创建菜单必须在目录下','danger',this);
						return;
					}
				}else if(data.type == 2){
					//按钮
					if (data.parentIds && data.parentIds.length >= 2 ) {
						data.parentId = data.parentIds[1];
					}else{
						$message('创建的按钮必须在菜单下','danger',this);
						return;
					}
				}
				 
				let method = this.menuWindow.update ? 'put' : 'post';
				ajax_json_promise(url, method, data).then(result => {
					if (result.status) {
						$message('操作菜单成功', 'success', this);
						this.menuWindow.visible = false;
						//this.loadTreeData();
					}
				});
			},
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},
			menuItemClick(item) {
				this.selectMenu = item.data;
			},
			menuItemOpenClose(item, index) {
				let me = this;
				item.openNext = !item.openNext;
				if (item.openNext) {
					item.open = true;
				}
				$.each(item.children, function (i, v) {
					v.open = item.openNext;
					//关闭 状态需要将子菜单也关闭
					if (!item.openNext) {
						handleNext(item);
					}
				});

				function handleNext(it) {
					let ay = me.menuData.filter(el => it.data.menuId == el.data.parentId);
					console.debug(it.data.name, ' 下一级 ', ay);
					ay.forEach(v => {
						v.open = item.openNext;
						handleNext(v);
					});
				}
				//fake: 触发数组更新
				this.menuData.push({});
				this.menuData.pop();
				/*setTimeout(() => {
					this.$set(this.menuData, index, item)
				}, 100);*/
			}
		}
	});

function toArray(md) {
	var ay = [];
	hdChildren(ay, md);
	return ay;
}

function hdChildren(ay, md) {
	$.each(md, function (i, v) {
		v.open = false;
		v.openNext = false;
		ay.push(v);
		if (v.children)
			hdChildren(ay, v.children);
	})
}

</script>