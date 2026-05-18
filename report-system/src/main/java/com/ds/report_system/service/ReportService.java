package com.ds.report_system.service;

import com.ds.report_system.entity.ReportEntity;
import com.ds.report_system.entity.UserEntity;
import com.ds.report_system.mapper.ReportMapper;
import com.ds.report_system.pojo.Report;
import com.ds.report_system.pojo.ReportPriority;
import com.ds.report_system.pojo.ReportStatus;
import com.ds.report_system.pojo.StatusCount;
import com.ds.report_system.repository.ReportRepository;
import com.ds.report_system.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@DependsOn("userService")
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, ReportMapper reportMapper, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
        this.userRepository = userRepository;
    }

    public Report findById(Long id) {
        return reportMapper.toDto(reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found")));
    }

    public List<StatusCount> countByStatus() {
        return reportRepository.countByStatus();
    }

    public Report save(Report report) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ReportEntity entity = reportMapper.toEntity(new Report(report.getTitle(), report.getDescription(), ReportStatus.NEW, ReportPriority.NEUTRAL, LocalDateTime.now(), userRepository.findByUsername(username).get().getId()), user);
        entity.setUser(user);
        ReportEntity saved = reportRepository.save(entity);
        return reportMapper.toDto(saved);
    }

    public Page<Report> getPage(ReportStatus status, ReportPriority priority, Pageable pageable) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        boolean isAdmin = auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return reportRepository.findAll(pageable)
                    .map(reportMapper::toDto);
        }

        if(status != null && priority != null) {

            return reportRepository.findByUserUsernameAndStatusAndPriority(username, status, priority, pageable).map(reportMapper::toDto);
        }

        if (priority != null) {
            return reportRepository.findByUserUsernameAndPriority(username, priority, pageable).map(reportMapper::toDto);
        }

        if (status != null) {
            return reportRepository.findByUserUsernameAndStatus(username, status, pageable).map(reportMapper::toDto);
        }

        else {
            return reportRepository.findByUserUsername(username, pageable).map(reportMapper::toDto);
        }
    }


    @PostConstruct
    public void init() {
        reportRepository.save(reportMapper.toEntity(new Report("Report1", "opis", ReportStatus.NEW, ReportPriority.HIGH, LocalDateTime.now(), userRepository.findByUsername("admin").get().getId()), userRepository.findByUsername("admin").orElseThrow()));
        reportRepository.save(reportMapper.toEntity(new Report("Report2", "opis", ReportStatus.NEW, ReportPriority.HIGH, LocalDateTime.now(), userRepository.findByUsername("admin").get().getId()), userRepository.findByUsername("admin").orElseThrow()));
        reportRepository.save(reportMapper.toEntity(new Report("Report3", "opis", ReportStatus.NEW, ReportPriority.HIGH, LocalDateTime.now(), userRepository.findByUsername("admin").get().getId()), userRepository.findByUsername("admin").orElseThrow()));
    }
}
