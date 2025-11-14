package com.app.bluecotton.domain.vo.post;

import lombok.Data;

import java.util.Date;

@Data
public class PostReplyReportVO {
    private Long id;
    private String postReplyReportContent;
    private Long postReplyId;
    private Long memberId;
    private Date postReplyReportCreateAt;
}
