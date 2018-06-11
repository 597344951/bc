<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>党员发展</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <style>
        .separation {
            text-align: center;
        }

        .el-table thead, .el-table__row {
            font-size: 14px;
        }
    </style>
</head>
<body>
<div id="app">
    <el-container>
        <el-header>
            <h3>党员发展</h3>
        </el-header>
        <el-main>
            <el-row :cutter="20">
                <el-col :span="20">
                    <el-form :inline="true" :model="filter">
                        <el-form-item label="姓名">
                            <el-input v-model="filter.name" placeholder="姓名"></el-input>
                        </el-form-item>

                        <el-form-item label="申请时间">
                            <el-col :span="11">
                                <el-form-item style="margin: 0px">
                                    <el-date-picker type="date" placeholder="选择日期" v-model="filter.startDate" style="width: 100%;"></el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="2" class="separation">-</el-col>
                            <el-col :span="11">
                                <el-form-item>
                                    <el-date-picker type="date" placeholder="选择时间" v-model="filter.endDate" style="width: 100%;"></el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="search" icon="el-icon-search">查询</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
            </el-row>
            <el-table :data="members.list" border style="width: 100%">
                <el-table-column prop="name" label="姓名"></el-table-column>
                <el-table-column prop="graduationDate" label="毕业时间" width="120"></el-table-column>
                <el-table-column prop="youthDate" label="入团时间" width="120"></el-table-column>
                <el-table-column prop="applyDate" label="申请入党时间"></el-table-column>
                <el-table-column prop="memberStepLabel" label="党员发展阶段">
                    <template slot-scope="scope">
                        <el-tag>{{scope.row.memberStepLabel}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="subMemberStepLabel" label="当前状态">
                    <template slot-scope="scope">
                        <el-tag>{{scope.row.subMemberStepLabel}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="remark" label="备注"></el-table-column>
                <el-table-column label="操作" width="250">
                    <template slot-scope="scope">
                        <el-button size="mini" type="text" @click="detail.show = true">查看资料</el-button>
                        <el-button size="mini" type="text" @click="showStep(scope.row)">查看发展进度</el-button>
                        <el-button size="mini" v-if="hasNext" type="text" @click="doCurOperate(scope.row)">进入下一阶段</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-main>
        <el-footer>
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="members.pageNum"
                    :page-sizes="pageSizes"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="members.total">
            </el-pagination>
        </el-footer>
    </el-container>

    <%--进度弹窗--%>
    <el-dialog
            title="当前阶段进度"
            width="500px"
            :visible.sync="stepsVisible">
        <el-steps :active="curStep.active" finish-status="success" direction="vertical" :space="100">
            <el-step v-for="s in steps[curStep.step].subStep" :title="s.label" :description="s.desc"></el-step>
        </el-steps>
        <span slot="footer" class="dialog-footer">
            <el-button size="mini" @click="stepsVisible = false">取 消</el-button>
            <el-button size="mini" type="primary" @click="stepsVisible = false">确 定</el-button>
        </span>
    </el-dialog>
    <%--培养联系人--%>
    <el-dialog
            title="添加培养联系人"
            :visible.sync="trainContacts.show">
        <el-button size="mini" type="primary" icon="el-icon-plus" @click="addTrainContact">添加</el-button>
        <el-table size="mini" :data="trainContacts.contacts" border style="height: 250px; margin-top: 10px">
            <el-table-column prop="name" label="姓名">
                <template slot-scope="scope">
                    <el-input size="mini" v-model="scope.row.name" placeholder="请输入姓名"></el-input>
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="联系电话">
                <template slot-scope="scope">
                    <el-input size="mini" v-model="scope.row.phone" placeholder="请输入联系方式"></el-input>
                </template>
            </el-table-column>
            <el-table-column width="100">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeTrainContact(scope.row)">移除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
            <el-button size="mini" @click="trainContacts.show = false">取 消</el-button>
            <el-button size="mini" type="primary" @click="trainContacts.show = false">确 定</el-button>
        </span>
    </el-dialog>
    <%--入党介绍人--%>
    <el-dialog
            title="添加入党介绍人"
            :visible.sync="sponsor.show">
        <el-button size="mini" type="primary" icon="el-icon-plus" @click="addSponsor">添加</el-button>
        <el-table size="mini" :data="sponsor.sponsors" border style="height: 250px; margin-top: 10px">
            <el-table-column prop="name" label="姓名">
                <template slot-scope="scope">
                    <el-input size="mini" v-model="scope.row.name" placeholder="请输入姓名"></el-input>
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="联系电话">
                <template slot-scope="scope">
                    <el-input size="mini" v-model="scope.row.phone" placeholder="请输入联系方式"></el-input>
                </template>
            </el-table-column>
            <el-table-column width="100">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeSponsor(scope.row)">移除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
            <el-button size="mini" @click="sponsor.show = false">取 消</el-button>
            <el-button size="mini" type="primary" @click="sponsor.show = false">确 定</el-button>
        </span>
    </el-dialog>

    <%--个人资料--%>
    <el-dialog
            title="个人资料"
            width="500px"
            :visible.sync="detail.show">
        <el-form :model="detail" label-width="150" size="mini">
            <el-form-item label="姓名">
                <el-input v-model="detail.name" disabled></el-input>
            </el-form-item>
            <el-form-item label="出生日期">
                <el-input v-model="detail.birthday" disabled></el-input>
            </el-form-item>
            <el-form-item label="毕业时间">
                <el-input v-model="detail.graduationDate" disabled></el-input>
            </el-form-item>
            <el-form-item label="入团时间">
                <el-input v-model="detail.youthDate" disabled></el-input>
            </el-form-item>
            <el-form-item label="申请入党时间">
                <el-input v-model="detail.applyDate" disabled></el-input>
            </el-form-item>
            <el-form-item label="申请加入组织">
                <el-input v-model="detail.org" disabled></el-input>
            </el-form-item>
            <el-form-item label="入党申请书">
                <el-button size="mini" type="text">点击查看入党申请书</el-button>
            </el-form-item>
            <el-form-item label="积极分子时间">
                <el-input v-model="detail.preparationDate" disabled></el-input>
            </el-form-item>
            <el-form-item label="预备党员时间">
                <el-input v-model="detail.officialDate" disabled></el-input>
            </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
            <el-button size="mini" @click="detail.show = false">取 消</el-button>
            <el-button size="mini" type="primary" @click="detail.show = false">确 定</el-button>
        </span>
    </el-dialog>

</div>
<script type="text/javascript">
    const app = new Vue({
        el: '#app',
        data: {
            pageSizes: [10, 20, 50, 100],
            pageSize: 10,
            filter: {},
            members: {
                total: 1,
                pageNum: 1,
                list: [{
                    name: '加特林',
                    graduationDate: '2015-07-01',
                    youthDate: '2009-07-01',
                    applyDate: '2018-07-01 10:00:00',
                    memberStep: 1,
                    memberStepLabel: '优秀分子 -> 积极分子',
                    subMemberStep: 1,
                    subMemberStepLabel: '提交入党申请书',
                    remark: ''
                }]
            },
            steps: [
                {
                    sort: 1,
                    name: 'activist',
                    label: '优秀分子 -> 积极分子',
                    subStep: [{
                        sort: 1,
                        name: 'apply',
                        label: '提交入党申请书',
                        desc: ''
                    }, {
                        sort: 2,
                        name: 'conversation',
                        label: '组织谈话',
                        desc: ''
                    }, {
                        sort: 3,
                        name: 'record',
                        label: '组织审批备案',
                        desc: ''
                    }]
                }, {
                    sort: 2,
                    name: 'preparation',
                    label: '积极分子 -> 预备党员',
                    subStep: [{
                        sort: 1,
                        name: 'trainContacts',
                        label: '指定培养联系人',
                        desc: ''
                    }, {
                        sort: 2,
                        name: 'train',
                        label: '培训教育考察',
                        desc: ''
                    }, {
                        sort: 3,
                        name: 'grow',
                        label: '确定为发展对象',
                        desc: ''
                    }, {
                        sort: 4,
                        name: 'record',
                        label: '组织备案',
                        desc: ''
                    }, {
                        sort: 5,
                        name: 'sponsor',
                        label: '指定入党介绍人',
                        desc: ''
                    }, {
                        sort: 6,
                        name: 'check',
                        label: '政治审查',
                        desc: ''
                    }, {
                        sort: 7,
                        name: 'train',
                        label: '集中培训',
                        desc: ''
                    }, {
                        sort: 8,
                        name: 'record',
                        label: '审批备案',
                        desc: ''
                    }, {
                        sort: 9,
                        name: 'application',
                        label: '入党志愿书',
                        desc: ''
                    }, {
                        sort: 10,
                        name: 'sponsorOpinion',
                        label: '介绍人意见',
                        desc: ''
                    }, {
                        sort: 11,
                        name: 'conference',
                        label: '支部党员大会',
                        desc: ''
                    }, {
                        sort: 12,
                        name: 'conversation',
                        label: '谈话',
                        desc: ''
                    }, {
                        sort: 13,
                        name: 'record',
                        label: '审批备案',
                        desc: ''
                    }]
                }, {
                    sort: 3,
                    name: 'official',
                    label: '预备党员 -> 正式党员',
                    subStep: [{
                        sort: 1,
                        name: 'apply',
                        label: '入党宣誓',
                        desc: ''
                    }, {
                        sort: 2,
                        name: 'verify',
                        label: '编入党小组',
                        desc: ''
                    }, {
                        sort: 3,
                        name: 'conversation',
                        label: '教育考察',
                        desc: ''
                    }, {
                        sort: 4,
                        name: 'record',
                        label: '申请转正',
                        desc: ''
                    }, {
                        sort: 5,
                        name: 'record',
                        label: '支部党员大会',
                        desc: ''
                    }, {
                        sort: 6,
                        name: 'archived',
                        label: '审批归档',
                        desc: ''
                    }]
                }
            ],
            curStep: {
                active: 0,
                step: 0
            },
            hasNext: true,
            stepsVisible: false,
            trainContacts: {
                show: false,
                contacts: []
            },
            sponsor: {
                show: false,
                sponsors: []
            },
            detail: {
                show: false
            }
        },
        methods: {
            handleSizeChange(pageSize) {
                load(1, pageSize)
            },
            handleCurrentChange(curPage) {
                load(curPage, this.pageSize)
            },
            search() {
            },
            doCurOperate(row) {
                this.$confirm('确认完成本阶段内容进入下一阶段？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let step = row.memberStep - 1
                    let subStep = row.subMemberStep - 1

                    let name = this.steps[step].subStep[subStep].name
                    if('trainContacts' == name) {
                        this.trainContacts.show = true
                    }
                    if('sponsor' == name) {
                        this.sponsor.show = true
                    }

                    subStep ++;
                    if(subStep >= this.steps[step].subStep.length) {
                        step ++
                        subStep = 0
                        if(step >= this.steps.length) {
                            this.$message({
                                message: '完成所有任务, 成为正式党员...',
                                type: 'success'
                            })
                            this.hasNext = false
                            this.curStep.active ++
                            return
                        }
                    }
                    this.curStep.active = subStep
                    this.curStep.step = step
                    row.memberStep = step + 1
                    row.memberStepLabel = this.steps[step].label
                    row.subMemberStep = subStep + 1
                    row.subMemberStepLabel = this.steps[step].subStep[subStep].label
                }).catch(() => {
                });
            },
            showStep(row) {
                this.stepsVisible = true
            },
            addTrainContact() {
                this.trainContacts.contacts.push({
                    id: new Date().getTime(),
                    name: '',
                    phone: ''
                })
            },
            removeTrainContact(row) {
                for(let i=0; i<this.trainContacts.contacts.length; i++) {
                    if(row.id == this.trainContacts.contacts[i].id) {
                        this.trainContacts.contacts.splice(i, 1)
                        break
                    }
                }
            },
            addSponsor() {
                this.sponsor.sponsors.push({
                    id: new Date().getTime(),
                    name: '',
                    phone: ''
                })
            },
            removeSponsor(row) {
                for(let i=0; i<this.sponsor.sponsors.length; i++) {
                    if(row.id == this.sponsor.sponsors[i].id) {
                        this.sponsor.sponsors.splice(i, 1)
                        break
                    }
                }
            }
        }
    })


    const load = (pageNum, pageSize) => {
        let url = '' + pageNum + '/' + pageSize
        get(url, reps => {
            if (reps.status) {
                app.lecture = reps.data;
            } else {
                app.$message('没有数据...');
            }
        })
    }

    const get = (url, callback) => {
        $.ajax({
            type: 'GET',
            url: url,
            dataType: 'json',
            success: function (reps) {
                callback(reps)
            },
            error: function (err) {
                app.$message('系统错误,请联系管理员.')
            }
        })
    }

    const post = (url, postData, callback) => {
        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(postData),
            success: function (reps) {
                callback(reps);
            },
            error: function (err) {
                app.$message('系统错误,请联系管理员.')
            }
        })
    }
</script>
</body>
</html>
