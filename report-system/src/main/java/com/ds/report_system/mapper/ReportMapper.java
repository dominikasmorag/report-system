package com.ds.report_system.mapper;

import com.ds.report_system.entity.ReportEntity;
import com.ds.report_system.pojo.Report;
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
                entity.getDateOfReport()
        );
    }

    public ReportEntity toEntity(Report dto) {
        return new ReportEntity(
                dto.getTitle(),
                dto.getDescription(),
                dto.getStatus(),
                dto.getPriority(),
                dto.getDateOfReport()
        );
    }
}
