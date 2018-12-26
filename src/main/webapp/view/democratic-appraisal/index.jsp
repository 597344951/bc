<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib
prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>

<head>
  <meta charset="UTF-8" />
  <title>民主评议会记录</title>
  <%@include file="/include/base.jsp" %>
  <%@include file="/include/element-ui.jsp" %>
  <link rel="stylesheet" href="/view/democratic-appraisal/index.css" />
</head>

<body>
  <div id="app">
    <el-tabs v-model="activeTab" @tab-click="onTabClick">
      <el-tab-pane :label="label.person" name="person">
        <el-container>
          <el-header>
            <el-card class="header">
              <p>{{label.person}}</p>
              <el-pagination background layout="prev, pager, next" @current-change="onCurrentChangeParticipated"
                :page-size="participated.pageSize" :total="participated.total">
              </el-pagination>
            </el-card>
          </el-header>
          <el-main>
            <el-table :data="participated.list" stripe height="80%">
              <el-table-column prop="typeName" label="类型" width="180"></el-table-column>
              <el-table-column prop="name" label="主题" width="180"></el-table-column>
              <el-table-column prop="description" label="描述"></el-table-column>
              <el-table-column prop="description" label="时间" width="320">
                <template slot-scope="scope">
                  {{scope.row.startTimeString}} - {{scope.row.endTimeString}}
                </template>
              </el-table-column>
              <el-table-column prop="operate" label="评议" width="180">
                <template slot-scope="scope">
                  <span v-if="scope.row.isParticipate != 0">{{appraisal[scope.row.isParticipate]}}</span>
                  <el-select v-show="scope.row.isParticipate == 0" size="mini" v-model="scope.row.isParticipate" @change="scheduleSign(scope.row)">
                    <el-option label="未评议" value="0"></el-option>
                    <el-option label="不合格" value="1"></el-option>
                    <el-option label="合格" value="2"></el-option>
                    <el-option label="优秀" value="3"></el-option>
                  </el-select>
                </template>
              </el-table-column>
            </el-table>
          </el-main>
        </el-container>
      </el-tab-pane>
      <shiro:hasAnyRoles name="plant_admin, org_admin">
        <el-tab-pane :label="label.all" name="all">
          <el-container>
            <el-header>
              <el-card shadow="always" class="header">
                <p>{{label.all}}</p>
                <el-pagination background layout="prev, pager, next" @current-change="onCurrentChange" :page-size="schedule.pageSize"
                  :total="schedule.total">
                </el-pagination>
              </el-card>
            </el-header>
            <el-main>
              <el-table :data="schedule.list" stripe height="80%">
                <el-table-column prop="typeName" label="类型" width="180"></el-table-column>
                <el-table-column prop="name" label="主题" width="180"></el-table-column>
                <el-table-column prop="description" label="描述"></el-table-column>
                <el-table-column prop="description" label="时间" width="320">
                  <template slot-scope="scope">
                    {{scope.row.startTimeString}} - {{scope.row.endTimeString}}
                  </template>
                </el-table-column>
                <el-table-column prop="operate" label="操作" width="250">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="loadScheduleParticipant(scope.row)">评议管理</el-button>
                    <el-button type="text" size="small" @click="summary.belongs = scope.row; summary.show = true">添加附件</el-button>
                    <el-button type="text" size="small" @click="onSummaryView(scope.row)">查看附件</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-main>
          </el-container>
        </el-tab-pane>
      </shiro:hasAnyRoles>
    </el-tabs>

    <!-- 上传评议附件 -->
    <el-dialog title="添加附件内容" :visible.sync="summary.show">
      <el-form ref="summaryForm" :model="summary.props" label-width="80px">
        <!-- <el-form-item label="附件描述" prop="summary">
          <el-input type="textarea" rows="10" v-model="summary.props.summary" placeholder="会议纪要内容......"></el-input>
        </el-form-item> -->
        <el-form-item label="附件内容" prop="annex">
          <el-upload ref="summaryAnnexUpload" action="${mediaServe}/upload" multiple :on-success="onSummaryAnnexUploadSuccess"
            :on-remove="onSummaryAnnexRemove">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">可上传文档或者图片</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetSummary()">重 置</el-button>
        <el-button type="primary" @click="conmmitSummary()">提 交</el-button>
      </span>
    </el-dialog>
    <!-- 查看附件 -->
    <el-dialog title="查看附件" :visible.sync="summary.current.show">
      <el-card class="summary" v-for="summary in summary.current.data" :key="summary.id">
        <div slot="header" class="clearfix">
          <span>{{new Date(summary.addDate).toLocaleString()}}</span>
        </div>
        <!-- <p v-if="summary.summary">{{summary.summary}}</p> -->
        <h3 v-for="annex in summary.annexObject" :key="annex.uid"><a href="#" @click="onAnnexView(annex)">{{annex.name}}</a></h3>
      </el-card>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="summary.current.show = false">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 评议管理 -->
    <el-dialog title="参加人员评议记录" :visible.sync="participant.show">
      <el-alert type="error" :closable="false" :title="participant.countStr"></el-alert>
      <el-table :data="participant.list" size="mini" style="width: 100%">
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="isParticipate" label="评议结果">
          <template slot-scope="scope">
            <el-select size="mini" v-model="scope.row.isParticipate">
              <el-option label="未评议" value="0"></el-option>
              <el-option label="不合格" value="1"></el-option>
              <el-option label="合格" value="2"></el-option>
              <el-option label="优秀" value="3"></el-option>
            </el-select>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="participant = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="conmmitScheduleParticipantSign">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</body>
<script>
  const mediaServe = '${mediaServe}'
  const meetingType = 'democratic-appraisal'
</script>
<script src="/components/xx-components.js"></script>
<script src="/view/democratic-appraisal/index.js"></script>

</html>
