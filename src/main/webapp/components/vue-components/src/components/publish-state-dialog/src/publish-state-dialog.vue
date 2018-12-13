<template>
  <el-dialog title="内容发布进度" :visible.sync="visible" width="60%" :show-close="false">
    <el-steps :space="200" :active="state.active" finish-status="success">
      <el-step v-for="step in state.steps" :title="step.title" :key="step.index"></el-step>
    </el-steps>
    <el-card shadow="never">
      <p style="font-size: 12px;" v-for="(msg, index) in state.detail" :key="index">{{msg}}</p>
    </el-card>
    <span slot="footer" class="dialog-footer">
      <el-button size="mini" type="primary" @click="dialogVisible = false">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { get } from "../../../lib/ajax";
export default {
  name: "publish-state-dialog",
  label: "发布状态浏览框",
  props: ["visible", "content"],
  data() {
    return {
      dialogVisible: true,
      state: {
        active: 0,
        steps: [],
        detail: []
      }
    };
  },
  watch: {
    visible(newVal, oldVal) {
      this.dialogVisible = newVal;
      if (newVal) {
        let url = '/publish/process/state/0/' + this.content
        let app = this, msg, processItem
        get(url, reps => {
          if(reps.status) {
            let curProcess = reps.data.curProcess
            app.state.detail = []
            for(var i in reps.data.log) {
              msg = reps.data.log[i]
              app.state.detail.push(msg.add_date + ': ' + msg.label + '  ' + msg.msg + ' [ '+ (msg.remark ? msg.remark : '') +' ] ')
            }
            app.state.steps = []
            for(var i in reps.data.steps) {
                processItem = reps.data.steps[i];
                app.state.steps.push({
                    index: i,
                    title: reps.data.steps[i].label
                });
                if(curProcess == processItem.process_item_id) {
                    app.state.active = Number.parseInt(i);
                }
            }
          }
        }, err => {
          app.$message.error("系统错误请联系管理员...")
        })
      }
    },
    dialogVisible(newVal, oldVal) {
      this.$emit("update:visible", newVal);
    }
  }
};
</script>
<style>
</style>

