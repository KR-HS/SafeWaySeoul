 package com.project.userapp.command;

 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;

 import java.sql.Timestamp;

 @Data
 @AllArgsConstructor
 @NoArgsConstructor
 @Builder
public class ChildrenVO {
     private Integer childKey;
     private String childName;
     private String childBirth;
     private String childGender;
     private Integer parentKey;

     private KinderVO kinderVO;

     private RecordVO recordVO;
     private DriveInfoVO driveInfoVO;
     private String profileImageUrl;


     //
     private UserVO userVO;
     private String dropState;
     private String recordName;
     private String driveInfoName;
     private String recordState;
     private Timestamp recordStartTime;
     private Timestamp recordEndTime;

     private String kmPickup;


 }
