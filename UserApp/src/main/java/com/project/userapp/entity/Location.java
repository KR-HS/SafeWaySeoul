package com.project.userapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.catalina.User;

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


//    @Column(name = "record_key", insertable = false, updatable = false)
    private Integer recordKey;
//    @Column(name = "user_key", insertable = false, updatable = false)
    private Integer userKey;

    @Column(name = "kinder_key", insertable = false, updatable = false)
    private Integer kinderKey;

    @OneToOne
    @JoinColumn(name = "kinder_key")
    @JsonIgnore
    private Kinder kinder;

//    @OneToOne
//    @JoinColumn(name="user_key")
//    private User user;
//
//    @OneToOne
//    @JoinColumn(name="record_key")
//    private Record record;
}
