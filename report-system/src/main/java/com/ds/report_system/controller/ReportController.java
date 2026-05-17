package com.ds.report_system.controller;

import com.ds.report_system.pojo.Report;
import com.ds.report_system.pojo.ReportPriority;
import com.ds.report_system.pojo.ReportStatus;
import com.ds.report_system.pojo.StatusCount;
import com.ds.report_system.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Returns paginated list of reports.
     * Allows filtering by status and priority
     * @param status optional filter
     * @param priority optional filter
     * @param pageable pagination info (page, size, sort)
     * @return page of reports
     */
    @GetMapping(value = "/api/reports")
    public Page<Report> getPage(
            @RequestParam(required = false) ReportStatus status,
            @RequestParam(required = false) ReportPriority priority,
            Pageable pageable) {
        return reportService.getPage(status, priority, pageable);
    }

    /**
     * Returns one report by id
     * @param id required
     * @return report
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/api/reports/{id}")
    public Report findById(@PathVariable Long id) {
        return reportService.findById(id);
    }

    @GetMapping(value = "/api/reports/status-count")
    public List<StatusCount> countByStatus() {
        return reportService.countByStatus();
    }

    @PostMapping("/api/reports")
    public Report create(@Valid @RequestBody Report report) {
        return reportService.save(report);
    }

}
