package com.jiyun.utils;

import java.util.List;

// 分页结果集
public class PageResult<T> {
    private  Integer pageIndex; // 当前页
    private Integer totalPage; //总页数
    private List<T> result; // 当前页的数据

    public PageResult(Integer pageIndex, Integer totalPage, List<T> result) {
        this.pageIndex = pageIndex;
        this.totalPage = totalPage;
        this.result = result;
    }

    public PageResult() {
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
