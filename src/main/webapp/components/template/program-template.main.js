let ProgramTemplate = {
    info: {
        name: 'program-template', //注册组件名
        template_url: '/components/template/program-template.view.html', //模块 页面模板 url
        author: 'Wangch',
        descript: '选择节目模板'
    },
    data() {
        return {
            catelogTreeData: [{
                label: '一级 1',
                children: [{
                    label: '二级 1-1',
                    children: [{
                        label: '三级 1-1-1'
                    }]
                }]
            }, {
                label: '一级 2',
                children: [{
                    label: '二级 2-1',
                    children: [{
                        label: '三级 2-1-1'
                    }]
                }, {
                    label: '二级 2-2',
                    children: [{
                        label: '三级 2-2-1'
                    }]
                }]
            }, {
                label: '一级 3',
                children: [{
                    label: '二级 3-1',
                    children: [{
                        label: '三级 3-1-1'
                    }]
                }, {
                    label: '二级 3-2',
                    children: [{
                        label: '三级 3-2-1'
                    }]
                }]
            }],
            defaultProps: {
                children: 'children',
                label: 'label'
            },
            programTemplates:[{
                "oid": 0,
                "name": "WIN-OUNLE84KUAN",
                "id": "2",
                "code": "f3b55ceee1aee2025a381a6886adffc3",
                "type_id": "1",
                "res_time": "2018-05-16 10:02:40",
                "online": "1",
                "last_time": "Thu Jun 07 13:49:42 CST 2018",
                "ip": "192.168.1.42",
                "mac": "1c:87:2c:78:4f:85",
                "sys": "666666666",
                "size": "21寸",
                "ratio": "1440*900",
                "rev": "横屏",
                "ver": "V3.0.0.180510_B777",
                "typ": "非触摸",
                "tel": "6666666666",
                "addr": "666666666",
                "gis": "666666666",
                "warranty": "二年",
                "loc": "街道"
              },
              {
                "oid": 1,
                "name": "DESKTOP-99T4O79",
                "id": "1",
                "code": "671ab55be30695ce83eb01c5a2caf2e4",
                "type_id": "1",
                "res_time": "2018-05-15 13:51:57",
                "online": "0",
                "last_time": "Thu Jun 07 13:46:50 CST 2018",
                "ip": "192.168.1.48",
                "mac": "40:f0:2f:b3:2f:5f",
                "sys": null,
                "size": "21寸",
                "ratio": "1920*1080",
                "rev": "横屏",
                "ver": "V3.0.0.180510_B777",
                "typ": "非触摸",
                "tel": null,
                "addr": null,
                "gis": null,
                "warranty": "一年",
                "loc": "园区"
              }
            ]
        };
    },methods:{
        selectCatelog(data){
            console.log(data);
        }
    }
}

export default ProgramTemplate;