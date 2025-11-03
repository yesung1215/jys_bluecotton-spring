package com.app.bluecotton.domain.vo.post;

import lombok.Data;

@Data
public class PostReplyReportVO {
    private Long id;
    private String postReplyReportContent;
    private Long postReplyId;
    private Long memberId;
}
