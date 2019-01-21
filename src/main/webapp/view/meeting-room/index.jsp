<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>

<head>
  <meta charset="UTF-8" />
  <title>会议室</title>
  <%@include file="/include/base.jsp" %>
  <%@include file="/include/element-ui.jsp" %>
  <link rel="stylesheet" href="/view/meeting-room/index.css" />
</head>

<body>
  <div id="app">
    <el-container>
      <el-header>
        <el-card class="header" shadow="never">
          <p>会议室</p>
          <el-pagination background layout="prev, pager, next" @current-change="onCurrentChangeAtRooms" :page-size="rooms.pageSize"
            :total="rooms.total">
          </el-pagination>
        </el-card>
      </el-header>
      <el-main>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="addRoom">添加会议室</el-button>
        <el-table :data="rooms.list" stripe height="80%">
          <el-table-column prop="park" label="园区"></el-table-column>
          <el-table-column prop="building" label="楼宇"></el-table-column>
          <el-table-column prop="floor" label="楼层"></el-table-column>
          <el-table-column prop="number" label="编号"></el-table-column>
          <el-table-column prop="operate" label="操作" width="200">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="editRoom(scope.row)">编辑</el-button>
              <el-button type="text" size="small" @click="listOrder(scope.row)">预订记录</el-button>
              <el-button type="text" size="small" @click="deleteRoom(scope.row)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
    <!-- 添加会议室 -->
    <el-dialog title="添加会议室" :visible.sync="room.show" width="40%">
      <el-form ref="roomForm" :model="room.props" :rules="room.rules" label-width="80px" :status-icon="true">
        <el-form-item label="园 区" prop="park">
          <el-input v-model="room.props.park" placeholder="园区" clearable></el-input>
        </el-form-item>
        <el-form-item label="楼 宇" prop="building">
          <el-input v-model="room.props.building" placeholder="楼宇" clearable></el-input>
        </el-form-item>
        <el-form-item label="楼 层" prop="floor">
          <el-input v-model="room.props.floor" placeholder="楼层" clearable></el-input>
        </el-form-item>
        <el-form-item label="编 号" prop="number">
          <el-input v-model="room.props.number" placeholder="编号" clearable></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetRoomForm()">重 置</el-button>
        <el-button type="primary" @click="submitRoomForm()">提 交</el-button>
      </span>
    </el-dialog>
    <el-dialog title="会议室预订情况" :visible.sync="orders.show" width="60%">
      <el-alert v-for="(order, index) in orders.list" :key="index"
        :title="order.date"
        :description="order.meeting"
        type="error"
        :closable="false">
      </el-alert>
      <el-alert v-if="orders.list.length == 0"
        title="没有会议室预订记录 ..."
        type="info"
        :closable="false">
      </el-alert>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="orders.show = false">确 认</el-button>
      </span>
    </el-dialog>
  </div>
</body>
<script src="/components/xx-components.js"></script>
<script src="/view/meeting-room/index.js"></script>

</html>
