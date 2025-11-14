package com.app.bluecotton.domain.vo.post;

import lombok.Data;

import java.util.Date;

@Data
public class PostReportVO {
    private Long id;
    private String postReportContent;
    private Long memberId;
    private Long postId;
    private Date postReportCreateAt;
}
