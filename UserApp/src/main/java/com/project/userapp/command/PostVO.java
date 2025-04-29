package com.project.userapp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostVO {

    private Integer postKey;
    private String postTitle;
    private String postContent;
    private Timestamp postRegdate;
    private Integer userKey;

    private String userName;
    private String kinderName;

    private Integer countComment;

    // 상대 시간 계산 메서드
    public String getElapsedTime() {
        if (postRegdate == null) {
            return "";
        }

        // Timestamp → LocalDateTime 변환
        LocalDateTime postTime = postRegdate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(postTime, now);

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();
        long months = days / 30;
        long years = days / 365;

        if (minutes < 1) {
            return "방금 전";
        } else if (minutes < 60) {
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else if (days < 30) {
            return days + "일 전";
        } else if (months < 12) {
            return months + "개월 전";
        } else {
            return years + "년 전";
        }
    }

}
