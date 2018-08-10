//Mock 拦截url地址配置
//
var datas = [{
  title: '中央军委向武警部队授旗仪式',
  descript: '一月十日，中央军委向武警部队授旗仪式在北京八一大楼举行。中共中央总书记、国家主席、中央军委主席习近平向武警部队授旗并致训词。这是习近平致训词。',
  cover_img: '/components/dang//images/1.jpg',
  date:'2018-08-01'
},{
  title: '中央军委向武警部队授旗仪式',
  descript: '一月十日，中央军委向武警部队授旗仪式在北京八一大楼举行。中共中央总书记、国家主席、中央军委主席习近平向武警部队授旗并致训词。这是习近平致训词。',
  cover_img: '/components/dang//images/1.jpg',
  date:'2018-08-01'
}, {
  title: '授旗仪式在北京举行习近平向武警部队授旗',
  descript: '一月十日，中央军委向武警部队授旗仪式在北京八一大楼举行。中共中央总书记、国家主席、中央军委主席习近平向武警部队授旗并致训词。这是习近平致训词。',
  cover_img: '/components/dang//images/1.jpg',
  date:'2018-08-02'
},{
  title: '习近平向武警部队授旗并致训词',
  descript: '一月十日，中央军委向武警部队授旗仪式在北京八一大楼举行。中共中央总书记、国家主席、中央军委主席习近平向武警部队授旗并致训词。这是习近平致训词。',
  cover_img: '/components/dang//images/1.jpg',
  date:'2018-08-03'
}]

Mock.mock("/history/query", "post", {
  code: 200,
  status: true,
  msg: "success",
  "data": datas
});