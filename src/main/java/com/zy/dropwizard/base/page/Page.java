package com.zy.dropwizard.base.page;

import java.util.Objects;

/**
 * Component:
 * Description:
 * Date: 15/11/4
 *
 * @author yue.zhang
 */
public class Page {

    private Integer pageNum; // 页码

    private Integer pageSize; // 页大小

    // 当前页码偏移量(用户查询数据库使用)
    private Integer offset;

    public Page() {
        this(1, 100);
    }

    public Page(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.offset = getOffset();
    }

    public Integer getOffset() {
        if (pageNum == null || pageSize == null) {
            return null;
        }
        return (pageNum - 1) * pageSize;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (null == obj) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Page page = (Page) obj;

        return Objects.equals(this.pageNum, page.getPageNum())
                && Objects.equals(this.pageSize, page.getPageSize());
    }

    @Override
    public int hashCode() {
        int result = 14; // 这个值随便填写

        result = 31 * result + (pageNum == null ? 0 : pageNum); // 用31这个数字比较奇特，可以使得hashCode保持更加的均匀
        result = 31 * result + (pageSize == null ? 0 : pageSize);

        return result;
    }
}
