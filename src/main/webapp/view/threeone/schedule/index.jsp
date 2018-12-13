<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String meetingType = request.getParameter("meetingType");
    meetingType = meetingType == null ? "threeone" : meetingType;
%>
<html>

<head>
    <meta charset="UTF-8">
    <title>三会一课日程</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <link rel="stylesheet" href="index.css">
</head>

<body>
    <div id="app">
        <el-container>
            <el-header>
                <el-card shadow="always" class="banner">
                    <p v-if="meetingType == 'threeone'">三会一课</p>
                    <p v-if="meetingType == 'life'">组织生活会</p>
                    <div>
                        <el-button type="primary" icon="el-icon-plus" @click="scheduleAddFormShow = true; operate = 'add'">新建</el-button>
                    </div>
                </el-card>
            </el-header>
            <el-main>
                <timeline :events="scheduleList" @on-choose="onChoose" @on-edit="onEdit" @on-delete="onDelete"></timeline>
            </el-main>
        </el-container>
        <!-- 添加日程弹出框 -->
        <el-dialog title="添加会议" :visible.sync="scheduleAddFormShow">
            <el-form ref="scheduleForm" :model="schedule" :rules="scheduleRule" label-width="80px">
                <el-form-item label="类 型" prop="type">
                    <el-select v-model="schedule.type" placeholder="请选择会议类型" style="width: 100%;">
                        <el-option v-if="meetingType == 'threeone'" label="党员小组会" value="1"></el-option>
                        <el-option v-if="meetingType == 'threeone'" label="支部党员大会" value="2"></el-option>
                        <el-option v-if="meetingType == 'threeone'" label="支部委员会" value="3"></el-option>
                        <el-option v-if="meetingType == 'threeone'" label="党课" value="4"></el-option>
                        <el-option v-if="meetingType == 'life'" label="民主生活会" value="5"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="主 题" prop="name">
                    <el-input v-model="schedule.name" placeholder="会议主题"></el-input>
                </el-form-item>
                <el-form-item label="时 间" required>
                    <el-col :span="11">
                        <el-form-item prop="startTime">
                            <el-date-picker type="datetime" placeholder="选择开始时间" v-model="schedule.startTime" style="width: 100%;"></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col class="line" :span="2">-</el-col>
                    <el-col :span="11">
                        <el-form-item prop="endTime">
                            <el-date-picker type="datetime" placeholder="选择结束时间" v-model="schedule.endTime" style="width: 100%;"></el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-form-item>
                <el-form-item label="场 地" prop="place">
                    <el-input v-model="schedule.place" placeholder="会议场地"></el-input>
                </el-form-item>
                <el-form-item label="描 述" prop="description">
                    <el-input type="textarea" rows="5" v-model="schedule.description" placeholder="会议要求、议题、事项、描述等等"></el-input>
                </el-form-item>
                <el-form-item label="参加人员" prop="members">
                    <div class="members">
                        <el-tag v-for="member in schedule.members" :key="member.id" closable @close="onMemberRemove(member)">
                            {{member.name}}
                        </el-tag>
                        <el-button class="button-new-tag" size="small" type="danger" @click="loadOrgMembers">+ 添加参会人员</el-button>
                    </div>
                </el-form-item>
            </el-form>
            <!-- 选择参加人员 -->
            <el-dialog title="选择参加人员" :visible.sync="memberSelectShow" width="80%" append-to-body>
                <el-table size="mini" :data="member.list" @selection-change="onMemberSelect" ref="memberSelectTable">
                    <el-table-column type="selection" width="55">
                    </el-table-column>
                    <el-table-column prop="name" sortable label="姓名" width="150"></el-table-column>
                    <el-table-column prop="sex" sortable label="性别" width="150"></el-table-column>
                    <el-table-column prop="background" sortable label="政治背景" width="150"></el-table-column>
                    <el-table-column label="入党日期" sortable width="200">
                        <template slot-scope="scope">{{ scope.row.joinDate ? new Date(scope.row.joinDate).toLocaleDateString() : '' }}</template>
                    </el-table-column>
                    <el-table-column prop="address" label="联系地址"></el-table-column>
                </el-table>
                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="memberSelectShow = false">确 定</el-button>
                </span>
            </el-dialog>


            <span slot="footer" class="dialog-footer">
                <el-button @click="resetForm('scheduleForm')">重 置</el-button>
                <el-button type="primary" @click="submitForm('scheduleForm')">提 交</el-button>
            </span>
        </el-dialog>

    </div>
</body>
<script>
    const meetingType = '<%=meetingType%>'
</script>
<script src="/components/xx-components.js"></script>
<script src="index.js"></script>

</html>