package com.ds.report_system.entity;

import com.ds.report_system.pojo.ReportPriority;
import com.ds.report_system.pojo.ReportStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ReportEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ReportStatus status;
    @Enumerated(EnumType.STRING)
    private ReportPriority priority;
    private LocalDateTime dateOfReport;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ReportEntity(String title, String description, ReportStatus status, ReportPriority priority, LocalDateTime dateOfReport) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dateOfReport = dateOfReport;
    }
}
