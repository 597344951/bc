//1. 引入定义的组件
import MixedComp from './mixed/MixedComp.vue';
import dangshi from './dang/dangshi.vue';

let compMap = new Map();
//2. 将要自动注册的组件放在此数组中
export const vueComps = [MixedComp,dangshi
];

