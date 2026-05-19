package com.ds.report_system.dto.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Report {
    private Long id;
    @NotBlank
    @Size(min=10, max=40)
    private String title;
    @NotBlank(message = "Minimum 20 chars")
    @Size(min=20, max=300)
    private String description;
    private ReportStatus status;
    private ReportPriority priority;
    private LocalDateTime dateOfReport;
    private Long userId;

    public Report(String title, String description, ReportStatus status, ReportPriority priority, LocalDateTime dateOfReport, Long userId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dateOfReport = dateOfReport;
        this.userId = userId;
    }

}
