<template>
    <el-dialog title="素材验证" :visible.sync="visible" :show-close="false" width="300px">
        <p style="font-size: 13px; margin: 5px 20px; color: #2b2b2b" v-for="(msg, index) in msgs" :key="index">{{msg}}</p>
        <div style="text-align:center;">
            <span class="el-icon-loading"></span>
        </div>
    </el-dialog>
</template>
<script>
export default {
  name: "md5-check-dialog",
  label: "文件MD5验证展示对话框",
  props: ["visible", "on-finished"],
  data() {
    return {
      msgs: []
    };
  },
  watch: {
    visible(newVal, oldVal) {
      if (newVal) {
        this.msgs = [];
        this.show();
      }
    }
  },
  methods: {
    show() {
      let messages = [
          "敏感词检测...",
          "完成, 未发现敏感词",
          "提取素材文件MD5...",
          "素材文件MD5提取完成",
          "MD5校验...",
          "校验完成",
          "正在提交...",
          "提交完成."
        ],
        index = 1,
        app = this;

      let iterval = setInterval(() => {
        app.msgs.push(messages[index]);
        index++;
        if (index == messages.length) {
          app.$emit("update:visible", false);
          clearInterval(iterval);
          app.$emit("finished");
        }
      }, 1000);
    }
  }
};
</script>
<style scoped>
.el-dialog__body {
  padding: 10px 20px 20px;
}
.el-dialog__body p {
  text-align: center;
  padding: 3px 20px;
}
.el-dialog {
  border-radius: 5px;
}
.el-dialog__header {
  padding: 20px 20px 10px;
  border-bottom: 1px solid #ddd;
  margin-bottom: 5px;
}
</style>


