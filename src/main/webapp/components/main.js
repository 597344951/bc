//1. 引入定义的组件
import MessageNotice from '/components/common/message-notice.main.js';
import TitleH1 from '/components/common/title-h1.main.js';
import AddEvent from '/components/calendar/add-event.main.js';
import EventToolTip from '/components/calendar/event-tooltip.main.js';
import CostPlan from '/components/calendar/cost-plan.main.js';
import PlanSuggest from '/components/calendar/plan-suggest.main.js';

import EventCalendar from '/components/calendar/event-calendar.main.js';
import PubedPlanInfo from '/components/calendar/pubed-plan-info.main.js';
import PubplanGuide from '/components/calendar/pubplan-guide.main.js';
import CommunityInfo from '/components/loadAnimation/community-info.main.js';
import PmInfo from '/components/loadAnimation/pm-info.main.js';
import FestivalInfo from '/components/loadAnimation/festival-info.main.js';
import HotNews from '/components/loadAnimation/hot-news.main.js';
import LoadReport from '/components/loadAnimation/load-report.main.js';
import ProgramTemplate from '/components/template/program-template.main.js';

import ContextMenu from '/components/common/context-menu.main.js';
let compMap = new Map();
//2. 将要自动注册的组件放在此数组中
export const comps = [MessageNotice, TitleH1, AddEvent, EventToolTip, CostPlan, PlanSuggest,
    EventCalendar, PubedPlanInfo, PubplanGuide, CommunityInfo, PmInfo, FestivalInfo, HotNews, LoadReport ,
    ProgramTemplate,ContextMenu
];
comps.map((comp) => {
    if (checkModuleName(comp))
        installComponent(Vue, comp);
})

//3.到 /components/index.html 查看组件 

/**
 * 检测组件 名称是否重复
 * @param {*} comp 
 */
function checkModuleName(comp) {
    let {
        info
    } = comp;
    if (!info) {
        console.error('组件信息缺失! 跳过注册.', comp);
        return false;
    }
    let v = compMap.get(info.name);
    if (!v) {
        compMap.set(info.name, comp);
        return true;
    } else {
        console.error('组件名称不能冲突!', info.name, info);
        return false;
    }
}

/**
 * 全局注册 Vue组件
 * @param {*} Vue 
 * @param {*} comp 组件信息 
 */
export function installComponent(Vue, comp) {
    let {
        info
    } = comp;
    if (!info) {
        console.error('组件信息缺失! 跳过注册.', comp);
        return;
    }
    Vue.component(info.name, function (resolve, reject) {
        console.debug('注册组件:', info.name, info.descript);
        if (!info.template_url) {
            //不需要加载模版html
            resolve(comp);
        } else {
            html_require_promise(info.template_url).then((html) => {
                comp.template = html;
                resolve(comp);
            }).catch((_e) => {
                console.error('注册组件失败!', info, _e);
            });
        }
    })
}
/**
 * 返回异步组件
 */
export function init_components() {
    var components = {};
    //配置异步加载组件
    comps.map((comp) => {
        components[comp.info.name] = (resolve, reject) => require_comp(resolve, reject, comp);
    });
    return components;
}
//异步路由
export function init_routes() {
    let routes = [];
    comps.map((comp) => {
        let {
            info
        } = comp;
        let o = {
            path: '/' + info.name,
            component: (resolve, reject) => require_comp(resolve, reject, comp)
        }
        routes.push(o);
    });
    return routes;
}
//同步加载路由
export function build_routes(router) {
    comps.map((comp) => {
        let {
            info
        } = comp;
        var p = new Promise((resolve, reject) => {
            require_comp(resolve, reject, comp)
        });
        p.then(r => {
            let routes = {
                path: '/' + info.name,
                component: r
            }
            router.addRoutes([routes])
        });
    });
}