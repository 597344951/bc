package com.zltel.broadcast.common.pager;

import com.github.pagehelper.Page;

public class PagerHelper {

    private PagerHelper() {}

    /** 精简分页信息 **/
    public static Pager toPager(Page<?> page) {
        Pager pager = new Pager(page.getPageNum(), page.getPageSize());
        pager.setTotal(page.getTotal());
        return pager;
    }
}
