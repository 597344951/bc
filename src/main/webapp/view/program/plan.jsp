<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>节目播放计划</title>
	<%@include file="/include/base.jsp" %>
	<%@include file="/include/element-ui.jsp" %>
	<style>
		.el-table thead,.el-table__row {
			font-size: 14px;
		}
		.el-row {
			margin: 10px auto;
			width: 95%;
		}
		.el-dialog__body {
			padding: 10px 25px;
		}
		.input-item {
			width: 250px;
		}
		.select-item {
			width: 200px;
		}
		.el-input-number i {
			margin-top: 6px;
		}
	</style>
</head>
<body>
	<div id="app">
		<el-container>
			<el-header>
				<h3>节目播放计划</h3>
			</el-header>
			<el-main>
				<el-row :gutter="10">
					<el-col :span="4">
						<el-input placeholder="请输入关键字" v-model="keyword"><i slot="suffix" class="el-input__icon el-icon-search"></i></el-input>
					</el-col>
					<el-col :span="2">
						<el-button type="primary" icon="el-icon-plus" @click="addPlanDialogVisible = true"></el-button>
					</el-col>
				</el-row>
				<el-table :data="plan.list" border style="width: 100%">
					<el-table-column prop="name" label="名称"></el-table-column>
					<el-table-column prop="plan" label="播放计划"></el-table-column>
					<el-table-column prop="date" label="下次播放时间"></el-table-column>
					<el-table-column prop="period" label="播放时段"></el-table-column>
					<el-table-column prop="playType" label="内容播放顺序"></el-table-column>
					<el-table-column label="操作">
						<template slot-scope="scope">
							<el-button type="text" size="small">移除</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-main>
			<el-footer>
				<el-pagination
						@size-change="handleSizeChange"
						@current-change="handleCurrentChange"
						:current-page="plan.pageNum"
						:page-sizes="pageSizes"
						:page-size="pageSize"
						layout="total, sizes, prev, pager, next, jumper"
						:total="plan.total">
				</el-pagination>
			</el-footer>
		</el-container>

		<%--添加计划弹窗--%>
		<el-dialog title="添加播放计划" :visible="addPlanDialogVisible" :show-close="false">
			<el-form :model="newPlan" label-position="left" label-width="120px">
				<el-form-item label="计划名称">
					<el-input v-model="newPlan.name" class="input-item"></el-input>
				</el-form-item>
				<el-form-item label="计划">
					<el-select v-model="newPlan.type" placeholder="计划类型" class="select-item">
						<el-option label="定时" value="timing"></el-option>
						<el-option label="循环" value="cycle"></el-option>
					</el-select>
					<el-date-picker v-if="newPlan.type == 'timing'" v-model="newPlan.timingDate" type="date" placeholder="定时日期" value-format="yyyy-MM-dd 00:00:00" class="input-item"></el-date-picker>
					<el-select v-if="newPlan.type == 'cycle'" v-model="newPlan.cycleType" placeholder="循环周期" class="select-item">
						<el-option label="周" value="week"></el-option>
						<el-option label="月" value="month"></el-option>
						<el-option label="固定时间" value="fixed"></el-option>
					</el-select>

					<el-select v-if="newPlan.type == 'cycle' && newPlan.cycleType == 'week'" v-model="newPlan.cycleDay" placeholder="指定日期" class="select-item">
						<el-option label="星期日" value="0"></el-option>
						<el-option label="星期一" value="1"></el-option>
						<el-option label="星期二" value="2"></el-option>
						<el-option label="星期三" value="3"></el-option>
						<el-option label="星期四" value="4"></el-option>
						<el-option label="星期五" value="5"></el-option>
						<el-option label="星期六" value="6"></el-option>
					</el-select>

					<el-select v-if="newPlan.type == 'cycle' && newPlan.cycleType == 'month'" v-model="newPlan.cycleDay" placeholder="指定日期" class="select-item">
						<el-option v-for="i in 31" :label="i" :value="i"></el-option>
					</el-select>

					<el-input-number v-if="newPlan.type == 'cycle' && newPlan.cycleType == 'fixed'" v-model="newPlan.cycleDay" controls-position="right" :min="0" class="select-item"></el-input-number>
				</el-form-item>

				<el-form-item label="开始时间" v-if="newPlan.type == 'cycle'">
					<el-date-picker v-model="newPlan.startDate" type="date" placeholder="定时日期" value-format="yyyy-MM-dd 00:00:00" style="width: 250px;"></el-date-picker>
				</el-form-item>

				<el-form-item label="播放时段">
					<el-time-picker
							style="width: 250px"
							is-range
							v-model="newPlan.period"
							range-separator="-"
							value-format="H:mm"
							start-placeholder="开始"
							end-placeholder="结束"
							placeholder="播放时段">
					</el-time-picker>
				</el-form-item>
				<el-form-item label="选择节目">
					<el-tree :props="newPlan.program"
							:load="loadProgram"
							lazy
							show-checkbox
							@check-change="handleCheckChange">
					</el-tree>
				</el-form-item>
				<el-form-item label="节目播放顺序">
					<el-select v-model="newPlan.playType" placeholder="播放顺序" class="input-item">
						<el-option label="顺序播放" value="order"></el-option>
						<el-option label="随机播放" value="random"></el-option>
					</el-select>
				</el-form-item>
			</el-form>

			<div slot="footer" class="dialog-footer">
				<el-button size="mini" @click="addPlanDialogVisible = false">取 消</el-button>
				<el-button size="mini" type="primary" @click="addPlan()">添 加</el-button>
			</div>
		</el-dialog>

		<el-dialog title="验证节目内容" :visible="commitMessage.show" :show-close="false" width="300px">
			<p style="font-size: 13px; margin: 5px 20px; color: blue" v-for="m in commitMessage.message">{{m}}</p>
		</el-dialog>
	</div>
	<script>
		var app = new Vue({
			el: '#app',
			data: {
			    keyword: '',
                pageSizes: [10, 20, 50, 100],
			    pageSize: 10,
				plan:{
			        total: 3,
                    pageNum: 1,
					list: [
					    {
							name: '计划一',
							plan: '循环：每周一',
							date: '2018-06-04',
							period: '8:00 - 10:00',
							playType: '顺序'
						},{
                            name: '计划一',
                            plan: '循环：每周一',
                            date: '2018-06-04',
                            period: '8:00 - 10:00',
                            playType: '顺序'
						}, {
                            name: '计划一',
                            plan: '循环：每周一',
                            date: '2018-06-04',
                            period: '8:00 - 10:00',
                            playType: '顺序'
						}]

				},
                addPlanDialogVisible: false,
                program: {
                    label: 'label',
                    children: 'children'
                },
				newPlan: {
                    program: []
				},
				commitMessage: {
                    show: false,
                    message: []
                },
				count: 0
			},
            methods: {
                handleSizeChange(pageSize) {
                    load(1, pageSize)
				},
                handleCurrentChange(curPage) {
					load(curPage, this.pageSize)
				},
                handleCheckChange(data, checked, indeterminate) {
                    if(data.type == 'program') {
                        if(checked) {
                            this.newPlan.program.push(data)
                        } else {
                            for(var i in this.newPlan.program) {
                                if(data.id == this.newPlan.program[i].id) {
                                    this.newPlan.program.splice(i, 1);
								}
							}
						}
					}
                },
                loadProgram(node, resolve) {
                    if (node.level === 0) {
                        return resolve([{ label: '节目列表', type: 'category', id: this.count ++ }]);
                    } else if (node.level === 1) {
                        return resolve([
                            { label: '国学', type: 'category', id: this.count ++ },
                            { label: '国学', type: 'category' },
                            { label: '国学', type: 'category' },
                            { label: '国学', type: 'category' }
						]);
                    } else if (node.level === 2) {
                        return resolve([
                            { label: '节目一', type: 'program', url: '#', id: this.count ++ },
                            { label: '节目一', type: 'program', url: '#', id: this.count ++ },
                            { label: '节目一', type: 'program', url: '#', id: this.count ++ },
                            { label: '节目一', type: 'program', url: '#', id: this.count ++ }
                        ]);
                    } else {
                        return resolve([])
					}
                },
				addPlan() {
                    this.plan.list.push({
						name: this.newPlan.name,
                        plan: planString(this.newPlan),
                        date: '2018-06-04',
                        period: this.newPlan.period[0] + ' - ' + this.newPlan.period[1],
                        playType: this.newPlan.playType == 'random' ? '随机' : '顺序'

					})
					this.plan.total ++;
					this.addPlanDialogVisible = false
					commitMessage()
				}
			}
		});
		//load(1, app.pageSize);

		const commitMessage = (callback) => {
			app.commitMessage.show = true
			message = [
						'校验节目MD5...',
						'完成, 未发现异常',
						'添加计划...',
						'添加完成'
					]
			let index = 1;
			app.commitMessage.message = []
			app.commitMessage.message.push(message[0])
			let iterval = setInterval(() => {
				app.commitMessage.message.push(message[index])
				index ++
				if(index == message.length) {
					app.commitMessage.show = false
                    clearInterval(iterval)
                    if(callback) {
                        callback()
                    }
					
				}
			}, 2*1000);
		}

		function planString(plan) {
			if('timing' == plan.type) {
			    return '定时：' + plan.timingDate
			} else if('cycle' == plan.type) {
			    var str = '循环：';
				if(plan.cycleType == 'week') {
					str += '每周' + plan.cycleDay
				} else if(plan.cycleType == 'month') {
                    str += '每月' + plan.cycleDay + '号'
				} else if(plan.cycleType == 'fixed') {
					str += '每' + plan.cycleDay + '天'
				}
				return str;
			}
        }

		function load(pageNum, pageSize) {
		    get('' + pageSize, reps => {
				if(reps.status) {
					app.plan = reps.data;
				} else {
				    app.$message('没有数据...');
				}
			});
        }

        function get(url, callback) {
            $.ajax({
                type:'GET',
                url:url,
                dataType:'json',
                success: function(reps){
                    callback(reps);
                },
                error: function (err) {
                    app.$notify({
                        title: 'ERROR',
                        message: '系统错误...',
                        type: 'error'
                    });
                }
            });
        }

        function post(url, postData, callback) {
            $.ajax({
                type:'POST',
                url:url,
                dataType:'json',
                contentType: 'application/json',
                data: JSON.stringify(postData),
                success: function(reps){
                    callback(reps);
                },
                error: function (err) {
                    app.$notify({
                        title: 'ERROR',
                        message: '系统错误...',
                        type: 'error'
                    });
                }
            });
        }
	</script>
</body>
</html>