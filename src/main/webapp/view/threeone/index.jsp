<%@ page contentType="text/html;charset=UTF-8" language="java"%> <%@taglib
prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>三会一课心得提交情况</title>
    <%@include file="/include/base.jsp" %> <%@include
    file="/include/element-ui.jsp" %>
    <link rel="stylesheet" href="index.css" />
  </head>

  <body>
    <div id="app">
      <el-container>
        <el-header>
          <el-card shadow="always" class="header">
            <p>三会一课心得提交情况</p>
            <el-pagination
              background
              layout="prev, pager, next"
              @current-change="onCurrentChange"
              :page-size="schedule.pageSize"
              :total="schedule.total"
            >
            </el-pagination>
          </el-card>
        </el-header>
        <el-main>
          <el-table :data="schedule.list" stripe>
            <el-table-column
              prop="typeName"
              label="类型"
              width="180"
            ></el-table-column>
            <el-table-column
              prop="name"
              label="主题"
              width="180"
            ></el-table-column>
            <el-table-column prop="description" label="描述"></el-table-column>
            <el-table-column prop="description" label="时间" width="320">
              <template slot-scope="scope">
                  {{scope.row.startTimeString}} - {{scope.row.endTimeString}}
              </template>
            </el-table-column>
            <el-table-column prop="operate" label="操作" width="180">
              <template slot-scope="scope">
                <el-button type="text" size="small">心得提交情况</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>
    </div>
  </body>
  <script src="/components/xx-components.js"></script>
  <script src="index.js"></script>
</html>
