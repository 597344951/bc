<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>

<head>
    <meta charset="UTF-8" />
    <title>接入设备管理</title>
    <%@ include file="/include/base.jsp" %>
    <%@ include file="/include/element-ui.jsp" %>
    <link rel="stylesheet" href="index.css" />
</head>

<body>
    <div id="app">
        <el-container style="height: 100%;">
            <el-header>
                <h3>设备接入管理</h3>
            </el-header>
            <el-container>
                <el-aside width="300px">
                    <el-button type="primary" size="small" icon="el-icon-circle-plus-outline" @click="groupItem.show = true; groupItem.isRoot = true">添加根分组项</el-button>
                    <el-tree :data="group" draggable node-key="id" default-expand-all :expand-on-click-node="false" @node-click="onNodeClick">
                        <span class="tree-node" slot-scope="{ node, data }">
                            <span>{{ node.label }}</span>
                            <span class="operate">
                                <span title="添加子分组" class="edit el-icon-circle-plus-outline" @click="groupItem.show = true; groupItem.operateNode = node"></span>
                                <span title="移除分组" class="delete el-icon-delete" @click="deleteGroupItem(node, data)"></span>
                                <span v-if="!data.children" title="添加管理设备" class="plus el-icon-plus" @click="terminals.addSelectShow = true; groupItem.operateNode = node"></span>
                            </span>
                        </span>
                    </el-tree>
                </el-aside>
                <el-main>
                    <h3>已接入终端列表</h3>
                    <el-table :data="terminals.piece" stripe>
                        <el-table-column prop="name" label="名称"></el-table-column>
                        <el-table-column prop="online" label="在线状态">
                            <template slot-scope="scope">
                                {{scope.row.online == 1 ? '在线' : '离线'}}
                            </template>
                        </el-table-column>
                        <el-table-column label="所属组织">
                                <template slot-scope="scope">
                                    {{getOrgInfo(scope.row)}}
                                </template>
                        </el-table-column>
                        <el-table-column prop="type" label="类型">
                            <template slot-scope="scope">
                                {{terminals.screenType[scope.row.type_id]}}
                            </template>
                        </el-table-column>
                        <el-table-column prop="typ" label="触摸类型"></el-table-column>
                        <el-table-column prop="rev" label="屏幕比列"></el-table-column>
                        <el-table-column prop="ratio" label="分辨率"></el-table-column>
                        <el-table-column prop="ver" label="版本"></el-table-column>
                        <el-table-column prop="ip" label="IP"></el-table-column>
                        <el-table-column prop="operate" label="操作">
                            <template slot-scope="scope">
                                <el-button type="text" size="small" @click="deleteTerminalFromGroup(scope.row)">从分组中移除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-main>
            </el-container>
        </el-container>
        <el-dialog title="添加分组项" :visible.sync="groupItem.show" width="30%">
            <el-form :model="groupItem.props" :rules="groupItem.rules" ref="groupItemForm" label-width="100px">
                <el-form-item label="名 称" prop="label">
                    <el-input v-model="groupItem.props.label"></el-input>
                </el-form-item>
                <el-form-item label="描 述" prop="descripton">
                    <el-input type="textarea" v-model="groupItem.props.descripton"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="resetForm('groupItemForm')">重 置</el-button>
                <el-button type="primary" @click="addGroupItem">提 交</el-button>
            </span>
        </el-dialog>
        <el-dialog title="选择要添加的终端" :visible.sync="terminals.addSelectShow" width="80%">
            <el-table :data="terminals.all" stripe @selection-change="onTerminalSelected" ref="terminalSelectTable">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column prop="name" label="名称"></el-table-column>
                <el-table-column prop="online" label="在线状态">
                    <template slot-scope="scope">
                        {{scope.row.online == 1 ? '在线' : '离线'}}
                    </template>
                </el-table-column>
                <el-table-column prop="typeId" label="类型">
                    <template slot-scope="scope">
                        {{terminals.screenType[scope.row.typeId]}}
                    </template>
                </el-table-column>
                <el-table-column prop="typ" label="触摸类型"></el-table-column>
                <el-table-column prop="rev" label="屏幕比列"></el-table-column>
                <el-table-column prop="ratio" label="分辨率"></el-table-column>
                <el-table-column prop="ver" label="版本"></el-table-column>
                <el-table-column prop="ip" label="IP"></el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="terminals.addSelectShow = false">取 消</el-button>
                <el-button type="primary" @click="addTerminalToGroup">确 认</el-button>
            </span>
        </el-dialog>
    </div>
</body>
<script src="/components/xx-components.js"></script>
<script src="index.js"></script>

</html>