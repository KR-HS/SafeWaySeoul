package com.project.userapp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KinderMatchVO {
    private Integer kmKey;
    private String kmPickup;
    private Integer childKey;
    private Integer kinderKey;
}
