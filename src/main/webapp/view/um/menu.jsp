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
<%@include file="/include/bootstrap.jsp"%>
<%@include file="/include/head.jsp"%>
    <style>
        .padding-0 {}

        .padding-1 {
            padding-left: 30px !important;
        }

        .padding-2 {
            padding-left: 55px !important;
        }

        .hand {
            cursor: pointer;
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
                        <el-button type="danger" icon="el-icon-delete" @click="deleteMenu">删除菜单</el-button>
                    </div>
                </el-col>
            </el-row>
  </el-header>
  <el-main>
      <table class="table table-hover treegrid-table table-bordered table-striped" id="menuTable" data-click-to-select="true" data-mobile-responsive="true">
            <thead class="treegrid-thead">
                <tr>
                    <th style="width:36px"></th>
                    <th style="padding:10px;width:80px">菜单ID</th>
                    <th style="padding:10px;width:180px">菜单名称</th>
                    <th style="padding:10px;width:100px">上级菜单</th>
                    <th style="padding:10px;width:80px">图标</th>
                    <th style="padding:10px;width:100px">类型</th>
                    <th style="padding:10px;width:100px">排序号</th>
                    <th style="padding:10px;width:160px">菜单URL</th>
                    <th style="padding:10px;">授权标识</th>
                </tr>
            </thead>
            <tbody>
                	<!--一级目录-->
				<tr v-for="item in menuData" v-show="item.open || item.type == 0" v-on:click="menuItemClick(item)">
					<td style="text-align:center;width:36px">
						<input name="select_item" type="radio" v-model="selectMenuId" :value="item.menuId">
					</td>
					<td style="width:80px">{{item.menuId}}</td>
					<td style="width:180px" v-bind:class="'padding-'+item.type">
						<span class="hand glyphicon" v-bind:class="item.children ? ( !item.openNext ? 'glyphicon-chevron-right':'glyphicon-chevron-down') :''" v-on:click="menuItemOpenClose(item)">
						</span>{{item.name}}
					</td>
					<td style="width:100px">{{item.parentId}}</td>
					<td style="width:80px">
						<i v-bind:class="item.icon"></i>
					</td>
					<td style="width:100px">
						<span class="label label-primary" v-if="item.type == 0">目录</span>
						<span class="label label-success" v-if="item.type == 1">菜单</span>
						<span class="label label-warning" v-if="item.type == 2">按钮</span>
					</td>
					<td style="width:100px">{{item.orderNum}}</td>
					<td style="width:160px">{{item.url}}</td>
					<td style="">{{item.perms}}</td>
				</tr>

            </tbody>
        </table>
  </el-main>
</el-container>

</body>
</html>
<script>
var appInstince = new Vue({
        el: '#app',
        data: {
            selectMenuId: 0,
            menuData: []
        },	
        methods: {
            addMenu(){

            },
            updateMenu(){

            },
            deleteMenu(){

            },
            loadTreeData(){
                var ins = this;
                var url = 'sys/menu/treeNode';
                ajax_json(url,'get',null,function(result){
                    ins.menuData = toArray(result.data);
                });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            menuItemClick(item) {
                this.selectMenuId = item.menuId;
            },
            menuItemOpenClose(item) {
                item.openNext = !item.openNext;
                if(item.openNext){
                    item.open = true;
                } 
				$.each(item.children, function (i, v) {
					v.open = item.openNext;
				});

			}
        }
    });
     

    //appInstince._data.menuData = initData(md)
    appInstince.loadTreeData();
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
			if (v.children) hdChildren(ay, v.children);
		})
	}
</script>