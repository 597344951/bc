package com.zltel.broadcast.lesson.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.pager.PagerHelper;
import com.zltel.broadcast.lesson.bean.LessonSection;
import com.zltel.broadcast.lesson.bean.LessonUnit;
import com.zltel.broadcast.lesson.bean.LessonUnitProgress;
import com.zltel.broadcast.lesson.service.LessonUnitService;
import com.zltel.broadcast.resource.bean.ResourceMaterial;
import com.zltel.broadcast.resource.service.ResourceMaterialService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lesson/unit")
public class LessonUnitController extends BaseController {

    @Resource
    private LessonUnitService unitService;
    @Resource
    private ResourceMaterialService resourceService;

    @ApiOperation("搜索")
    @PostMapping("/unit/search/{pageNum}-{pageSize}")
    public R search(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize,
            @RequestBody LessonUnit record) {
        SysUser user = getSysUser();
        record.setLearnOrgId(user.getOrgId());
        Page<LessonUnit> page = PageHelper.startPage(pageNum, pageSize);
        List<LessonUnit> lessons = this.unitService.queryCategoryRelatedData(record);
        Pager pager = PagerHelper.toPager(page);
        return R.ok().setData(lessons).setPager(pager);
    }


    @ApiOperation("增加")
    @PostMapping("/unit")
    public R save(@RequestBody LessonUnit record) {
        this.initRelatedInfo(record);
        this.unitService.insertSelective(record);
        return R.ok();
    }



    @ApiOperation("更新分类信息")
    @PutMapping("/unit")
    public R update(@RequestBody LessonUnit record) {
        if (record == null || record.getLessonUnitId() == null) throw RRException.makeThrow("更新数据不能为空");
        this.initRelatedInfo(record);
        this.unitService.updateByPrimaryKeySelective(record);
        return R.ok();
    }

    @ApiOperation("删除分类信息")
    @DeleteMapping("/unit/{lessonUnitId}")
    public R delete(@PathVariable("lessonUnitId") Integer lessonUnitId) {
        LessonUnit lc = this.unitService.selectByPrimaryKey(lessonUnitId);
        SysUser user = getSysUser();
        if (null != lc && lc.getOrgId() == user.getOrgId() && lc.getUserId() == user.getUserId()) {
            this.unitService.deleteByPrimaryKey(lessonUnitId);
        }
        return R.ok();
    }

    @ApiOperation("获取分类信息")
    @GetMapping("/unit/{lessonUnitId}")
    public R get(@PathVariable("lessonUnitId") Integer lessonUnitId) {
        LessonUnit lc = this.unitService.selectByPrimaryKey(lessonUnitId);
        SysUser user = getSysUser();
        if (null != lc && lc.getOrgId() == user.getOrgId() && lc.getUserId() == user.getUserId()) {
            return R.ok().setData(user);
        }
        return R.ok();
    }

    @ApiOperation("播放指定课程")
    @GetMapping("/unit/play/{lessonUnitId}")
    public void play(@PathVariable("lessonUnitId") Integer lessonUnitId, Integer lessonId)
            throws ServletException, IOException {
        LessonUnit record = new LessonUnit();
        record.setLessonUnitId(lessonUnitId);
        List<LessonUnit> list = this.unitService.queryCategoryRelatedData(record);
        if (list.isEmpty()) {
            throw RRException.makeThrow("课程不存在");
        }
        LessonUnit lessonUnit = list.get(0);
        LessonSection currentLesson = null;
        LessonUnitProgress currentProgress = null;
        List<LessonUnitProgress> progress = lessonUnit.getLessonProgress();
        // 没有指定播放的课程, 自动选择一个
        if (null == lessonId) {
            Optional<LessonUnitProgress> option =
                    progress.stream().reduce((a, b) -> a.getSaveTime().getTime() > b.getSaveTime().getTime() ? a : b);

            // 尝试从播放记录选取 最后播放的条目
            if (option.isPresent()) {
                currentProgress = option.get();
                lessonId = currentProgress.getLessonId();
            } else {
                // 从 课程列表中获取第一个 课程
                Optional<LessonSection> opt =
                        lessonUnit.getLessonTree().stream().flatMap(lu -> lu.getChildren().parallelStream())
                                .filter(lu -> lu.getSourceType() != 2).findFirst();
                if (opt.isPresent()) {
                    currentLesson = opt.get();
                    currentProgress = new LessonUnitProgress();
                    currentProgress.setLessonUnitId(currentLesson.getLessonUnitId());
                    currentProgress.setLessonId(currentLesson.getLessonId());
                    currentProgress.setPlayProgress(0);
                    currentProgress.setCreditHours(0);
                }
            }
        } else {
            final Integer id = lessonId;
            Optional<LessonUnitProgress> opt = progress.stream().filter(p -> p.getLessonId() == id).findFirst();
            if (opt.isPresent()) currentProgress = opt.get();
        }
        if (currentLesson == null) {
            final Integer id = lessonId;
            Optional<LessonSection> opt =
                    lessonUnit.getLessonList().stream().filter(ls -> ls.getLessonId() == id).findFirst();
            if (opt.isPresent()) currentLesson = opt.get();
        }
        if (currentLesson != null && currentLesson.getSourceType() == 0) {
            ResourceMaterial rm =
                    this.resourceService.selectByPrimaryKey(Integer.valueOf(currentLesson.getSourceData()));
            this.request.setAttribute("rm", rm);
        }


        this.request.setAttribute("lessonUnit", lessonUnit);
        this.request.setAttribute("progress", progress);
        this.request.setAttribute("currentProgress", currentProgress);
        this.request.setAttribute("currentProgressJson", JSON.toJSONString(currentProgress));
        this.request.setAttribute("currentLesson", currentLesson);
        this.request.setAttribute("currentLessonJson", JSON.toJSONString(currentLesson));

        this.forward(request, response, "/view/learn-center/lesson-unit.jsp");
    }



    private void initRelatedInfo(LessonUnit record) {
        SysUser user = getSysUser();
        record.setOrgId(user.getOrgId());
        record.setUserId(user.getUserId());
    }
}
