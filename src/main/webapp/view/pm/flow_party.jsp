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
        <title>流动党员管理</title>
        <%@include file="/include/head_notbootstrap.jsp"%>
        <script type="text/javascript" src="/json/address-pca.json"></script>
        <style>
            body {
                margin: 0px;
                padding: 10px;
            }
            .el-input.el-input--small {
                width: 230px;
            }
            .el-textarea.el-input--small {
                width: 230px;
            }
            .el-tree.el-tree--highlight-current {
                width: 230px;
            }
            .el-picker-panel.el-date-picker.el-popper.has-time .el-input.el-input--small {
                width: 140px;
            }
        </style>
    </head>

    <body>
        <div id="app">
            <div>
                <span class="margin-left-10">
                    <el-button size="small" type="primary" @click="open_inser_flow_party_dialog">
                        添加流动党员
                    </el-button>
                </span>
                <el-popover
                    class="margin-left-10"
                    placement="bottom" 
                    width="230" 
                    trigger="click" >
                    <el-button size="small" type="primary" slot="reference">
                        <i class="el-icon-search"></i>
                        搜索用户
                    </el-button>
                    <div>
                        <el-input size="small" clearable
                            @change="query_flow_party"
                            v-model="query_condition.name" placeholder="请输入用户名">
                        </el-input>
                    </div>
                </el-popover>
                <span style="float: right;">
                    <el-pagination
                          layout="total, prev, pager, next, jumper" 
                          @current-change="query_flow_party"
                          :current-page.sync="flow_party_pager.pageNum"
                          :page-size.sync="flow_party_pager.pageSize"
                          :total="flow_party_pager.total">
                    </el-pagination>
                </span>
            </div>
            <div>
                <el-table size="small" :data="flow_party_pager.list" stripe>
                    <el-table-column prop="userId" label="ID"></el-table-column>
                    <el-table-column prop="name" label="姓名"></el-table-column>
                    <el-table-column prop="sex" label="性别"></el-table-column>
                    <el-table-column prop="nation" label="民族"></el-table-column>
                    <el-table-column prop="mobilePhone" label="联系电话"></el-table-column>
                    <el-table-column label="党员类型">
                        <template slot-scope="scope">
                            <span>
                                {{scope.row.partyType == 1 ? "正式党员" : scope.row.partyType == 0 ? 
                                "预备党员" : "非党员"}}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column label="党员状态">
                        <template slot-scope="scope">
                            <span>
                                {{scope.row.partyStatus == 1 ? "正常" : scope.row.partyType == 0 ? 
                                "停止党籍" : scope.row.partyStatus == -1 ? "流动党员" : "未知"}}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="flowOrgInfoName" label="流动组织"></el-table-column>
                    <el-table-column prop="flowTime" label="流动时间"></el-table-column>
                    <el-table-column label="更多">
                        <template slot-scope="scope">
                            <el-popover
                                placement="top-start"
                                title="流动原因"
                                trigger="focus"
                                :content="scope.row.reason">
                                <el-button size="small" type="text" slot="reference">查看</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                    <el-table-column label="联系记录">
                        <template slot-scope="scope">
                            <el-popover
                                placement="top-start"
                                width="450"
                                trigger="click">
                                <el-table size="small" :data="flow_party_records">
                                    <el-table-column width="220px" label="联系状态">
                                        <template slot-scope="_scope">
                                            <span :style="_scope.row.contact == 1 ? 'color: green;' : 'color: red;'">
                                                {{_scope.row.contact == 1 ? "联系成功" : "联系失败"}}
                                            </span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column width="220px" property="contactTime" label="联系时间"></el-table-column>
                                </el-table>
                                <el-button @click="query_flowParty_records(scope.row)" size="small" type="text" slot="reference">查看</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button 
                                @click="add_contact_record(scope.row)" 
                                size="small" type="text">增加联系记录</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            
            <el-dialog @close="" title="联系记录" :visible.sync="contact_record.main_dialog" width="400px">
                <div style="margin: 5px;">
                    <el-form label-position="left" size="small" :model="contact_record.contact_record_form" 
                        status-icon :rules="contact_record.contact_record_form_form_rules" 
                        ref="contact_record_form" label-width="80px">
                        
                        <div>
                            <el-form-item label="联系状态" prop="contact">
                                <el-select clearable v-model="contact_record.contact_record_form.contact">
                                    <el-option label="联系成功" :value="1"></el-option>
                                    <el-option label="联系失败" :value="0"></el-option>
                                </el-select>
                            </el-form-item>
                        </div>
                        <div class="contactTime">
                            <el-form-item label="联系时间" prop="contactTime">
                                <el-date-picker
                                    v-model="contact_record.contact_record_form.contactTime"
                                    type="datetime"
                                    format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="选择日期">
                                </el-date-picker>
                            </el-form-item>
                        </div>
                        
                    </el-form>
                </div>
                <div style="margin: 5px;">
                    <el-button size="small" type="primary" @click="inser_contact_record_submit">添加记录</el-button>
                    <el-button size="small" @click="reset_contact_record_submit">重置</el-button>
                </div>
            </el-dialog>

            <!-- 弹窗 -->
            <el-dialog @close="" title="添加流动党员信息" :visible.sync="insert_flow_party.main_dialog" width="400px">
                <div>
                    <div style="margin-left: 5px;color: red;">
                        <p>外来流动党员在此处登记信息，平台有记录的党员需要流动请去“党员信息”里填写流动记录</p>
                    </div>
                    <div style="margin: 5px;">
                        <el-form label-position="left" size="small" :model="insert_flow_party.insert_flow_party_form" 
                            status-icon :rules="insert_flow_party.insert_flow_party_form_rules" 
                            ref="insert_flow_party_form" label-width="80px">
                            <div>
                                <el-form-item label="姓名" prop="name">
                                    <el-input clearable v-model="insert_flow_party.insert_flow_party_form.name" placeholder="姓名">
                                    </el-input>
                                </el-form-item>
                            </div>
                            <div>
                                <el-form-item label="身份证" prop="idCard">
                                    <el-input clearable v-model="insert_flow_party.insert_flow_party_form.idCard" placeholder="身份证">
                                    </el-input>
                                </el-form-item>
                            </div>
                            <div>
                                <el-form-item label="性別" prop="sex">
                                    <el-select clearable v-model="insert_flow_party.insert_flow_party_form.sex">
                                        <el-option label="女" value="女"></el-option>
                                        <el-option label="男" value="男"></el-option>
                                    </el-select>
                                </el-form-item>
                            </div>
                            <div>
                                <el-form-item label="联系方式" prop="mobilePhone">
                                    <el-input clearable v-model="insert_flow_party.insert_flow_party_form.mobilePhone" placeholder="11位手机号码"></el-input>
                                </el-form-item>
                            </div>
                            <div>
                                <el-form-item label="流动区域" prop="flowAddress_pca">
                                    <el-cascader clearable :props="insert_flow_party.address_prop"
                                        v-model="insert_flow_party.insert_flow_party_form.flowAddress_pca"
                                        separator="/"
                                        placeholder="可搜索地点" :options="insert_flow_party.address_pca" 
                                        filterable >
                                    </el-cascader>
                                </el-form-item>
                            </div>
                            <div>
                                <el-form-item label="详细地址" prop="flowAddressDetail">
                                    <el-input clearable v-model="insert_flow_party.insert_flow_party_form.flowAddressDetail" placeholder="11位手机号码"></el-input>
                                </el-form-item>
                            </div>
                            <div>
                                <el-form-item label="党员类型" prop="type">
                                    <el-select clearable v-model="insert_flow_party.insert_flow_party_form.type">
                                        <el-option label="预备党员" :value="0"></el-option>
                                        <el-option label="正式党员" :value="1"></el-option>
                                    </el-select>
                                </el-form-item>
                            </div>
                            <div>
                                <el-form-item label="归属组织" prop="orgInfoId">
                                    <el-select @change="query_org_duty" clearable v-model="insert_flow_party.insert_flow_party_form.orgInfoId">
                                        <el-option
                                            v-for="item in insert_flow_party.orgInfos"
                                            :key="item.orgInfoId"
                                            :label="item.orgInfoName"
                                            :value="item.orgInfoId">
                                            <span style="float: left; margin-right: 15px;">{{item.orgInfoName}}</span>
                                            <span style="float: right;">{{item.orgInfoId}}</span>
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </div>
                            <div>
                                <el-form-item label="党员角色" prop="orgRltDutyIds">
                                    <el-tree :default-expand-all="true" 
                                        node-key="id" 
                                        ref="ref_org_duty_tree"
                                        show-checkbox 
                                        :expand-on-click-node="false" 
                                        :highlight-current="true" 
                                        :data="insert_flow_party.org_duty_tree" 
                                        :props="insert_flow_party.org_duty_tree_props" 
                                        :check-strictly="true" >
                                        </el-tree>
                                </el-form-item>
                            </div>
                            <div>
                                <el-form-item label="流动原因" prop="reason">
                                    <el-input
                                        type="textarea"
                                        :autosize="{ minRows: 3, maxRows: 5}"
                                        placeholder="请输入流动原因"
                                        v-model="insert_flow_party.insert_flow_party_form.reason">
                                    </el-input>
                                </el-form-item>
                            </div>
                            
                        </el-form>
                    </div>
                    <div style="margin: 5px;">
                        <el-button size="small" type="primary" @click="inser_flow_party_submit">添加流动党员</el-button>
                        <el-button size="small" @click="reset_flow_party_form">重置信息</el-button>
                    </div>
                </div>
            </el-dialog>
        </div>
    </body>

    <script>
        var appInstince = new Vue({
            el: '#app',
            data: {
                flow_party_pager: {
                    pageNum: 1,		/* 当前页 */
                    pageSize: 10,	/* 默认页面大小 */
                    total: 0,
                    list: []
                },
                flow_party_records: [],
                query_condition: {
                    name: null
                },
                insert_flow_party: {
                    main_dialog: false,
                    orgInfos: null,
                    org_duty_tree: null,
                    address_prop: {	/* 地址prop */
                        value: "name",
                        label: "name",
                        children: "children"
                    },
                    org_duty_tree_props: {
                        children: 'children',
                        label: function(_data, node){
                            return _data.data.orgDutyName;
                        }
                    },
                    address_pca: pca,	/* 省市区三级联动数据 */
                    insert_flow_party_form: {
                        name: null,
                        sex: null,
                        idCard: null,
                        mobilePhone: null,
                        type: null,
                        orgInfoId: null,
                        flowAddress_pca: null,
                        flowAddressDetail: null,
                        flowAddressProvince: null,
                        flowAddressCity: null,
                        flowAddressArea: null,
                        reason: null,
                        orgRltDutyId: null
                    },
                    insert_flow_party_form_rules: {
                        orgRltDutyIds: [
                            {
                                validator: function(rule, value, callback){
                                    let checkedKeys = appInstince.$refs.ref_org_duty_tree.getCheckedKeys(false);
                                    if (checkedKeys == null || checkedKeys.length == 0) {
                                        callback(new Error('请选择该段党员的角色'));
                                    } else {
                                        callback();
                                    }
                                }
                            }
                        ],
                        flowAddressDetail: [
                            { required: true, message: '请输入详细区域!' }
                        ],
                        flowAddress_pca: [
                            { required: true, message: '请选择流动区域!' }
                        ],
                        name: [
                            { required: true, message: '请输入身份姓名!', trigger: 'blur' },
                            { pattern: /^[\u4e00-\u9fa5.·]+$/, message: '请输入正确的姓名!'}	
                        ],
                        mobilePhone: [
                            { required: true, message: '请输入手机号码!', trigger: 'blur' },
                            { pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: '请输入11位正确的手机号码!'}
                        ],
                        orgInfoId: [
                            { required: true, message: '请选择组织!' }
                        ],
                        sex: [
                            { required: true, message: '请选择性别!' }
                        ],
                        type: [
                            { required: true, message: '请选择党员类型' }
                        ],
                        idCard: [
                            { required: true, message: '请输入15或18位身份证号码!', trigger: 'blur' },
                            { pattern: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/, message: '请输入正确的身份证号码!'},
                            { 	/* 重复身份证号码验证 */
                                validator: function(rule, value, callback){
                                    setTimeout(function() {
                                        var url = "/base/userInfo/queryBaseUserInfos";
                                        var t = {
                                            idCard: value,
                                            pageNum: 1,
                                            pageSize: 1,
                                        }
                                        $.post(url, t, function(data, status){
                                            if (data.code == 200) {
                                                if (data.data == undefined) {
                                                    callback();
                                                } else {
                                                    callback(new Error('该身份证用户在系统中存在!'));
                                                }
                                            } else {
                                                alert("添加出错");
                                                appInstince.insert_flow_party.main_dialog = false;
                                            }
                                        })
                                    }, 1);
                                }
                            }
                        ]
                    }
                },
                contact_record: {
                    main_dialog: false,
                    contact_record_form: {
                        contact: null,
                        fpId: null,
                        contactTime: null
                    },
                    contact_record_form_form_rules: {
                        contact: [
                            { required: true, message: '请选择联系状态' }
                        ],
                        contactTime: [
                            { required: true, message: '请选择联系时间' }
                        ]
                    }
                }
            },
            created: function () {
                this.set_page_size();
            },
            mounted: function () {
                this.query_flow_party();
            },
            methods: {
                inser_contact_record_submit() {
                    let obj = this;
                    this.$refs.contact_record_form.validate( function(valid) {
                        if (valid) {
                            var url = "/flow_party/record/insertFlowPartyRecords";
                            var t = obj.contact_record.contact_record_form;
                            $.post(url, t, function(data, status){
                                if (data.code == 200) {	/*添加成功*/
                                    toast('添加成功',data.msg,'success');
                                    obj.contact_record.main_dialog = false;
                                    obj.reset_contact_record_submit();
                                }

                            })
                        } else {
                            console.log('error submit!!');
                            return false;
                        }
                    });
                },
                reset_contact_record_submit() {
                    let obj = this;
                    obj.$refs.contact_record_form.resetFields();
                },
                add_contact_record(row) {
                    let obj = this;
                    obj.contact_record.contact_record_form.fpId = row.id;
                    obj.contact_record.main_dialog = true;
                },
                query_org_duty() {	/*设置组织id*/
                    var obj = this;
                    
                    url = "/org/duty/queryOrgDutyTreeForOrgInfo";
                    t = {
                        orgDutyOrgInfoId: obj.insert_flow_party.insert_flow_party_form.orgInfoId
                    }
                    $.post(url, t, function(datas, status){
                        if (datas.code == 200) {
                            if (datas.data != undefined) {
                                obj.insert_flow_party.org_duty_tree = datas.data;
                                obj.org_duty_add_id(obj.insert_flow_party.org_duty_tree);
                            } else {
                                obj.insert_flow_party.org_duty_tree = [];
                            }
                        }
                        
                    })
                },
                org_duty_add_id(menuTrees){	/* 向树里添加id属性，方便设置node-key */
                    var obj = this;
                    if(menuTrees != null) {
                        for (var i = 0; i < menuTrees.length; i++) {
                            var menuTree = menuTrees[i];
                            menuTree.id = menuTree.data.orgDutyId;
                            
                            obj.org_duty_add_id(menuTree.children);
                        }
                    }
                },
                reset_flow_party_form() {
                    var obj = this;
				    obj.$refs.insert_flow_party_form.resetFields();
                    obj.insert_flow_party.org_duty_tree = [];
                },
                inser_flow_party_submit() {
                    let obj = this;
                    this.$refs.insert_flow_party_form.validate( function(valid) {
                        if (valid) {
                            let checkedKeys = appInstince.$refs.ref_org_duty_tree.getCheckedKeys(false);
                            if (checkedKeys.length != 1) {
                                toast('提示', '只能勾选一个角色', 'error');
                                return;
                            }
                            obj.insert_flow_party.insert_flow_party_form.orgRltDutyId = checkedKeys[0];

                            var url = "/flow/party/insertFlowPartyUserInfo";
                            if (obj.insert_flow_party.insert_flow_party_form.flowAddress_pca != null && 
                                obj.insert_flow_party.insert_flow_party_form.flowAddress_pca != undefined &&
                                obj.insert_flow_party.insert_flow_party_form.flowAddress_pca.length == 3) {
                                obj.insert_flow_party.insert_flow_party_form.flowAddressProvince = obj.insert_flow_party.insert_flow_party_form.flowAddress_pca[0];
                                obj.insert_flow_party.insert_flow_party_form.flowAddressCity = obj.insert_flow_party.insert_flow_party_form.flowAddress_pca[1];
                                obj.insert_flow_party.insert_flow_party_form.flowAddressArea = obj.insert_flow_party.insert_flow_party_form.flowAddress_pca[2];
                            }
                            var t = obj.insert_flow_party.insert_flow_party_form;
                            $.post(url, t, function(data, status){
                                if (data.code == 200) {	/*添加成功*/
                                    toast('添加成功',data.msg,'success');
                                    obj.insert_flow_party.main_dialog = false;
                                    obj.query_flow_party();
                                    obj.reset_flow_party_form();
                                }

                            })
                        } else {
                            console.log('error submit!!');
                            return false;
                        }
                    });
                },
                open_inser_flow_party_dialog() {
                    let obj = this;
                    
                    let url = "/org/ifmt/queryOrgInfosSelect";
                    let t = {}
                    $.post(url, t, function(data, status){	//选择加入的党组织
                        if (data.code == 200) {
                            obj.insert_flow_party.orgInfos = data.data;
                            obj.insert_flow_party.main_dialog = true;
                        } 
                    })
                },
                set_page_size() {	/*根据屏幕分辨率个性化每页数据记录数*/
                    var obj = this;
                    var height = window.innerHeight;
                    //100是搜索栏高度，50是表格每条数据所占的高度
                    obj.flow_party_pager.pageSize = parseInt((height - 100) / 50);
                },
                initPager() {	/* 初始化页面数据 */
                    var obj = this;
                    obj.flow_party_pager.pageNum = 1;
                    obj.flow_party_pager.total = 0;
                    obj.flow_party_pager.list = new Array();
                },
                query_flow_party() {
                    var obj =  this;
                    var url = "/flow/party/queryFlowPartys";
                    var t = {
                        pageNum: obj.flow_party_pager.pageNum,
                        pageSize: obj.flow_party_pager.pageSize,
                        name: obj.query_condition.name
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200) {
                            if (data.data == undefined) {	
                                obj.initPager();/* 没有查询到数据时，初始化页面信息，使页面正常显示 */
                                toast('提示',"没有查询到信息",'error');
                            } else {
                                obj.flow_party_pager = data.data;
                            }
                        }
                        
                    })
                },
                query_flowParty_records(row) {
                    var obj =  this;
                    var url = "/flow_party/record/queryFlowPartyRecords";
                    var t = {
                        fpId: row.id
                    }
                    $.post(url, t, function(data, status){
                        if (data.code == 200 && data.data != undefined && data.data != null && data.data.length > 0) {
                            obj.flow_party_records = data.data;
                        } else {
                            obj.flow_party_records = []
                        }
                        
                    })
                }
            }
        })
    </script>
</html>