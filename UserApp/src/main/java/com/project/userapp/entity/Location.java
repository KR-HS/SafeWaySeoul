package com.project.userapp.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="LOCATION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationKey;

    private String latitude;
    private String longitude;
    private Timestamp locationRegtime;
    private Integer recordKey;
    private Integer userKey;
    private Integer kinderKey;
}
