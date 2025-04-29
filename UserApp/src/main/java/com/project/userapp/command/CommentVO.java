package com.project.userapp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentVO {
    private Integer commentId;
    private Integer commentLV;
    private Integer commentParentLV;
    private String commentContent;
    private Timestamp commentRegdate;

    private Integer postKey;
    private Integer userKey;
}
