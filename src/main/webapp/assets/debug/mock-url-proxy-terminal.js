//Mock 拦截url地址配置
//
var datas = [{
    "oid": 0,
    "name": "WIN-OUNLE84KUAN",
    "id": "2",
    "code": "f3b55ceee1aee2025a381a6886adffc3",
    "type_id": "1",
    "res_time": "2018-05-16 10:02:40",
    "online": "1",
    "last_time": "Thu Jun 07 13:49:42 CST 2018",
    "ip": "192.168.1.42",
    "mac": "1c:87:2c:78:4f:85",
    "sys": "666666666",
    "size": "21寸",
    "ratio": "1440*900",
    "rev": "横屏",
    "ver": "V3.0.0.180510_B777",
    "typ": "非触摸",
    "tel": "6666666666",
    "addr": "666666666",
    "gis": "666666666",
    "warranty": "二年",
    "loc": "街道"
  },
  {
    "oid": 1,
    "name": "DESKTOP-99T4O79",
    "id": "1",
    "code": "671ab55be30695ce83eb01c5a2caf2e4",
    "type_id": "1",
    "res_time": "2018-05-15 13:51:57",
    "online": "0",
    "last_time": "Thu Jun 07 13:46:50 CST 2018",
    "ip": "192.168.1.48",
    "mac": "40:f0:2f:b3:2f:5f",
    "sys": null,
    "size": "21寸",
    "ratio": "1920*1080",
    "rev": "横屏",
    "ver": "V3.0.0.180510_B777",
    "typ": "非触摸",
    "tel": null,
    "addr": null,
    "gis": null,
    "warranty": "一年",
    "loc": "园区"
  }
]
let logs = [{
  "logId": 13061,
  "username": "develop",
  "operation": "得到党员证件照",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.PartyUserInfoController.getPartyUserInfoIdPhoto()",
  "date": "2018-06-22T09:56:56.000+0800",
  "msg": "记录得到党员证件照 执行",
  "costtime": 6,
  "type": 0,
  "params": "[1]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13060,
  "username": "develop",
  "operation": "查询组织信息",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.OrganizationInformationController.queryOrgInfosForMap()",
  "date": "2018-06-22T09:56:55.000+0800",
  "msg": "记录查询组织信息 执行",
  "costtime": 79,
  "type": 0,
  "params": "[{\"pageNum\":\"1\",\"pageSize\":\"9\",\"orgInfoTypeId\":\"\",\"orgInfoName\":\"\",\"orgInfoCommitteeProvince\":\"\",\"orgInfoCommitteeCity\":\"\",\"orgInfoCommitteeArea\":\"\"},1,9]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13059,
  "username": "develop",
  "operation": "查询组织类型",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.OrganizationTypeController.queryOrgTypesNotPage()",
  "date": "2018-06-22T09:56:55.000+0800",
  "msg": "记录查询组织类型 执行",
  "costtime": 5,
  "type": 0,
  "params": "[{}]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13058,
  "username": "develop",
  "operation": "查询省份信息",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.OrganizationInformationController.queryOrgInfosCommitteeProvince()",
  "date": "2018-06-22T09:56:55.000+0800",
  "msg": "记录查询省份信息 执行",
  "costtime": 3,
  "type": 0,
  "params": "[{\"orgInfoCommitteeAddress\":\"null-null-null-null\"}]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13057,
  "username": "develop",
  "operation": "查询一线情况",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.FirstLineTypeController.queryFirstLineTypes()",
  "date": "2018-06-22T09:56:55.000+0800",
  "msg": "记录查询一线情况 执行",
  "costtime": 6,
  "type": 0,
  "params": "[{}]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13056,
  "username": "develop",
  "operation": "查询党员信息",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.PartyUserInfoController.queryPartyUserInfos()",
  "date": "2018-06-22T09:56:55.000+0800",
  "msg": "记录查询党员信息 执行",
  "costtime": 19,
  "type": 0,
  "params": "[{\"pageNum\":\"1\",\"pageSize\":\"9\",\"name\":\"\"},1,9]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13055,
  "username": "develop",
  "operation": "查询加入党支部方式",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.JoinPartyBranchTypeController.queryJoinPartyBranchTypes()",
  "date": "2018-06-22T09:56:55.000+0800",
  "msg": "记录查询加入党支部方式 执行",
  "costtime": 7,
  "type": 0,
  "params": "[{}]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13054,
  "username": "develop",
  "operation": "查询工作性质",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.WorkNatureTypeController.queryWorkNatureTypes()",
  "date": "2018-06-22T09:56:55.000+0800",
  "msg": "记录查询工作性质 执行",
  "costtime": 6,
  "type": 0,
  "params": "[{}]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13053,
  "username": "develop",
  "operation": "查询学位水平",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.AcademicDegreeLevelController.queryAcademicDegreeLevels()",
  "date": "2018-06-22T09:56:55.000+0800",
  "msg": "记录查询学位水平 执行",
  "costtime": 7,
  "type": 0,
  "params": "[{}]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13052,
  "username": "develop",
  "operation": "查询教育水平",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.EducationLevelController.queryEducationLevels()",
  "date": "2018-06-22T09:56:55.000+0800",
  "msg": "记录查询教育水平 执行",
  "costtime": 7,
  "type": 0,
  "params": "[{}]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13051,
  "username": "develop",
  "operation": "查询民族",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.NationTypeController.queryNationType()",
  "date": "2018-06-22T09:56:55.000+0800",
  "msg": "记录查询民族 执行",
  "costtime": 7,
  "type": 0,
  "params": "[{}]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13050,
  "username": "develop",
  "operation": "查询组织信息",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.OrganizationInformationController.queryThisOrgChildren()",
  "date": "2018-06-22T09:52:24.000+0800",
  "msg": "记录查询组织信息 执行",
  "costtime": 32,
  "type": 0,
  "params": "[{\"pageNum\":\"1\",\"pageSize\":\"10\",\"orgInfoId\":\"20\"},1,10]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13049,
  "username": "develop",
  "operation": "得到党员证件照",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.PartyUserInfoController.getPartyUserInfoIdPhoto()",
  "date": "2018-06-22T09:48:28.000+0800",
  "msg": "记录得到党员证件照 执行",
  "costtime": 8,
  "type": 0,
  "params": "[14]",
  "startTime": null,
  "endTime": null
}, {
  "logId": 13048,
  "username": "develop",
  "operation": "得到党员证件照",
  "ip": "0:0:0:0:0:0:0:1",
  "level": "INFO",
  "method": "com.zltel.broadcast.um.controller.PartyUserInfoController.getPartyUserInfoIdPhoto()",
  "date": "2018-06-22T09:48:28.000+0800",
  "msg": "记录得到党员证件照 执行",
  "costtime": 12,
  "type": 0,
  "params": "[13]",
  "startTime": null,
  "endTime": null
}];
//终端节目信息
let program = [{
  "PkId": 152,
  "CoverImage": "<img src=\"/assets/img/program-test.jpg\" width=\"120px\" height=\"67px\" onerror=\"this.src='http://10.0.0.122/Apps/DSIS/Images/noshot.png'\">",
  "Name": "测试节目",
  "PublishTypeStr": "<span style=\"color:green\">简单推送</span>轮播",
  "ActiveTime": "2018-05-08--永久有效",
  "PlayTime": "时间：00:00-23:59</br>每周：0,1,2,3,4,5,6",
  "UserName": "ibo_55XIte",
  "CreateTime": "2018-05-08 17:39:16"
}, {
  "PkId": 188,
  "CoverImage": "<img src=\"/assets/img/program-test.jpg\" width=\"120px\" height=\"67px\" onerror=\"this.src='http://10.0.0.122/Apps/DSIS/Images/noshot.png'\">",
  "Name": "test",
  "PublishTypeStr": "<span style=\"color:green\">简单推送</span>轮播",
  "ActiveTime": "2018-05-08--永久有效",
  "PlayTime": "时间：00:00-23:59</br>每周：0,1,2,3,4,5,6",
  "UserName": "ibo_55XIte",
  "CreateTime": "2018-05-08 17:39:16"
}]

let optLogs = {
  "PageCount": 1, //总页数
  "RecordCount": 3, //总条数
  "DISOperationLogModelList": [{
    "PkId": 10,
    "Remark": "简单推送节目：普通测试到终端：PC201606111543",
    "CreateTime": "2018-03-28 11:11:28",
    "UserName": "admin",
    "IP": "10.0.0.145"
  }, {
    "PkId": 9,
    "Remark": "推送管理融合节目：到终端：PC201606111543",
    "CreateTime": "2018-03-28 11:10:28",
    "UserName": "admin",
    "IP": "10.0.0.145"
  }, {
    "PkId": 8,
    "Remark": "推送管理融合节目：到终端：PC201606111543",
    "CreateTime": "2018-03-28 11:10:28",
    "UserName": "admin",
    "IP": "10.0.0.145"
  }]
}
let executeLogs = {
  "PageCount": 1, //总页数
  "RecordCount": 1, //总条数
  "DISOperationLogModelList": [{
    "PkId": 10,
    "Name": "",
    "Sendtime": "2018-03-28 11:11:28",
    "Acesstime": "2018-03-28 11:11:2",
    "Remark": "",
    "Status": "开始执行"
  }]
}


Mock.mock("/terminal/list", "get", {
  code: 200,
  status: true,
  msg: "success",
  //https://fullcalendar.io/docs/event-object
  "data": datas
});

Mock.mock("/terminal/logs", "post", {
  code: 200,
  status: true,
  msg: "success",
  "data": logs
});

//查询终端节目
Mock.mock("/terminal/control/1/program", "get", {
  code: 200,
  status: true,
  msg: "success",
  "data": program
});
Mock.mock("/terminal/control/2/program", "get", {
  code: 200,
  status: true,
  msg: "success",
  "data": program
});