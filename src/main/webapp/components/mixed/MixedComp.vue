<script type="text/babel">
export default {
  info: {
    name: "mixed-comp", //注册组件名
    author: "Wangch",
    descript: "混合组件编写测试"
  },
  props: {},
  data() {
    return {
      visible: true
    };
  },
  methods: {},
  computed: {}
};
</script>
<template>
   <h1>Mixed Vue组件</h1>
</template>
<style scoped>
h1 {
  color: red;
}
</style>