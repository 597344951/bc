/**
 * vcharts 公共配置信息
 */

let config = {
    themeName: 'customed', //主题名
    //工具箱配置
    toolbox: {
        show: true,
        feature: {
            /**dataZoom: {
                yAxisIndex: 'none'
            },**/
            dataView: {
                readOnly: false
            },
          /**  magicType: {
                type: ['line', 'bar']
            },
            restore: {},**/
            saveAsImage: {}
        }
    }
}
export default config;