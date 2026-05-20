package com.ds.report_system.mapper;

import com.ds.report_system.dto.report.UserReportResponse;
import com.ds.report_system.dto.report.AdminReportResponse;
import com.ds.report_system.entity.ReportEntity;
import com.ds.report_system.entity.UserEntity;
import com.ds.report_system.dto.report.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    public ReportMapper() {}

    public Report toDto(ReportEntity entity) {
        return new Report(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getPriority(),
                entity.getDateOfReport(),
                entity.getUser().getId()
        );
    }

    public ReportEntity toEntity(Report dto, UserEntity user) {
        return new ReportEntity(
                dto.getTitle(),
                dto.getDescription(),
                dto.getStatus(),
                dto.getPriority(),
                dto.getDateOfReport(),
                user
        );
    }

    public UserReportResponse toUserReportResponse(ReportEntity reportEntity) {
        return new UserReportResponse(
                reportEntity.getId(),
                reportEntity.getTitle(),
                reportEntity.getDescription(),
                reportEntity.getDateOfReport()
        );
    }

    public AdminReportResponse toAdminReportResponse(ReportEntity reportEntity) {
        return new AdminReportResponse(
                reportEntity.getId(),
                reportEntity.getTitle(),
                reportEntity.getDescription(),
                reportEntity.getStatus(),
                reportEntity.getPriority(),
                reportEntity.getUser().getId(),
                reportEntity.getDateOfReport()
        );
    }
}
