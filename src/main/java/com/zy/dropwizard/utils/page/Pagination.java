package com.zy.dropwizard.utils.page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/11/4
 *
 * @author yue.zhang
 */
@Setter
@Getter
public class Pagination<E> {

    private List<E> list;

    private Integer pageNum;

    private Integer pageSize;

    private Integer totals;

    public Pagination(){

    }

    public Pagination(List<E> list, Integer pageNum, Integer pageSize, Integer totals) {
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totals = totals;
    }

}
