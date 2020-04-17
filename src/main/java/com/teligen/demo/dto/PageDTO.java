package com.teligen.demo.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页公共类
 */
public class PageDTO {
    @NotNull
    @Min(1)
    private Integer pageIndex;
    @NotNull
    @Max(1000)
    @Min(1)
    private Integer pageSize;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PageDTO(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public PageDTO() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pageIndex\":")
                .append(pageIndex);
        sb.append(",\"pageSize\":")
                .append(pageSize);
        sb.append('}');
        return sb.toString();
    }
}
