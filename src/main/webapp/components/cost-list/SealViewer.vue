<script type="text/babel">
import ms from "../my_seal/index.js";
//CompanySeal,PersonSeal
import html2canvas from "html2canvas";
import NumConvertUtil from "../utils/NumConvertUtil.js";

//https://www.npmjs.com/package/my_seal

//数据格式
let dataExample = {
  companyName: "广州卓凌通信第一党支部",
  typeName: "收款专用章",
  radius: 150, //number(半径，默认值75),
  color: "", //String(颜色，关键字或十六进制颜色字符，默认 红色),
  fontFamily: "宋体", //String(字体，默认宋体),
  hasInnerLine: true, //Boolean(内边框，可选),
  securityCode: "" //String(防伪码，可选)
}; //印章相关信息

export default {
  info: {
    name: "seal-viewer",
    author: "Wangch",
    descript: "印章预览."
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
    return {};
  },
  computed: {},
  mounted() {
    this.redraw();
  },
  watch: {
    data: {
      handler: function(val, oldVal) {
        this.redraw();
      },
      //深度监控
      deep: true,
      // 立即调用此回调
      immediate: false
    }
  },
  methods: {
    redraw() {
      var canvas = this.$refs.seal;
      var option = this.data;
      var seal = new ms.CompanySeal(canvas, option);
    }
  }
};
</script>
<template>
  <canvas ref="seal"></canvas>
</template>
<style scoped>
</style>