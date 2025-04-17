package com.project.userapp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
    private Integer userKey;
    @NotNull
    private String userId;
    @NotNull
    private String userPw;
    private String userName;
    private String userBirth;
    private String userAddressNumber;
    private String userAddressRoad;
    private String userAddressDetail;
    @NotNull
    private String userType;
    private String userPhone;

}
