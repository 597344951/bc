/**
 * vcharts 公共配置信息
 */

let config = {

    themeName: 'customed', //主题名
    label:{
    	color:"#fff",
    },
    barWidth:'40',
    textStyle: {
        color: '#fff',
    },
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
    },
    legend: {
        x: 'center', // 'center' | 'left' | {number},
        y: 'bottom', // 'center' | 'bottom' | {number}
        test: '',
        	textStyle:{
        		color:'#fff',
        },
    }
   
}
export default config;