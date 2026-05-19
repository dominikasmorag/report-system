package com.ds.report_system.dto.report;

public class StatusCount {
    private ReportStatus reportStatus;
    private Long count;

    public StatusCount(ReportStatus reportStatus, Long count) {
        this.reportStatus = reportStatus;
        this.count = count;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public Long getCount() {
        return count;
    }
}
