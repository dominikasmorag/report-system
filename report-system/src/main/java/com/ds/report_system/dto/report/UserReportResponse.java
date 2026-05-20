package com.ds.report_system.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserReportResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
