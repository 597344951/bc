//Mock 拦截url地址配置
//
Mock.mock("manage/users", "get", {
  status: "OK",
  msg: "success",
  "data|8-15": [
    {
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
  ]
});

var data = Mock.mock({
  "data|8-15": [
    {
      avatar: "@image(48x48)", //图片
      color: "@color", //随机16进制颜色

      age: "@integer(1,100)", //数字范围
      sex: "@cword(男女,1)", //字符随机候选
      tel: /^1[385][1-9]\d{8}/, //电话号
      birtyday: '@date("yyyy-MM-dd")', //日期
      //web
      url: "@url", //url
      ip: "@ip", //IP
      email: "@email", //email
      //name
      cfirst: "@cfirst", //姓
      clast: "@clast", //名
      name: "@cname", //中文名字
      //地理位置信息
      region: "@region", //行政分区
      province: "@province", //省份
      city: "@city", //市
      country: "@county()", //县
      countryFullName: "@county(true)", //县全名
      zip: "@zip", //邮政编码
      //排版
      ctitle: "@ctitle(10,15)", //10-15字的中文标题
      csentence: "@csentence(5, 7)", //5-7字的句子
      cparagraph: "@cparagraph(3,5)", //3-5个 中文段落

      guid: "@guid" //guid
    }
  ]
});
//console.log(JSON.stringify(data));
var a = {
	"data": {
		"avatar": "http://dummyimage.com/48x48",
		"color": "#e1f279",
		"age": 100,
		"sex": "女",
		"tel": "13154457552",
		"birtyday": "1971-07-20",
		"url": "telnet://jlpokptqt.pe/eks",
		"ip": "236.56.202.22",
		"email": "n.eksbi@xfvtxxx.bs",
		"cfirst": "钱",
		"clast": "秀英",
		"name": "熊敏",
		"region": "东北",
		"province": "四川省",
		"city": "佳木斯市",
		"country": "将乐县",
		"countryFullName": "海外 海外 -",
		"zip": "464161",
		"ctitle": "定或习长由置把准思话王设政",
		"csentence": "级效先被南。",
		"cparagraph": "权有矿分内根易但内七思细小。取也革于动压值第需此火质程花转加。人权样第片管准价装始速持子十斯内难。",
		"guid": "DA596B3F-Ee68-Cc46-d299-96498Ca1aadc"
	}
}