/**
 * 费用计划 模块js部分
 */

let CostPlan = {
    info: {
        name: 'cost-plan',
        template_url: '/components/calendar/cost-plan.view.html',
        author: 'Wangch',
        descript: '活动费用计划编辑'
    },
    props: {
        costPlanData: {
            type: Array,
            default: function () {
                return [];
            }
        },
        mode: {
            type: String,
            default: 'view'
        }
    },
    //['costPlanData', 'costTypeGroup'],
    data: function () {
        return {
            costTypeGroup: [],
            formModel: {},
            rules: {
                name: [{
                        required: true,
                        message: "请输入费用名",
                        trigger: "blur"
                    },
                    {
                        min: 1,
                        max: 50,
                        message: "长度在 1 到 50个字符",
                        trigger: "blur"
                    }
                ],
                monely: [{
                        required: true,
                        message: "请输入金额",
                        trigger: "blur"
                    },
                    {
                        type: 'number',
                        message: '金额必须是数字值'
                    }
                ],
                costType: [{
                    required: true,
                    message: "请选择分类",
                    trigger: "change"
                }]
            }
        }
    },
    watch: {
        costPlanData: function (val, oldVal) {
            //console.log('new: %s, old: %s', val, oldVal)
            //初始化 加载类型
            val.forEach(e1 => {
                this.changeCostType(e1);
            });
            this.reBindFormModel();
        }
    },
    computed: {
        /**转换花费数据**/
        costValues() {
            if (!this.costPlanData || this.costPlanData.length == 0) return [0];
            const values = this.costPlanData.map(item => {
                if (item.$CostType) {
                    return item.$CostType.type * item.value;
                }
                return 0;
            });
            return values;
        },
        //赞助费用
        sponsorship_fee() {
            const values = this.costValues;
            let in_sum = 0;
            var ay1 = values.filter(i => i > 0);
            if (ay1.length > 0) in_sum = ay1.reduce((a, b) => a + b);
            return in_sum;
        },
        cost_fee() {
            const values = this.costValues;
            let minus_sum = 0;
            let ay2 = values.filter(i => i < 0);
            if (ay2.length > 0) minus_sum = ay2.reduce((a, b) => a + b);
            return minus_sum;
        },
        total_fee() {
            return this.sponsorship_fee + this.cost_fee;
        }
    },
    mounted() {
        this.loadCostTypeData();
        setTimeout(() => {
            for (let i = 0; i < this.costPlanData.length; i++) {
                let e1 = this.costPlanData[i];
                this.changeCostType(e1);
            }
            this.reBindFormModel();
            this.reDraw();
        }, 50);
    },
    methods: {
        reDraw() {
            //fake: 触发Vue重新计算属性
            this.costPlanData.push({});
            this.costPlanData.pop();
        },
        loadCostTypeData() {
            let url = '/costplan/costtype';
            ajax_promise(url, 'get', {}).then(result => {
                this.costTypeGroup = result.data;
            });
        },

        //绑定输入的costPlanData 到 formModel上
        reBindFormModel() {
            $.each(this.costPlanData, (i, cpd) => {
                $.each(cpd, (k, v) => {
                    this.formModel[k + '_' + i] = v;
                });
            });
        },
        inputChange(fn, idx) {
            console.log(fn, idx, 'change');
            let v = this.costPlanData[idx][fn];
            this.formModel[fn + '_' + idx] = v;
        },
        addNewCostItem() {
            this.costPlanData.push({
                name: '',
                value: 0
            });
        },
        delCostItem(obj, index) {
            console.log(arguments);
            this.$confirm('是否删除该条记录?', '提示', {
                type: 'warning'
            }).then(() => {
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                this.costPlanData.splice(index, 1);
            }).catch(() => {});
        },
        //花费主题计算
        computCostTypeTheme(obj) {
            var row = obj.row;
            var rowIndex = obj.rowIndex;
            if (row.$CostType && row.$CostType.type < 0) {
                return 'row-minus';
            }
            return '';
        },
        costSummaries(param) {
            const sums = [];
            sums[0] = "赞助: " + this.sponsorship_fee + " 元";
            sums[1] = "支出: " + this.cost_fee + " 元";
            sums[2] = "共计: " + this.total_fee + " 元";
            return sums;
        },
        changeCostType(row, fn, idx) {
            let tv = row.costType;
            this.costTypeGroup.forEach(e1 => {
                if (!e1.children) return;
                e1.children.forEach(e2 => {
                    if (e2.data.costType == tv) {
                        row.$CostType = e2.data;
                    }
                })
            });
            if (fn && idx) {
                this.inputChange(fn, idx);
            }
        },
        finsh() {
            this.$refs.cpform.validate((valid) => {
                if (!valid) {
                    return false;
                }
                this.$emit("complete", this.costPlanData)
            });
        }
    },
    template: require("./cost-plan.view.html")
}

export default CostPlan;