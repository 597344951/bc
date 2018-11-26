<script type="text/babel">
import ms from "my_seal";
//CompanySeal,PersonSeal
import html2canvas from "html2canvas";
import NumConvertUtil from '../utils/NumConvertUtil.js'

//数据格式
let dataExample = {
  title: "VPN购买收据",
  seriaNum: "000001",
  time: new Date(), //时间
  payer: "王春华", //付款人
  payee: "邓晓祥", //收款人
  detail: "2018年VPN购买费用", //收款详情
  amount: 968.33, //收款金额
  sealInfo: {
    name: "广州卓凌通信第一党支部",
    type: "收款专用章"
  } //印章相关信息
};


export default {
  info: {
    name: "payment-list",
    author: "Wangch",
    descript: "缴费清单展示组件,附带收费章."
  },
  props: {
    data: {
      type: Object,
      default() {
        return dataExample;
      }
    }
  },
  data() {
    return {
      visible: true
    };
  },
  computed: {},
  mounted() {
    var id = "#company";
    var option = {
      typeName: this.data.sealInfo.type,
      companyName: this.data.sealInfo.name,
      hasInnerLine: true
    };
    var seal = new ms.CompanySeal(id, option);
  },
  computed: {
    amountCapital() {
      if (!this.data.amount) return "";
      return NumConvertUtil.digitUppercase(this.data.amount)
    }
  },
  methods: {
    
    formatDate(time) {
      let ay = [];
      ay.push(time.getFullYear());
      ay.push("年");
      ay.push(time.getMonth() + 1);
      ay.push("月");
      ay.push(time.getDate());
      ay.push("日");
      return ay.join("");
    },
    download() {
      let name = this.data.payer+' '+this.data.title;
      console.debug("下载图片");
      let opts = {
        useCORS: true // 跨域图片
      };
      let target = this.$refs.paymentList.querySelector(".box");
      html2canvas(target, opts).then(canvas => {
        console.debug("canvas", canvas);
        let url = canvas.toDataURL("image/png");
        let a = document.createElement("a");
        let event = new MouseEvent("click");

        a.download = name || "缴费凭据";
        a.href = url;

        // 触发a的单击事件
        a.dispatchEvent(event);
      });
    }
  }
};
</script>
<template>
<div ref="paymentList">
<div class="box" >
		<span class="number">{{data.seriaNum}}</span>
		<h1>{{data.title}}</h1>
		<span class="line"></span>
		<h2>{{formatDate(data.time)}}</h2>
		<div class="contain">
			<p>今收到<span class="name">{{data.payer}}</span></p>
			<p>交来<span class="time">{{data.detail}}</span></p>
			<p>人民币（大写）<span class="daxie">{{amountCapital}}</span> ￥ <span class="xiaoxie">{{data.amount}}</span></p>
			<div class="footer">
        <span class="danwei">收款单位（章）</span>
        <canvas id="company"></canvas>
        <span class="ren">收款人 <span class="get">{{data.payee}}</span></span></div>
		</div>
	</div>
  <div id="control-button">
      <el-button type="primary" icon="el-icon-download" size="small" @click="download" ></el-button>
  </div>
</div>
</template>
<style scoped>
h1 {
  color: red;
}
.box {
  width: 600px;
  height: auto;
  margin: 0 auto;
  padding: 30px 50px;
  background-color: #ffdffb;
  position: relative;
}
.contain {
  border: 2px solid #222;
  padding: 20px;
}
h1,
h2,
.line {
  text-align: center;
  margin: 15px 0;
  font-family: "宋体";
}
h1 {
  font-size: 28px;
}
h2 {
  font-size: 20px;
}
.line {
  width: 300px;
  height: 1px;
  display: block;
  background: #222;
  margin: 0 auto;
  box-shadow: 0 3px 0 0px #222;
}
.contain p {
  width: 100%;
  border-bottom: 1px solid #111;
}
.name,
.time,
.daxie,
.xiaoxie,
.get {
  margin-left: 20px;
  font-size: 20px;
}
.daxie {
  margin-right: 20px;
}
.footer {
  margin: 60px 0 40px;
  font-size: 20px;
}
.ren {
  float: right;
  margin-right: 30px;
}
#company {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  float: left;
  margin-left: 97px;
  margin-top: -50px;
  margin-right: -210px;
}
#control-button {
  padding-top: 20px;
}
.number {
  font-size: 22px;
  letter-spacing: 4px;
  color: red;
  font-family: "宋体";
  float: right;
  margin-left: -50%;
  margin-top: 20px;
}
</style>