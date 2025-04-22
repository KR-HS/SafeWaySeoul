 package com.project.userapp.command;

 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;

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

     private String profileImageUrl;
}
