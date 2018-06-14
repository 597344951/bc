var appInstince = new Vue({
  el: "#app",
  data: {
    rules: {
      name: [{
          required: true,
          message: "请输入名称",
          trigger: "blur"
        },
        {
          min: 1,
          max: 50,
          message: "长度在 1 到 50个字符",
          trigger: "blur"
        }
      ],
      remark: [{
          required: true,
          message: "请输入表述",
          trigger: "blur"
        },
        {
          min: 1,
          max: 50,
          message: "长度在 1 到 50个字符",
          trigger: "blur"
        }
      ],
      orderNum: [{
        required: true,
        message: "请输入排序序号",
        trigger: "blur"
      }],
      title: [{
          required: true,
          message: "请输入标题",
          trigger: "blur"
        },
        {
          min: 1,
          max: 50,
          message: "长度在 1 到 50个字符",
          trigger: "blur"
        }
      ],
      tpTypeIds: [{
        required: true,
        message: "请选择分类",
        trigger: "change"
      }]
    },
    tpt_props: {
      label: "name",
      value: "id"
    },
    tp_empty:{
      previewPicture:''
    },
    tp: {
      title: "文章模板编辑",
      visible: false,
      update: false,
      data: {
        title: "模板标题",
        content: "模板正文",
        tpTypeIds: [1, 3],
        previewPicture:''
      }
    },
    tpt: {
      title: "测试标题",
      visible: false,
      update: false, // 是否是更新
      data: {
        tpTypeId: "",
        name: "",
        remark: "",
        orderNum: 0,
        parent: 0
      }
    },
    currentDate: new Date(),
    props: {
      label: "label",
      children: "children"
    },
    tpt_data: [],
    tps: []
  },
  mounted() {
    this.loadTreeData();
  },
  methods: {
    // 加载类别树的数据
    loadTreeData: function () {
      var ins = this;
      ajax("tpt/listTypeTree", "get", "", function (result) {
        ins.tpt_data = result.data;
      });
    },
    // 加载分类数据
    loadTpTypeData: function (data, ins) {
      ajax_json("tp/listByType", "get", data, function (result) {
        ins.tps = result.data;
      });
    }, // 重新加载分类数据
    reloadTpTypeData: function () {
      var node = this.$refs.tree.getCurrentNode();
      this.loadTpTypeData(node.data, this);
    },
    checkTreeSelect: function () {
      var node = this.$refs.tree.getCurrentNode();
      if (!node) {
        $message("请先选择要操作的位置", "warning", this)
        return null;
      }
      return node;
    },
    // 增加模板类别
    addTemplateType: function () {
      var node = this.checkTreeSelect();
      if (!node) return;

      var tpt = this.tpt;
      tpt.update = false;
      tpt.title = "新增模板类别";
      tpt.visible = true;
      tpt.data = {
        parent: node.data.tpTypeId,
        parentLabel: node.data.name,
        orderNum: 0
      };
    },
    // 修改模板类别
    updateTemplateType: function () {
      var node = this.checkTreeSelect();
      if (!node) return;

      var tpt = this.tpt;
      tpt.title = "修改模板类别";
      tpt.update = true;
      tpt.visible = true;
      tpt.data = node.data;
    },
    // 保存或者更新数据
    saveOrUpdateTemplateType: function () {
      var ins = this;
      var tpt = this.tpt;
      this.$refs.tptForm.validate(function (valid) {
        if (!valid) {
          return false;
        }
        if (tpt.update) {
          // 更新数据
          ajax_json("tpt/tptype", "put", tpt.data, function (result) {
            if (result.status) {
              tpt.visible = false;
              ins.loadTreeData();
            }
          });
        } else {
          // 新增数据
          ajax_json("tpt/tptype", "post", tpt.data, function (result) {
            if (result.status) {
              tpt.visible = false;
              ins.loadTreeData();
            }
          });
        }
      });
    },
    deleteTemplateType: function () {
      var ins = this;
      var tpt = this.tpt;
      var node = this.checkTreeSelect();
      if (!node) return;
      var data = node.data;
      if (node.children && node.children.length > 0) {
        $message("本节点包含子节点,如需删除请先删除子节点。", "warning", this);
        return;
      }
      this.$confirm("此操作将永久该分类数据, 是否继续?", "提示", {
        type: "warning"
      }).then(function () {
        // 删除数据
        ajax_json("tpt/tptype/" + data.tpTypeId, "delete", null, function (
          result
        ) {
          if (result.status) {
            tpt.visible = false;
            ins.loadTreeData();
          }
        });
      });
    },
    // 类别点击
    tptTreeClick: function (_data, node) {
      var ins = this;
      var data = _data.data; // 类别节点数据
      this.loadTpTypeData(data, ins);
    },
    //预览模板
    viewTemplate: function (tp) {
      _editor.setContent(tp.content);
      _editor.execCommand("preview");
    },
    // 新增模板
    addTemplate: function () {
      this.tp.visible = true;
      this.tp.update = false;
      this.tp.title = "新增模板";
      this.tp.data = {previewPicture:''};
      _editor.setContent("");
    },
    // 修改模板
    updateTemplate: function (tp) {
      this.tp.visible = true;
      this.tp.update = true;
      this.tp.title = "修改模板";
      this.tp.data = tp;
      _editor.setContent(tp.content);
      var tpTypeIds = getTpTypeIds(this.tp.data, this.tpt_data);
      this.tp.data.tpTypeIds = tpTypeIds;
    },
    // 删除模板
    delTemplate: function (tp) {
      var ins = this;
      var tpId = tp.tpId;
      if (!tpId) {
        $message("请选择要删除的模板!", "warning", ins);
        return;
      }
      var url = "tp/template/" + tpId;
      ajax_json(url, "delete", null, function (result) {
        if (result.status) {
          ins.loadTreeData();
          ins.reloadTpTypeData();
        }
      });
    },
    saveOrUpdateTemplate: function () {
      var ins = this;
      var tp = this.tp;
      this.$refs.tpForm.validate(function (valid) {
        if (!valid) return false;
        // 更新tpTypeIds
        var ids = tp.data.tpTypeIds;
        tp.data.tpTypeId = ids[ids.length - 1];
        tp.data.content = _editor.getContent();
        if (!_editor.hasContents()) {
          $message("请输入模板内容!", "warning", ins);
          return;
        }
        console.log(tp.data);
        if (tp.update) {
          // 更新数据
          ajax_json("tp/template", "put", tp.data, function (result) {
            if (result.status) {
              tp.visible = false;
              ins.loadTreeData();
              ins.reloadTpTypeData();
            }
          });
        } else {
          // 新增数据
          ajax_json("tp/template", "post", tp.data, function (result) {
            if (result.status) {
              tp.visible = false;
              ins.loadTreeData();
              ins.reloadTpTypeData();
            }
          });
        }
      });
    },
    handleAvatarSuccess(res, file) {
      this.tp.data.previewPicture = res.url;//URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type.startsWith('image');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }
  }
});


// 编辑器
var _editor = UE.getEditor("templateText", {
  
});

function getTpTypeIds(data, tpt_data) {
  var ay = [];
  pathScan(ay, data, tpt_data);
  return ay.reverse().map(function (a) {
    return a.pid;
  });
}

// 深度遍历路径
function pathScan(ay, data, tpt_data) {
  var pid = data.tpTypeId;

  for (var i = 0; i < tpt_data.length; i++) {
    var v = tpt_data[i];
    if (v.children) {
      pathScan(ay, data, v.children);
      if (ay.length > 0) {
        var cn = ay[ay.length - 1];
        if (v.data.tpTypeId == cn.parent) {
          ay.push({
            pid: v.data.tpTypeId,
            parent: v.data.parent
          });
        }
      }
    } else {
      if (ay.length == 0) {
        // 找到了位置
        if (v.data.tpTypeId == pid) {
          ay.push({
            pid: pid,
            parent: v.data.parent
          });
        }
      } else {
        var cn = ay[ay.length - 1];
        if (v.data.tpTypeId == cn.parent) {
          ay.push({
            pid: v.data.tpTypeId,
            parent: v.data.parent
          });
        }
      }
    }
  }
}