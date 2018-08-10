 
import {comps} from '../components/index.js';
import {vueComps} from '../components/index-vue.js';

let array = [];
/* istanbul ignore if */
if (typeof window !== 'undefined' && window.Vue) {
    comps.map((comp) => {
        array.push(comp.info);
        Vue.component(comp.info.name,comp);
    });
    vueComps.map((comp) => {
        array.push(comp.info);
        Vue.component(comp.info.name,comp);
    });
    window.componentInfos = array;
}
 