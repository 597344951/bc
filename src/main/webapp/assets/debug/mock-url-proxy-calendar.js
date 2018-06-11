//Mock 拦截url地址配置
//
var data = {
  id:11,//唯一编号
  title:'任务标题',//
  start:'2018-05-01',
  end:'2018-05-05',
  type: 'CyclePlan',//计划类型,CyclePlan 周期计划，其他临时计划
  from:'festival',//计划来源,festival 系统内置活动计划,person 个人自定义
  status:0,// 计划状态,0: 就绪,1:正在投票，2:正在进行，-1:未通过
  priority:1,//计划优先级，4个等级,1,2,3,4
  //className:'',//状态展示类名, event-ready:就绪,event-progress:正在进行,event-disable:未通过
  //themeClass:'',//主题颜色
  //priorityClass:'',//priority 颜色
  voteInfo:{//投票信息
    status:'投票统计',
    agree:90,
    total:100
  }
}
Mock.mock("calendar/list", "get", {
  status: true,
  msg: "success",
  //https://fullcalendar.io/docs/event-object
  "data": [{
      id: 11,
      title: '元旦节',
      start: '2018-01-01',
      //计划类型
      type: 'CyclePlan',
      //计划来源
      from: 'festival',
      status: 0
    },
    {
      id: 35,
      title: '中国青年志愿者服务日',
      start: '2018-03-05',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    }, {
      id: 38,
      title: '国际劳动妇女节',
      start: '2018-03-08',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    }, {
      id: 312,
      title: '中国植树节',
      start: '2018-03-12',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    }, {
      id: 315,
      title: '消费者权益保护日',
      start: '2018-03-15',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    }, {
      id: 41,
      title: '清明节',
      start: '2018-04-05',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    }, {
      id: 423,
      title: '中国海军建军节',
      start: '2018-04-23',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    }, {
      id: 424,
      title: '中国航天日',
      start: '2018-04-24',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 51,
      title: '五一劳动节',
      start: '2018-05-01',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    }, {
      id: 54,
      title: '五四青年节',
      start: '2018-05-04',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    }, {
      id: 530,
      title: '五卅惨案纪念日',
      start: '2018-05-30',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    }, {
      id: 61,
      title: '六一国际儿童节',
      start: '2018-06-01',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 65,
      title: '世界环境日',
      start: '2018-06-05',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 66,
      title: '全国爱眼日',
      start: '2018-06-06',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 7111,
      title: '七一建党节',
      start: '2018-07-01',
      type: 'CyclePlan',
      from: 'festival',
      status: 2,priority:1,
      className: 'event-progress event-icon emergency-level',
      voteInfo:{
        status:'投票统计',
        agree:90,
        total:100
      }
    },
    {
      id: 712,
      title: '香港回归纪念日',
      start: '2018-07-01',
      type: 'CyclePlan',
      from: 'festival',priority:2,
      status: -1,
      className:'event-disable'
    },
    {
      id: 77,
      title: '中国人民抗日战争纪念日',
      start: '2018-07-07',
      type: 'CyclePlan',
      from: 'festival',
      status: 0,priority:3,
      className: 'event-icon high-level',
    },
    {
      id: 81,
      title: '八一建军节',
      start: '2018-08-01',
      type: 'CyclePlan',
      from: 'festival',priority:4,
      status: 0,className: 'event-icon normal-level',
    },
    {
      id: 93,
      title: '抗日战争胜利纪念日',
      start: '2018-09-03',
      type: 'CyclePlan',
      from: 'festival',
      status: 0,
      className: 'event-icon low-level',

    },
    {
      id: 910,
      title: '中国教师节',
      start: '2018-09-10',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 918,
      title: '九一八事变纪念日',
      start: '2018-09-18',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 920,
      title: '全国爱牙日',
      start: '2018-09-20',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 10,
      title: '十一国庆节',
      start: '2018-10-01',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 1018,
      title: '重阳节',
      start: '2018-10-18',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 119,
      title: '消防节',
      start: '2018-11-09',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 121,
      title: '世界艾滋病日',
      start: '2018-12-01',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 123,
      title: '世界残疾人日',
      start: '2018-12-03',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 1213,
      title: '南京大屠杀纪念日',
      start: '2018-12-13',
      type: 'CyclePlan',
      from: 'festival',
      status: 0
    },
    {
      id: 525,
      title: '自定义计划',
      start: '2018-05-25',
      type: 'CyclePlan',
      from: 'person',
      status: 0
    }
  ]
});
var tps = [{
  name: '模板1',
  title: '策划标题1',
  content: '策划内容1'
}, {
  name: '模板2',
  title: '策划标题2',
  content: '策划内容2'
}, {
  name: '模板3',
  title: '策划标题3',
  content: '策划内容3'
}, {
  name: '模板4',
  title: '策划标题4',
  content: '策划内容4'
}];

//加载事件策划模版
Mock.mock("/plan/costplan", "post", {
  status: true,
  msg: "success",
  code:200,
  //"data":tps
  "data|5-6": [
    {
      name: '@ctitle(5)',
      "value|100-10000.0-0": 1,
      costType: '@string("345678",1)'
    }]
});

Mock.mock("/plan/templates", "post", {
  status: true,
  msg: "success",
  code:200,
  //"data":tps
  "data|5-6": [
    {
      name: '@ctitle(5)',
      title: '@ctitle',
      content: '@cparagraph'
    }]
});
var data = {
  avatar: "@image(48x48)",
  name: "@cname",
  idnum: /^\d{11}/,
  sex: '@string("男女",1)',
  marry: '@string("已未",1)',
  children: '@string("有无",1)',
  tel: /^1[385][1-9]\d{8}/,
  nation: "汉",
  education: "本科",
  org: "@county()",
  jointime: '@date("yyyy-MM")',
  PartyWorker: '@string("是否",1)',
  PartyRepresentative: '@string("是否",1)',
  volunteer: '@string("是否",1)',
  DifficultPartyMember: '@string("是否",1)'
}



function toCSV(datas){
  var ay = [];
  datas.map((it)=>{
	  var line = [];
	  line.push('6');
	  line.push(it.title);
	  line.push(it.start);
	  line.push(it.start);
	  line.push(3);
	  line.push(it.title);
	  line.push(it.from);
	  line.push(3);
	  ay.push(line.join(','));
  })
  
  console.log(ay.join('\r\n'));
}