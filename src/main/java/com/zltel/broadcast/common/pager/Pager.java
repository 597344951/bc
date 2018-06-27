package com.zltel.broadcast.common.pager;

import com.github.pagehelper.PageRowBounds;

/**
 * 拓展 PageRowBounds ,支持页码分页
 * 
 * @author wangch
 *
 */
public class Pager extends PageRowBounds {
    /** 当前页码 **/
    private int pageIndex;

    /**
     * 创建分页对象
     * 
     * @param pageIndex 页码
     * @param limit 限制条数
     */
    public Pager(int pageIndex, int limit) {
        super(pageOfoffset(pageIndex, limit), limit);
        this.setPageIndex(pageIndex);
    }

    private static int pageOfoffset(int pi, int limit) {
        int r = (pi - 1) * limit;
        return r < 0 ? 0 : r;
    }

    /**
     * 当前页码数
     * 
     * @return
     */
    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex < 0 ? 1 : pageIndex;
    }

}
