package com.project.userapp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordMatchVO {
    private Integer rmKey;
    private Integer kmKey;
    private Integer recordKey;
}
