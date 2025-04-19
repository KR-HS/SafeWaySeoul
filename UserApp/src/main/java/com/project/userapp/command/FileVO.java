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
public class FileVO {
     private Integer fileKey;
     private String fileName;
     private String filePath;
     private String fileUuid;
     private Timestamp fileRegdate;
     private Integer childKey;
     private Integer kinderKey;
     private Integer userKey;
}
