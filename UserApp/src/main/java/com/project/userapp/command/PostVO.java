package com.project.userapp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostVO {
    private Integer postKey;
    private String postTitle;
    private String postContent;
    private Timestamp postRegdate;
    private Integer userKey;
}
