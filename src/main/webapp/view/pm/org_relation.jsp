<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>组织关系转出</title>
        <%@include file="/include/head_notbootstrap.jsp"%>
        <style type="text/css">

        </style>
    </head>

    <body>
        <div id="app">
            <el-container>
                <el-header>
                    <el-row class="toolbar" :gutter="20">
                        <el-popover 
                            class="margin-left-10"
                            placement="bottom" 
                              width="200" 
                              trigger="click" >
                              <el-button size="small" type="primary" slot="reference">
                                  <i class="el-icon-search"></i>
                                  搜索人员
                              </el-button>
                              <div>
                                <el-row>
                                    <el-input size="small" clearable
                                        @change="queryTurnOutOrgPartyUsers"
                                        v-model="turnOutOrgPartyUsers.query.conditions.idCard" placeholder="请输入身份证号码"></el-input>
                                </el-row>
                              </div>
                        </el-popover>
                        <span style="float: right;">
                            <el-pagination
                                  layout="total, prev, pager, next, jumper" 
                                   @current-change="queryTurnOutOrgPartyUsers"
                                  :current-page.sync="turnOutOrgPartyUsers.query.page.pageNum"
                                  :page-size.sync="turnOutOrgPartyUsers.query.page.pageSize"
                                  :total="turnOutOrgPartyUsers.query.page.total">
                            </el-pagination>
                        </span>
                    </el-row>
                </el-header>
                <el-main>
                    <el-table 
                        row-key="id"
                        size="small" 
                        :data="turnOutOrgPartyUsers.query.page.list" 
                        style="width: 100%">
                        <el-table-column label="转移编号" prop="id" width="100"></el-table-column>
                        <el-table-column label="姓名" prop="name" width="100"></el-table-column>
                        <el-table-column label="性别" prop="sex" width="100"></el-table-column>
                        <el-table-column label="身份证号码" prop="idCard" width="200"></el-table-column>
                        <el-table-column label="申请时间" prop="applyTime" width="200"></el-table-column>
                        <el-table-column label="申请状态" prop="joinStatus" width="100"></el-table-column>
                        <el-table-column label="当前进行" prop="nowStepName"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button 
                                    @click="openTurnOutOrgPartyStatusDialog(scope.row)" 
                                    type="text" size="small">审批 / 状态
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-main>
            </el-container>


            <el-dialog @close="resetTurnOutOrgPartyStatusDialog" title="组织关系转出" :visible.sync="turnOutOrgPartyUsers.turnOutStatus.dialog" width="80%">
                <div>
                    <div>
                        <el-steps :active="turnOutOrgPartyUsers.turnOutStatus.stepNum" finish-status="success">
                            <template v-for="item in turnOutOrgPartyUsers.turnOutStatus.process">
                                <el-step :title="item.name" :status="item.status"></el-step>
                            </template>
                        </el-steps>
                    </div>
                    
                    <!-- 公共提示信息 -->
                    <div style="font-size: 12px;">
                        <!-- thisStepInfoNull代表没有查询到这个步骤信息 -->
                        <div v-if="turnOutOrgPartyUsers.turnOutStatus.stepInfo != null">	
                            <p style="color: red;" v-if="turnOutOrgPartyUsers.turnOutStatus.stepInfo.describes != null && 
                                turnOutOrgPartyUsers.turnOutStatus.stepInfo.describes != ''">
                                说明：{{turnOutOrgPartyUsers.turnOutStatus.stepInfo.describes}}
                            </p>
                            <p>
                                提交时间：{{turnOutOrgPartyUsers.turnOutStatus.stepInfo.stepTime}}
                            </p>
                            <p>
                                方向：{{turnOutOrgPartyUsers.turnOutStatus.userInfo.orgInfoName == null || 
                                    turnOutOrgPartyUsers.turnOutStatus.userInfo.orgInfoName == '' ? 
                                    '非本平台组织' : '本平台组织'}}
                            </p>
                            <p>
                                转入组织：{{turnOutOrgPartyUsers.turnOutStatus.userInfo.orgInfoName == null || 
                                    turnOutOrgPartyUsers.turnOutStatus.userInfo.orgInfoName == '' ? 
                                    turnOutOrgPartyUsers.turnOutStatus.userInfo.otherOrgName : 
                                    turnOutOrgPartyUsers.turnOutStatus.userInfo.orgInfoName}}
                            </p>
                            <p>审核状态：
                                <span :style="turnOutOrgPartyUsers.turnOutStatus.stepInfo.stepStatus == 'success' ? 
                                    'color: green; font-weight: bold;' : turnOutOrgPartyUsers.turnOutStatus.stepInfo.stepStatus == 'wait' ? 
                                    'color: #808080; font-weight: bold;' : turnOutOrgPartyUsers.turnOutStatus.stepInfo.stepStatus == 'error' ? 
                                    'color: red; font-weight: bold;' : 'color: black; font-weight: bold;'">
                                    {{turnOutOrgPartyUsers.turnOutStatus.stepInfo.stepStatus}}
                                </span>
                            </p>
                            <p v-if="turnOutOrgPartyUsers.turnOutStatus.stepInfo.stepStatusReason != null && 
                            turnOutOrgPartyUsers.turnOutStatus.stepInfo.stepStatusReason != ''">
                                附加消息：{{turnOutOrgPartyUsers.turnOutStatus.stepInfo.stepStatusReason}}
                            </p>
                        </div>
                        <template v-if="turnOutOrgPartyUsers.turnOutStatus.stepInfo == null">
                            <p>请等待用户将资料上传完毕</p>
                        </template>

                        <el-row v-if="turnOutOrgPartyUsers.turnOutStatus.stepInfo != null 
                            && turnOutOrgPartyUsers.turnOutStatus.stepInfo.stepFiles != null
                            && turnOutOrgPartyUsers.turnOutStatus.stepInfo.stepFiles.length > 0">
                            <p style="margin-bottom: 10px;">以下是上传的附加材料：</p>
                            <template v-for="item in turnOutOrgPartyUsers.turnOutStatus.stepInfo.stepFiles">
                                <div style="width: 80px; height: 150px; float: left; margin-right: 10px; margin-bottom: 10px;">
                                    <a target="_blank" style="text-decoration:none; color: dimgray;" :href="'http://192.168.1.8:3000' + item.filePath" :title="item.fileTitle">
                                        <div style="text-align: center">
                                            <img 
                                                :src="getFileTypeImg(item.fileType)" style="width: 60px; height: 80px;" />
                                        </div>
                                        <div>
                                            <p style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;">
                                                {{item.fileTitle}}
                                            </p>
                                        </div>
                                    </a>
                                </div>
                            </template>
                        </el-row>
                    </div>

                    <el-row style="margin-bottom: 10px;">
                        <el-button v-if="turnOutOrgPartyUsers.turnOutStatus.stepNum > 0" 
                            size="small" @click="turnOutStatusStepSet('s')">查看上一步结果
                        </el-button>
                        <el-button v-if="turnOutOrgPartyUsers.turnOutStatus.stepNum < turnOutOrgPartyUsers.turnOutStatus.stepNumNow" 
                            size="small" @click="turnOutStatusStepSet('x')">查看下一步结果
                        </el-button>
                        <el-button v-if="turnOutOrgPartyUsers.turnOutStatus.stepNum == turnOutOrgPartyUsers.turnOutStatus.stepNumNow
                            && turnOutOrgPartyUsers.turnOutStatus.userInfo.joinStatus == 'wait'" 
                            size="small" type="danger" @click="openTurnOutPartyStepReasonDialog('error')">拒绝组织转移申请
                        </el-button>
                        <el-button v-if="turnOutOrgPartyUsers.turnOutStatus.stepNum == turnOutOrgPartyUsers.turnOutStatus.stepNumNow
                            && turnOutOrgPartyUsers.turnOutStatus.userInfo.joinStatus == 'wait'" 
                            size="small" type="primary" @click="openTurnOutPartyStepReasonDialog('success')">本次通过
                        </el-button>
                    </el-row>
				</div>
            </el-dialog>

            <el-dialog @close="resetStepReason" :visible.sync="turnOutOrgPartyUsers.update.dialog">
                <el-input
                    style="margin-top: 10px; margin-bottom: 10px;"
                    type="textarea"
                    :autosize="{ minRows: 4}"
                    placeholder="有什么想说的吗？（可不填）"
                    v-model="turnOutOrgPartyUsers.update.setStepStatus.stepReason">
                </el-input>
                <el-button	
                    style="margin-bottom: 10px;"
                    size="small" type="primary" @click="turnOutOrgPartyUsers.update.setStepStatus.stepStatus == 'error' ? 
                        refuseTurnOutPartyStep() : adoptTurnPartyStep()">下一步</el-button>
            </el-dialog>

            <el-dialog @close="" title="指定职责" :visible.sync="turnOutDuty.dialog">
                <div>
                    <el-tree :default-expand-all="true" 
                        node-key="id" 
                        ref="turnOutOrgInfoTree"
                        show-checkbox 
                        :expand-on-click-node="false" 
                        :highlight-current="true" 
                        :data="turnOutDuty.orgDutyTreesForOrgInfo" 
                        :props="turnOutOrgInfoOrgDutyTreesForOrgInfoProps" 
                        :check-strictly="true" >
                    </el-tree>
                </div>
                <div style="margin: 10px;">
                    <el-button 
                        @click="turnOutUpdateOrgRelation" 
                        type="primary" size="small">确认
                    </el-button>
                </div>
            </el-dialog>
        </div>
    </body>

    <script type="text/javascript">
        var appInstince = new Vue({
            el: '#app',
		    data: {
                commons: {
                    innerScreenHeight: 0	//屏幕高度
                },
                turnOutOrgPartyUsers: {
                    query: {
                        page: {
                            pageNum: 1,		/* 当前页 */
                            pageSize: 10,	/* 页面大小 */
                            total: 0,
                            list: []
                        },
                        conditions: {	//搜索条件
                            idCard: null
                        }
                    },
                    update: {
                        dialog: false,
                        setStepStatus: {
                            stepReason: null,
                            stepStatus: null
                        }
                    },
                    turnOutStatus: {
                        dialog: false,
                        userInfo: {
                            joinStatus: null
                        },
                        process: null,
                        stepNum: 0,
                        stepNumNow: 0,
                        stepInfo: null
                    }
                },
                turnOutDuty: {
                    dialog: false,
                    orgDutyTreesForOrgInfo: [],
                    userInfo: null
                },
                turnOutOrgInfoOrgDutyTreesForOrgInfoProps: {
                    children: 'children',
                    label: function(_data, node){
                        return _data.data.orgDutyName;
                    }
                }
            },
            created: function () {
			    this.getScreenHeightForPageSize();
            },
            mounted:function () {
                this.queryTurnOutOrgPartyUsers();
            },
            methods: {
                turnOutUpdateOrgRelation() {
                    var obj = this;
                    var checkedKeys = [];
                    checkedKeys = obj.$refs.turnOutOrgInfoTree.getCheckedKeys(false);
                    if (checkedKeys.length == 0) {
                        toast('错误',"请选择该用户在此组织担任的职责",'error');
                        return;
                    }
                    obj.$confirm(
                        '同意后将加入申请的组织，本条申请流程完毕，将会消失，确认？', 
                        '提示', 
                        {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }
                    ).then(function(){			
                        var url = "/toou/user/insertOrgRelation";
                        var t = {
                            turnOutId: obj.turnOutDuty.userInfo.id,
                            orgRltUserId: obj.turnOutDuty.userInfo.baseUserId,
                            orgRltInfoId: obj.turnOutDuty.userInfo.turnOutOrgId,
                            orgRltDutys: checkedKeys
                        }
                        $.post(url, t, function(datas, status){
                            if (datas.code == 200) {
                                toast('成功',datas.msg,'success');
                                obj.turnOutDuty.dialog = false;
                            }
                            
                        })
                    }).catch(function(){
                        obj.$message({
                            type: 'info',
                            message: '已取消此操作'
                        });  
                    });
                },
                openTurnOutOrgDutyDialog(row) {
                    let obj = this;
                    obj.turnOutDuty.userInfo = row;

                    let url = "/org/duty/queryOrgDutyTreeForOrgInfo";
                    let t = {
                        orgDutyOrgInfoId: row.turnOutOrgId
                    }
                    $.post(url, t, function(datas, status){
                        if (datas.code == 200) {
                            if (datas.data != undefined) {
                                obj.turnOutDuty.orgDutyTreesForOrgInfo = datas.data;
                                obj.forPartyUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(obj.turnOutDuty.orgDutyTreesForOrgInfo);
                            } else {
                                obj.turnOutDuty.orgDutyTreesForOrgInfo = [];
                            }
                        }
                        
                    })

                    obj.turnOutDuty.dialog = true;
                },
                forPartyUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(menuTrees){	/* 向树里添加id属性，方便设置node-key */
                    var obj = this;
                    if(menuTrees != null) {
                        for (var i = 0; i < menuTrees.length; i++) {
                            var menuTree = menuTrees[i];
                            menuTree.id = menuTree.data.orgDutyId;                            
                            obj.forPartyUser_manager_queryOrgDutyForOrgInfoClickTreeToAddId(menuTree.children);
                        }
                    }
                },
                changeTurnOutPartyStepStatus() {
                    let obj = this;
                    let url = "/turn_out/step/updateTurnOutOrgPartySteps";
                    var t = {
                        id: obj.turnOutOrgPartyUsers.turnOutStatus.stepInfo.id,
                        stepStatus: obj.turnOutOrgPartyUsers.update.setStepStatus.stepStatus,
                        stepStatusReason: obj.turnOutOrgPartyUsers.update.setStepStatus.stepReason == null 
                            || obj.turnOutOrgPartyUsers.update.setStepStatus.stepReason == "" ? 
                            obj.turnOutOrgPartyUsers.update.setStepStatus.stepStatus == 'error' ? '拒绝申请' : '审核通过' 
                            : obj.turnOutOrgPartyUsers.update.setStepStatus.stepReason,
                        turnOutId: obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.id,
                        processId: obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.nowStep,
                        isFile: obj.turnOutOrgPartyUsers.turnOutStatus.stepInfo.isFile,
                        haveThisOrg: (obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.maxOrgProcess.processId == 
                        obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.nowStep && 
                        obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.otherOrgName != null && 
                        obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.otherOrgName != '') ? false : true
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200) {
                            obj.$message({
                                type: 'success',
                                message: '操作成功'
                            });
                            obj.turnOutOrgPartyUsers.turnOutStatus.dialog = false;
                            obj.turnOutOrgPartyUsers.update.dialog = false;
                            obj.queryTurnOutOrgPartyUsers();
                        } 
                    })
                },
                refuseTurnOutPartyStep() {
                    var obj = this;
                    obj.$confirm(
                        '拒绝此人的入党申请', 
                        '提示', 
                        {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }
                    ).then(function(){
                        obj.changeTurnOutPartyStepStatus();
                    }).catch(function(){
                        obj.$message({
                            type: 'info',
                            message: '已取消此操作'
                        });  
                    });
                },
                adoptTurnPartyStep() {
                    var obj = this;
                    let msgInfo = "入党申请本步骤通过";
                    if (obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.maxOrgProcess.processId == 
                        obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.nowStep && 
                        obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.otherOrgName != null && 
                        obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.otherOrgName != '') {
                        msgInfo = "用户组织关系转移到非本平台的组织，此步骤通过后该党员状态会被修改为“历史党员”，继续吗？";
                    }
                    obj.$confirm(
                        msgInfo, 
                        '提示', 
                        {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }
                    ).then(function(){
                        obj.changeTurnOutPartyStepStatus();
                    }).catch(function(){
                        obj.$message({
                            type: 'info',
                            message: '已取消此操作'
                        });  
                    });
                },
                openTurnOutPartyStepReasonDialog(stepStatus) {
                    var obj = this;
				    obj.turnOutOrgPartyUsers.update.setStepStatus.stepStatus = stepStatus;
				    obj.turnOutOrgPartyUsers.update.dialog = true;
                },
                resetStepReason() {
                    var obj = this;
				    obj.turnOutOrgPartyUsers.update.setStepStatus.stepReason = null;
                },
                resetTurnOutOrgPartyStatusDialog() {
                    let obj = this;
                },
                turnOutStatusStepSet(step){
                    var obj = this;

                    if (step == 's') {
                        obj.turnOutOrgPartyUsers.turnOutStatus.stepNum--;
                    } else if (step == 'x') {					
                        obj.turnOutOrgPartyUsers.turnOutStatus.stepNum++;
                    }
                    obj.turnOutOrgPartyUsers.turnOutStatus.stepInfo = null;

                    //查询出此步骤的信息
                    let url = "/org/turn_out/queryOrgTurnOutProcess";
                    let t = {
                        orgId: obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.turnOutOrgId,
                        indexNum: obj.turnOutOrgPartyUsers.turnOutStatus.stepNum
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200) {
                            let url = "/turn_out/step/queryUserTurnOutOrgSteps"
                            let t = {
                                userId: obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.baseUserId,
                                processId: data.data[0].processId
                            }
                            $.post(url, t, function(data, status){
                                if (data.code == 200 && data.data != null && data.data != undefined) {
                                    obj.turnOutOrgPartyUsers.turnOutStatus.stepInfo = data.data[0];
                                }
                                obj.turnOutOrgPartyUsers.turnOutStatus.dialog = true;
                            })
                        }
                    })
                },
                openTurnOutOrgPartyStatusDialog(row) {
                    let obj = this;
                    var url = "/toou/user/queryTurnOutOrgPartyUsers";
                    var t = {
                        pageNum: obj.turnOutOrgPartyUsers.query.page.pageNum,
                        pageSize: obj.turnOutOrgPartyUsers.query.page.pageSize,
                        isHistory: 0,
                        idCard: row.idCard
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200) {
                            if (data.data != undefined && data.data != null && data.data.list.length == 1) {	
                                obj.turnOutOrgPartyUsers.turnOutStatus.userInfo = data.data.list[0];

                                var url;
                                var t;
                                if (obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.orgInfoName != null && 
                                    obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.orgInfoName != '') {
                                    url = "/org/turn_out/queryOrgTurnOutProcess";
                                    t = {
                                        orgId: obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.turnOutOrgId
                                    }
                                } else if(obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.otherOrgName != null && 
                                    obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.otherOrgName != '') {
                                    url = "/toou/user/queryToopOtherOrg";
                                    t = {}
                                } else {
                                    obj.$message({
                                        type: 'error',
                                        message: '出现错误'
                                    }); 
                                    return;
                                }
                                $.post(url, t, function(data, status){
                                    if (data.code == 200) {
                                        obj.turnOutOrgPartyUsers.turnOutStatus.process = data.data;
                                        //当前用户进行的流程
                                        let url = "/turn_out/step/queryUserTurnOutOrgSteps"
                                        let t = {
                                            userId: obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.baseUserId,
                                            isHistory: 0
                                        }
                                        $.post(url, t, function(_data, status){
                                            if (_data.code == 200) {
                                                let userProcess = _data.data;
                                                for (var i = 0; i < obj.turnOutOrgPartyUsers.turnOutStatus.process.length; i++) {	
                                                    //增加status属性，给步骤条设置状态
                                                    var _process = {id: null, name: null, orgId: null, processId: null, indexNum: null, isFile: 0, status: 'wait'};
                                                    //给已进行的步骤设置状态
                                                    if (userProcess != null && userProcess != undefined && userProcess[i] != null) {
                                                        _process.status = userProcess[i].stepStatus == 'wait' ? 'process' : userProcess[i].stepStatus;
                                                    }
                                                    _process.id = obj.turnOutOrgPartyUsers.turnOutStatus.process[i].id;
                                                    _process.name = obj.turnOutOrgPartyUsers.turnOutStatus.process[i].name;
                                                    _process.orgId = obj.turnOutOrgPartyUsers.turnOutStatus.process[i].orgId;
                                                    _process.processId = obj.turnOutOrgPartyUsers.turnOutStatus.process[i].processId;
                                                    _process.indexNum = obj.turnOutOrgPartyUsers.turnOutStatus.process[i].indexNum;
                                                    _process.isFile = obj.turnOutOrgPartyUsers.turnOutStatus.process[i].isFile;
                                                    obj.turnOutOrgPartyUsers.turnOutStatus.process[i] = _process;
                                                    //设置步骤条步骤，根据当前进行的步骤取得进行到第几步
                                                    if (_process.processId == obj.turnOutOrgPartyUsers.turnOutStatus.userInfo.nowStep) {
                                                        obj.turnOutOrgPartyUsers.turnOutStatus.stepNum = _process.indexNum;	//设置当前步骤，默认1
                                                        obj.turnOutOrgPartyUsers.turnOutStatus.stepNumNow = _process.indexNum;
                                                    }
                                                }
                                                obj.turnOutStatusStepSet('z');
                                            }
                                        })
                                    } 
                                })
                            } else {
                                obj.$message({
                                    type: 'info',
                                    message: '查询失败'
                                });  
                                return;
                            }
                        }
                        
                    })
                },
                queryTurnOutOrgPartyUsers() {
                    var obj = this;
                    var url = "/toou/user/queryTurnOutOrgPartyUsers";
                    var t = {
                        pageNum: obj.turnOutOrgPartyUsers.query.page.pageNum,
                        pageSize: obj.turnOutOrgPartyUsers.query.page.pageSize,
                        isHistory: 0,
                        idCard: obj.turnOutOrgPartyUsers.query.conditions.idCard
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200) {
                            if (data.data == undefined) {	
                                obj.turnOutOrgPartyUsers.query.page.pageNum = 1;
                                obj.turnOutOrgPartyUsers.query.page.total = 0;
                                obj.turnOutOrgPartyUsers.query.page.list = new Array();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
                            } else {
                                obj.turnOutOrgPartyUsers.query.page = data.data;
                            }
                        }
                        
                    })
                },
                getScreenHeightForPageSize() {	/*根据屏幕分辨率个性化每页数据记录数*/
                    var obj = this;
                    obj.commons.innerScreenHeight = window.innerHeight;
                    obj.turnOutOrgPartyUsers.query.page.pageSize = parseInt((obj.commons.innerScreenHeight - 100)/50);
                },
                getFileTypeImg(fileType) {
                    if (fileType == "doc") {
                        return "/view/pm/fileTypeImg/doc.png";
                    } else if (fileType == "docx") {
                        return "/view/pm/fileTypeImg/docx.png";
                    } else if (fileType == "jpg") {
                        return "/view/pm/fileTypeImg/jpg.png";
                    } else if (fileType == "xls" || fileType == "xlsx") {
                        return "/view/pm/fileTypeImg/xls.png";
                    } else if (fileType == "ppt" || fileType == "pptx") {
                        return "/view/pm/fileTypeImg/ppt.jpg";
                    } 
                }
            }
        })
    </script>
</html>