 
import {comps} from '../components/index.js';

/* istanbul ignore if */
if (typeof window !== 'undefined' && window.Vue) {
    comps.map((comp) => {
        Vue.component(comp.info.name,comp);
    });
}
 