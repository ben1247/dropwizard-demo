package com.zy.dropwizard.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Component:
 * Description:
 * Date: 17/5/13
 *
 * @author yue.zhang
 */
@Setter
@Getter
public class Book {
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private Double price;
    private Timestamp createTime;
    private Timestamp updateTime;
}
