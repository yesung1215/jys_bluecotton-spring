package com.app.bluecotton.domain.vo.som;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SomVO {
    private Long id;
    private String somTitle;
    private String somCategory;
    private String somAddress;
    private String somType;
    private Date somStartDate;
    private Date somEndDate;
    private Integer somLike;
    private String somContent;
    private Long memberId;
}
