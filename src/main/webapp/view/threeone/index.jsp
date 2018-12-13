<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib
prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>

<head>
  <meta charset="UTF-8" />
  <title>三会一课心得提交情况</title>
  <%@include file="/include/base.jsp" %>
  <%@include file="/include/element-ui.jsp" %>
  <link rel="stylesheet" href="/view/threeone/index.css" />
</head>

<body>
  <div id="app">
    <el-tabs v-model="activeTab" @tab-click="onTabClick">
      <el-tab-pane :label="label.person" name="person">
        <el-container>
          <el-header>
            <el-card class="header">
              <p>{{label.person}}</p>
              <el-pagination background layout="prev, pager, next" @current-change="onCurrentChangeParticipant"
                :page-size="participant.pageSize" :total="participant.total">
              </el-pagination>
            </el-card>
          </el-header>
          <el-main>
            <el-table :data="participant.list" stripe height="80%">
              <el-table-column prop="typeName" label="类型" width="180"></el-table-column>
              <el-table-column prop="name" label="主题" width="180"></el-table-column>
              <el-table-column prop="description" label="描述"></el-table-column>
              <el-table-column prop="description" label="时间" width="320">
                <template slot-scope="scope">
                  {{scope.row.startTimeString}} - {{scope.row.endTimeString}}
                </template>
              </el-table-column>
              <el-table-column prop="operate" label="操作" width="180">
                <template slot-scope="scope">
                  会议心得（
                  <el-button type="text" size="small" @click="onLearnedView(scope.row)">查看</el-button>
                  <el-button type="text" size="small" @click="learned.belongs = scope.row; learned.show = true">提交</el-button>
                  ）
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
                <el-table-column prop="operate" label="操作" width="200">
                  <template slot-scope="scope">
                    会议纪要（
                    <el-button type="text" size="small" @click="onSummaryView(scope.row)">查看</el-button>
                    <el-button type="text" size="small" @click="summary.belongs = scope.row; summary.show = true">提交</el-button>
                    ）
                  </template>
                </el-table-column>
              </el-table>
            </el-main>
          </el-container>
        </el-tab-pane>
      </shiro:hasAnyRoles>
    </el-tabs>
    <!-- 会议纪要添加框 -->
    <el-dialog title="添加会议纪要" :visible.sync="summary.show">
      <el-form ref="summaryForm" :model="summary.props" label-width="80px">
        <el-form-item label="会议纪要" prop="summary">
          <el-input type="textarea" rows="10" v-model="summary.props.summary" placeholder="会议纪要内容......"></el-input>
        </el-form-item>
        <el-form-item label="文档附件" prop="annex">
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
    <!-- 心得体会添加框 -->
    <el-dialog title="添加会议心得" :visible.sync="learned.show">
      <el-form ref="learnedForm" :model="learned.props" label-width="80px">
        <el-form-item label="会议心得" prop="learned">
          <el-input type="textarea" rows="10" v-model="learned.props.learned" placeholder="会议纪要内容......"></el-input>
        </el-form-item>
        <el-form-item label="文档附件" prop="annex">
          <el-upload ref="learnedAnnexUpload" action="${mediaServe}/upload" multiple :on-success="onLearnedAnnexUploadSuccess"
            :on-remove="onLearnedAnnexRemove">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">可上传文档或者图片</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetLearned">重 置</el-button>
        <el-button type="primary" @click="conmmitLearned">提 交</el-button>
      </span>
    </el-dialog>
    <!-- 查看会议纪要 -->
    <el-dialog title="会议纪要查看" :visible.sync="summary.current.show">
      <el-card class="summary" v-for="summary in summary.current.data" :key="summary.id">
        <div slot="header" class="clearfix">
          <span>{{new Date(summary.addDate).toLocaleString()}}</span>
        </div>
        <p v-if="summary.summary">{{summary.summary}}</p>
        <h3 v-for="annex in summary.annexObject" :key="annex.uid"><a href="#" @click="onAnnexView(annex)">{{annex.name}}</a></h3>
      </el-card>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="summary.current.show = false">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 查看会议纪要 -->
    <el-dialog title="会议心得查看" :visible.sync="learned.current.show">
      <el-card class="summary" v-for="learned in learned.current.data" :key="learned.id">
        <div slot="header" class="clearfix">
          <span>{{new Date(learned.addDate).toLocaleString()}}</span>
        </div>
        <p v-if="learned.learned">{{learned.learned}}</p>
        <h3 v-for="annex in learned.annexObject" :key="annex.uid"><a href="#" @click="onAnnexView(annex)">{{annex.name}}</a></h3>
      </el-card>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="learned.current.show = false">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</body>
<script>
  const mediaServe = '${mediaServe}'
  const meetingType = '${meetingType}'
</script>
<script src="/components/xx-components.js"></script>
<script src="/view/threeone/index.js"></script>

</html>