package com.ds.report_system.dto.user;

import com.ds.report_system.dto.report.ReportPriority;
import com.ds.report_system.dto.report.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminReportResponse {
    private Long id;
    private String title;
    private String description;
    private ReportStatus reportStatus;
    private ReportPriority reportPriority;
    private Long userId;
    private LocalDateTime createdAt;
}
