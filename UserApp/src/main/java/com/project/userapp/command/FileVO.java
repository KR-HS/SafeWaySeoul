 package com.project.userapp.command;

 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;

 import java.sql.Timestamp;

 import javax.persistence.Entity;
 import javax.persistence.Id;
 import javax.persistence.Table;

 @Entity
 @Table(name = "files")
 @Data
 @AllArgsConstructor
 @NoArgsConstructor
 @Builder
public class FileVO {
     @Id
     private Integer fileKey;
     private String fileName;
     private String filePath;
     private String fileUuid;
     private Timestamp fileRegdate;
     private Integer childKey;
     private Integer kinderKey;
     private Integer userKey;

     private Integer postKey;
}
