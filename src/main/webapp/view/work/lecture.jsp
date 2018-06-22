<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>内容报告</title>
    <%@include file="/include/base.jsp" %>
    <%@include file="/include/element-ui.jsp" %>
    <%@include file="/include/ueditor.jsp" %>
    <style>
        .el-table thead, .el-table__row {
            font-size: 14px;
        }

        .el-row {
            margin: 5px auto;
			width: 95%;
        }

        #lecture {
            display: none;
        }
    </style>
</head>
<body>
<div id="app">
    <el-container>
        <el-header>
            <h3>内容报告</h3>
        </el-header>
        <el-main>
            <el-row :gutter="10">
                <el-col :span="2">
                    <el-button type="primary" icon="el-icon-plus" @click="openNewLectureDialog()"></el-button>
                </el-col>
            </el-row>
            <el-table :data="lecture.list" border style="width: 100%">
                <el-table-column prop="name" label="提交人"></el-table-column>
                <el-table-column prop="date" label="提交时间"></el-table-column>
                <el-table-column prop="type" label="报告类型"></el-table-column>
                <el-table-column prop="category" label="报告属性"></el-table-column>
                <el-table-column prop="desc" label="描述"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" @click="lecturePreview">查看报告内容</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-main>
        <el-footer>
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="lecture.pageNum"
                    :page-sizes="pageSizes"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="lecture.total">
            </el-pagination>
        </el-footer>
    </el-container>

    <%--添加计划弹窗--%>
    <el-dialog title="新建报告" :visible.sync="addLectureDialogVisible" :modal="false">
        <el-form :model="newLecture" label-position="left" label-width="120px">
            <el-form-item label="描述">
                <el-input v-model="newLecture.desc" placeholder="报告描述"></el-input>
            </el-form-item>
            <el-form-item label="类型">
                <el-select v-model="newLecture.type" placeholder="报告类型">
                    <el-option label="工作计划" value="workPlan"></el-option>
                    <el-option label="工作总结" value="workSummary"></el-option>
                    <el-option label="学习心得" value="learningExperience"></el-option>
                    <el-option label="思想汇报" value="thoughtReport"></el-option>
                </el-select>
                <el-select v-model="newLecture.category" placeholder="工作计划类型" v-if="newLecture.type == 'workPlan'">
                    <el-option label="周度计划" value="week"></el-option>
                    <el-option label="月度计划" value="month"></el-option>
                    <el-option label="季度计划" value="season"></el-option>
                    <el-option label="年度计划" value="year"></el-option>
                </el-select>
                <el-input v-model="newLecture.category" placeholder="工作内容" v-if="newLecture.type == 'workSummary'" style="width: 250px"></el-input>
                <el-input v-model="newLecture.category" placeholder="学习内容" v-if="newLecture.type == 'learningExperience'" style="width: 250px"></el-input>
            </el-form-item>
            <el-form-item label="内容模板">
                <el-select v-model="newLecture.template" placeholder="内容模板">
                    <el-option label="不使用模板" value="0"></el-option>
                    <el-option label="模板一" value="1"></el-option>
                    <el-option label="模板二" value="2"></el-option>
                    <el-option label="模板三" value="3"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="内容">
                <div id="planContent"></div>
            </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
            <el-button @click="addLectureDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addLecture()">添 加</el-button>
        </div>
    </el-dialog>
</div>
<div id="lecture"></div>
<script>
    var planContent;
    var app = new Vue({
        el: '#app',
        data: {
            keyword: '',
            pageSizes: [10, 20, 50, 100],
            pageSize: 10,
            lecture: {
                total: 1,
                pageNum: 1,
                list: [{
                    name: '工作人员一',
                    date: '2018-06-01',
                    type: '工作计划',
                    category: '月度工作计划',
                    desc: '六月份工作计划'
                }]

            },
            addLectureDialogVisible: false,
            newLecture: {}
        },
        methods: {
            handleSizeChange(pageSize) {
                load(1, pageSize)
            },
            handleCurrentChange(curPage) {
                load(curPage, this.pageSize)
            },
            openNewLectureDialog () {
                this.addLectureDialogVisible = true;
                if (!planContent) {
                    setTimeout(() => {
                        planContent = UE.getEditor('planContent', {
                            serverUrl: '/ueditor/controller.jsp',
                            wordCount: false,
                            elementPathEnabled: false,
                            autoHeightEnabled: false
                        })
                    }, 1000)
                }
            },
            addLecture() {
                this.addLectureDialogVisible = false
                this.lecture.list.push({
                    name: '工作人员N',
                    date: '2018-06-01',
                    type: this.newLecture.type,
                    category: this.newLecture.category,
                    desc: this.newLecture.desc
                })
                this.lecture.total++

            },
            lecturePreview() {
                lecture.setContent('<h1 style="margin-top:20px;margin-right:0;margin-bottom:15px;margin-left:0;text-align:center;line-height:32px"><span style="font-size: 24px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">坚持以科学态度学用当代中国马克思主义</span></h1><p style="margin-top:10px;text-align:center;orphans: 2;widows: 2"><span style="font-size:12px;color:black">2018</span><span style="font-size:12px;color:black">年05月23日08:53&nbsp;</span></p><p style="line-height:25px"><span style="font-size:14px;color:black">原标题：坚持以科学态度学用当代中国马克思主义</span></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">以科学的态度对待科学，以真理的精神追求真理。这应该成为我们学习践行习近平新时代中国特色社会主义思想的重要原则。作为当代中国马克思主义，习近平新时代中国特色社会主义思想处处蕴含着辩证唯物主义和历史唯物主义的科学世界观和方法论，是一个内涵丰富、博大精深的思想宝库。党员领导干部必须深入学、持久学、刻苦学，不断从中汲取科学智慧和真理力量，切实领悟好、掌握好、运用好蕴含其中的立场、观点和方法。</span></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><strong><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">理解把握习近平新时代中国特色社会主义思想的时代性，不断涵养高举旗帜的政治定力</span></strong></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">时代是思想之母，实践是理论之源。改革开放之初，我们党发出了走自己的路、建设中国特色社会主义的伟大号召。从那时以来，我们党团结带领全国各族人民不懈奋斗，推动我国经济实力、科技实力、国防实力、综合国力进入世界前列，推动我国国际地位实现前所未有的提升，党的面貌、国家的面貌、人民的面貌、军队的面貌、中华民族的面貌发生了前所未有的变化，中华民族正以崭新姿态屹立于世界的东方。正如党的十九大报告所指出：“经过长期努力，中国特色社会主义进入了新时代，这是我国发展新的历史方位。”</span></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">黑格尔曾说，哲学是被把握在思想中的它的时代。伟大时代的到来，必然有伟大的精神力量来推动。拥抱新时代，就必须掌握新时代的思想武器，强化深入学习贯彻习近平新时代中国特色社会主义思想的理论自觉。一要真信。联系党的十八大以来的历史成就和历史变革，深刻感悟习近平新时代中国特色社会主义思想的实践伟力，深刻认识到只有坚持和发展中国特色社会主义，才是通往人民幸福、民族复兴的唯一道路；只有坚持以习近平新时代中国特色社会主义思想为指引，才能解决当下中国问题、引领我们爬坡过坎走向复兴；只有坚持和强化中国共产党的领导，中国才真正有希望和能力实现中华民族伟大复兴的中国梦，从而强化“四个意识”，坚定“四个自信”。二要真学。党的十九大把习近平新时代中国特色社会主义思想确立为党的指导思想，必须把学习贯彻这一党的理论创新最新成果作为长期的战略任务来抓。每名党员都要坚持读原著、学原文、悟原理，做到学而信、学而思、学而行，不断提高思想觉悟和理论素养，增强对党的理论创新成果的真理认同、思想认同、情感认同。三要真用。坚决摒弃学用脱节、装潢门面的做法，坚持问题导向，始终聚焦改革开放和社会主义现代化建设面临的重大现实问题、全局性战略问题、人民群众关心和关注的热点难点问题，为解决问题提供新理念、新思路、新办法，在解决问题中提高运用理论的能力和推动发展的水平。</span></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><strong><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">理解把握习近平新时代中国特色社会主义思想的创新性，以创新思维推动伟大事业的发展进步</span></strong></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">实践之路常新，理论之树常青。把坚持马克思主义和发展马克思主义统一起来，结合实践不断作出新的理论创造，这是马克思主义永葆生机活力的奥妙所在。习近平新时代中国特色社会主义思想，以我们正在做的事情为中心，直面前进道路上的各种困难和矛盾、风险和挑战，着力探索破解难题、推进事业发展的新理念新思想新战略，具有强烈的时代气息和现实针对性，充分反映了中国特色社会主义进入新时代的大思路、大战略、大智慧，开辟了马克思主义新境界、中国特色社会主义新境界、治国理政新境界、管党治党新境界，为发展马克思主义作出了重大原创性贡献。</span></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">当今世界正在发生广泛而深刻的变化，当代中国正在发生广泛而深刻的变革。我们必须跟上党的理论创新步伐，以党的理论创新最新成果指导实践创新，推动新时代中国特色社会主义事业向前发展。一是加强理论武装更新思想观念。要着眼深刻理解掌握习近平新时代中国特色社会主义思想“来一场大学习”，在学习中汲取思想力量，掌握思想方法，使习近平新时代中国特色社会主义思想深植于心、外化于行。二是胸怀大局提升战略思维。深刻把握习近平新时代中国特色社会主义思想洞察时代风云、把握时代大势的理论特质，学会从全局角度、战略高度思考问题，努力做到因势而谋、应势而动、顺势而为，不断增强工作的原则性、系统性、预见性、创造性。三是加大自主创新实现弯道超车。大力实施创新驱动战略，既抓好核心领域和关键技术的创新，又全面推动理论、制度、文化创新等各个方面创新；既学习西方先进经验和技术，又注意防止掉入陷阱，坚定不移走中国特色自主创新之路。</span></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><strong><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">理解把握习近平新时代中国特色社会主义思想的人民性，始终站在人民立场上担起初心使命</span></strong></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">人民性是马克思主义的根本立场，体现无产阶级政党鲜明的政治属性。习主席在党的十九大报告中鲜明提出，人民对美好生活的向往，就是我们的奋斗目标。中国共产党人的初心和使命，就是为中国人民谋幸福、为中华民族谋复兴。这些重要论述，突显了爱民、忧民、为民的真挚情怀，昭示了中国特色社会主义的价值取向和力量源泉所在。</span></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">人民立场是中国共产党的根本政治立场，是马克思主义政党区别于其他政党的显著标志。我们党来自人民、植根人民、服务人民，党的根基在人民、血脉在人民、力量在人民。学习贯彻习近平新时代中国特色社会主义思想，必须紧紧扭住“人民性”这一思想内核用力，在坚定人民立场上下功夫。一是始终牢记党性和人民性从来都是一致的、统一的。不论想什么、做什么，都要把人民放在心中最高位置。自觉摒弃私心杂念，做为人民谋幸福的新时代共产党人。二是以人民性改进作风。必须始终牢记，作风建设的核心是密切联系群众，作风问题的本质是脱离人民群众，“四个危险”中最大的危险是脱离群众的危险。要以永远在路上的执着和定力抓好作风建设，下大力纠治形式主义、官僚主义，严防享乐主义和奢靡之风变异反弹，确保党的群众观点、党的群众路线得到有力践行。三是以人民性强化担当。必须始终牢记，权力就是责任，责任就要担当；对于共产党人来说，我们的责任，就是对人民的责任，对人民的担当。党员干部必须牢固树立为民用权、为官有为的思想，坚决纠治一切庸政懒政怠政的思想行为。各级领导干部要敢于担当、勇于任事，带头真抓实干，带头从严要求，带头廉洁自律，以实际行动带领群众把强国强军伟业推向前进。</span></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><strong><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">理解把握习近平新时代中国特色社会主义思想的革命性，大力弘扬自我革命精神</span></strong></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">革命性与科学性相统一，是马克思主义的显著特征。党的十八大以来，我们党从一开始就强调以党的自我革命推动党领导人民进行伟大的社会革命，展开了一系列具有许多新的历史特点的伟大斗争，推动党和国家事业取得全方位、开创性历史成就，发生深层次、根本性历史变革。历史和现实都昭示我们，中国共产党正是依靠革命精神走进新时代，也必将依靠革命精神迈向更加光明的未来。</span></p><p style="margin-top:10px;margin-right:0;margin-bottom:10px;margin-left: 0;text-indent:32px;line-height:36px"><span style="font-size:18px;font-family:&#39;微软雅黑&#39;,sans-serif;color:black">习主席深刻指出，中华民族伟大复兴，绝不是轻轻松松、敲锣打鼓就能实现的，全党同志必须继续进行具有许多新的历史特点的伟大斗争。我们要充分认识这场伟大斗争的长期性、复杂性、艰巨性，发扬革命精神，提高斗争本领，不断夺取伟大斗争新胜利。一是与自身存在问题作斗争。坚定“全面从严治党永远在路上”的信念，按照新时代党的建设总要求，全面推进党的政治建设、思想建设、组织建设、作风建设、纪律建设，把党的制度建设贯穿始终，持之以恒正风肃纪，着力解决思想不纯、组织不纯、作风不纯等突出问题，夺取反腐败斗争压倒性胜利。二是与深化改革艰难险阻作斗争。坚定“只有改革开放才能发展中国、发展社会主义、发展马克思主义”的信念，以逢山开路、遇水架桥的勇气胆魄，坚决破除一切不合时宜的思想观念和体制机制弊端，勇于突破利益固化的藩篱，向一切阻碍改革、迟滞改革的问题开刀，把改革开放不断引向深入。三是与意识形态领域各种错误思潮作斗争。坚定“意识形态决定文化前进方向和发展道路”的信念，清醒看到意识形态领域斗争形势依然严峻复杂，牢固确立马克思主义在意识形态领域的指导地位，大力弘扬社会主义核心价值观，传承中华优秀传统文化，旗帜鲜明地讲好中国故事、传播中国声音，进一步增强全民族的文化自信，以意识形态的纯洁巩固保证国家长治久安。</span></p><p><br/></p>')
                lecture.execCommand('preview')
            }
        }
    });

    var lecture = UE.getEditor('lecture', {
        serverUrl: '/ueditor/controller.jsp',
        toolbars: [['fullscreen']],
        wordCount: false,
        elementPathEnabled: false
    });

    //load(1, app.pageSize);

    function load(pageNum, pageSize) {
        get('' + pageSize, reps => {
            if (reps.status) {
                app.lecture = reps.data;
            } else {
                app.$message('没有数据...');
            }
        });
    }

    function get(url, callback) {
        $.ajax({
            type: 'GET',
            url: url,
            dataType: 'json',
            success: function (reps) {
                callback(reps);
            },
            error: function (err) {
                app.$notify({
                    title: 'ERROR',
                    message: '系统错误...',
                    type: 'error'
                });
            }
        });
    }

    function post(url, postData, callback) {
        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(postData),
            success: function (reps) {
                callback(reps);
            },
            error: function (err) {
                app.$notify({
                    title: 'ERROR',
                    message: '系统错误...',
                    type: 'error'
                });
            }
        });
    }
</script>
</body>
</html>