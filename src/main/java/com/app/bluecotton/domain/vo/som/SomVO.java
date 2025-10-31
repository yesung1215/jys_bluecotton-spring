package com.app.bluecotton.domain.vo.som;

import lombok.Data;

import java.util.Date;

@Data
public class SomVO {
    private Long id;
    private String somTitle;
    private String somCategory;
    private String somContent;
    private String somType;
    private String somAddress;
    private Date somStartDate;
    private Date somEndDate;
    private Integer somCount;
    private Integer somLike;
}
