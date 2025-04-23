package com.project.userapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name ="KINDER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Kinder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kinderKey;

    private String kinderName;
    private String kinderPhone;
    private String kinderPostcode;
    private String kinderAddress;
    private String kinderOpenTime;
    private String kinderCloseTime;
    private String kinderWeekendOpen;
    private String kinderNightOpen;
    private Integer kinderCapacity;

    @OneToOne(mappedBy = "kinder", fetch = FetchType.LAZY)
    private Location location;
}
