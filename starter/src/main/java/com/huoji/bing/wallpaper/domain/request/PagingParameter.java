package com.huoji.bing.wallpaper.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数
 */
@Data
public class PagingParameter implements Serializable {

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final int MAX_PAGE_SIZE = 1000;

    public static final int FIRST_PAGE = 1;

    public PagingParameter() {
        setPage(FIRST_PAGE);
        setLimit(DEFAULT_PAGE_SIZE);
    }

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 每页记录数
     */
    private Integer limit;

    /**
     * 总数
     */
    private long total;

    public static void setDefault(PagingParameter parameter) {
        if (parameter.getPage() == null) {
            parameter.setPage(FIRST_PAGE);
        }
        if (parameter.getLimit() == null) {
            parameter.setLimit(DEFAULT_PAGE_SIZE);
        }
        if (parameter.getLimit() > PagingParameter.MAX_PAGE_SIZE) {
            parameter.setLimit(PagingParameter.MAX_PAGE_SIZE);
        }
    }

    public void init() {
        setDefault(this);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
