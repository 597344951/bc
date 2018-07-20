import serverConfig from '/environment/resourceUploadConfig.jsp';

window.onclose = function () {
  let tp = appInstince.tp.data;
  if (tp.title || _editor.hasContents()) {
    return confirm('有未保存数据，是否关闭？');
  }
  return true;
}

var appInstince = new Vue({
  el: "#app",
  data: {
    //右键 节点的数据
    curContextData: null,
    contextMenu: {
      visiable: false,
      event: null
    },
    //菜单数据
    contextMenuData: [{
      label: '增加分类',
      icon: 'el-icon-plus',
      type: 'success'
    }, {
      label: '修改分类',
      icon: 'el-icon-edit',
      type: 'primary'
    }, {
      label: '删除分类',
      icon: 'el-icon-delete',
      type: 'danger'
    }],
    resource_menu: [{
      label: '素材管理',
      children: [{
        label: '视频(8)'
      }, {
        label: '音频(4)'
      }, {
        label: '图片(34)'
      }]
    }],
    keyword: '',
    //当前目录
    currentCategory: {
      name: '所有类别'
    },
    tpager: {
      total: 0,
      current: 1,
      size: 10
    },
    //节目模版选择
    ptw: {
      visiable: false
    },
    rules: {
      programTemplate: [{
        required: true,
        message: "请选择节目模版",
        trigger: "blur"
      }],
      programTemplateName: [{
        required: true,
        message: "请选择节目模版",
        trigger: "blur"
      }],
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
    tp_empty: {
      previewPicture: ''
    },
    tp: {
      title: "文章模板编辑",
      visible: false,
      update: false,
      data: {
        title: "",
        content: "",
        tpTypeIds: [1, 3],
        previewPicture: '',
        programTemplate: ''
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
    tps: [],
    resource_server_url: serverConfig.getUploadUrl('image')
  },
  mounted() {
    this.loadTreeData();
    this.loadTpTypeData(null, this);
  },
  computed: {
    breadcrumbData() {
      let bp = breadPath(this.currentCategory, this.tpt_data, item => item.children, item => item.parent, item => item.tpTypeId, item => item.data);
      return bp;
    },
    tpt_parents() {
      let ay = [{
        parent: 0,
        parentLabel: '根目录'
      }];
      if (this.tpt.data.parent != 0) {
        var nodedata = this.checkTreeSelectData();
        if (nodedata) {
          ay.push({
            parent: nodedata.tpTypeId,
            parentLabel: nodedata.name
          })
        }
      }
      return ay;
    }
  },
  methods: {
    rechoseTree(data) {
      console.debug('重新设置 选中');
      let bp = breadPath(data, this.tpt_data, item => item.children, item => item.parent, item => item.tpTypeId, item => item.data);
      setTimeout(() => {
        if (bp.length > 1) {
          this.$refs.tree.setCurrentKey(bp[bp.length - 2].tpTypeId)
        } else {
          this.$refs.tree.setCurrentKey(this.tpt_data[0].id)
        }
      }, 500)
    },
    breadPathClick(item) {
      let cc = this.currentCategory;
      if (cc.tpTypeId == item.tpTypeId) return;
      this.currentCategory = item;
      this.loadTpTypeData(item);
    },
    treeContextmenu(event, data, node, ins) {
      console.log('right click', arguments);
      this.contextMenu.visiable = true;
      this.contextMenu.event = event;
      this.curContextData = data.data;
    },
    contextMenuClose() {
      this.curContextData = null;
    },
    contextMenuClick(menuItem) {
      console.log('菜单点击事件', menuItem);
      if (menuItem.label == '增加分类') {
        this.addTemplateType();
      }
      if (menuItem.label == '修改分类') {
        this.updateTemplateType();
      }
      if (menuItem.label == '删除分类') {
        this.deleteTemplateType();
      }
      this.curContextData = null;
    },

    card_hover(it) {
      it.showtoolbar = true;
    },
    card_leave(it) {
      it.showtoolbar = false;
    },

    //查询模板
    searchTemplate() {
      this.currentCategory.name = '所有类别';
      this.currentCategory.tpTypeId = 0;
      this.loadTpTypeData();
      this.loadTreeData();
    },
    // 加载类别树的数据
    loadTreeData: function () {
      var ins = this;
      let data = {
        keyword: this.keyword
      };
      ajax("tpt/listTypeTree", "get", data, function (result) {
        ins.tpt_data = result.data;
      });
    },
    // 加载分类数据
    loadTpTypeData: function (data) {
      let ins = this;
      if (data) {
        this.currentCategory = data;
      } else {
        data = {};
      }
      let url = 'tp/listByType/' + this.tpager.current + '-' + this.tpager.size;
      data.keyword = this.keyword;
      ajax_json(url, "get", data, function (result) {
        ins.tps = ins.initData(result.data);
        ins.tpager.total = result.pager.total;
      });
    }, // 重新加载分类数据
    reloadTpTypeData: function () {
      var node = this.$refs.tree.getCurrentNode();
      this.loadTpTypeData(node ? node.data : null, this);
    },
    checkTreeSelectData: function () {
      var node = this.$refs.tree.getCurrentNode();
      if (!node && !this.curContextData) {
        $message("请先选择要操作的位置", "warning", this)
        return null;
      }
      return this.curContextData ? this.curContextData : node.data;
    },
    initData(data) {
      data.forEach(element => {
        element.showtoolbar = false;
        element.cfv = false;
      });
      return data;
    },
    // 增加模板类别
    addTemplateType: function () {
      var nodedata = this.checkTreeSelectData();
      if (!nodedata) {
        nodedata = {
          tpTypeId: 0,
          name: '根目录'
        };
      }

      var tpt = this.tpt;
      tpt.update = false;
      tpt.title = "新增模板类别";
      tpt.visible = true;
      tpt.data = {
        parent: nodedata.tpTypeId,
        parentLabel: nodedata.name,
        orderNum: 0
      };
    },
    // 修改模板类别
    updateTemplateType: function () {
      var nodedata = this.checkTreeSelectData();
      if (!nodedata) return;

      var tpt = this.tpt;
      tpt.title = "修改模板类别";
      tpt.update = true;
      tpt.visible = true;
      tpt.data = nodedata;
    },
    // 保存或者更新数据
    saveOrUpdateTemplateType: function () {
      var ins = this;
      var tpt = this.tpt;
      if (tpt.data.parentLabel == '根目录') {
        tpt.data.parent = 0;
      }
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
      var nodedata = this.checkTreeSelectData();
      if (!nodedata) return;
      var data = nodedata;
      if (nodedata.children && nodedata.children.length > 0) {
        $message("本节点包含子节点,如需删除请先删除子节点。", "warning", this);
        return;
      }
      this.$confirm("此操作将永久该分类数据, 是否继续?", "提示", {
        type: "warning"
      }).then(function () {
        // 删除数据
        ajax_json("tpt/tptype/" + data.tpTypeId, "delete", null,
          result => {
            if (result.status) {
              tpt.visible = false;
              ins.rechoseTree(data);
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

      _editor.setContent('');
    },
    // 新增模板
    addTemplate: function () {
      this.resetTemplate();

      this.tp.visible = true;
      this.tp.update = false;
      this.tp.title = "新增模板";

    },
    //重置模板类别
    resetTemplate() {
      this.tp.data = {
        previewPicture: '',
        programTemplate: ''
      };
      _editor.setContent("");
    },
    // 修改模板
    updateTemplate: function (tp) {
      this.resetTemplate();
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
              ins.resetTemplate();
            }
          });
        } else {
          // 新增数据
          ajax_json("tp/template", "post", tp.data, function (result) {
            if (result.status) {
              tp.visible = false;
              ins.loadTreeData();
              ins.reloadTpTypeData();
              ins.resetTemplate();
            }
          });
        }
      });
    },
    handleAvatarSuccess(res, file) {
      this.tp.data.previewPicture = res.url; //URL.createObjectURL(file.raw);
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
    },
    choseProgramTemplate(pt) {
      console.log("已选择节目模版", pt)
      this.ptw.visiable = false;
      this.tp.data.programTemplate = pt.programId;
      this.tp.data.programTemplateName = pt.name;
      this.tp.programTempate = pt;
      this.tp.data.categoryId = pt.categoryId;
    },
    handleSizeChange(val) {
      this.tpager.size = val;
      this.loadTpTypeData();
    },
    handleCurrentChange(val) {
      this.tpager.current = val;
      this.loadTpTypeData();
    },
    getResUrl(url) {
      return serverConfig.getUrl(url);
    }
  }
});
window.appInstince = appInstince;

// 编辑器
var _editor = UE.getEditor("templateText", {

});

function getTpTypeIds(data, tpt_data) {
  let ay = breadPath(data, tpt_data, item => item.children, item => item.parent, item => item.tpTypeId, item => item.data);

  return ay.reverse().map(function (a) {
    return a.tpTypeId;
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