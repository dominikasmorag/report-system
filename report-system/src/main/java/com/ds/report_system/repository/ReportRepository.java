package com.ds.report_system.repository;

import com.ds.report_system.entity.ReportEntity;
import com.ds.report_system.pojo.ReportPriority;
import com.ds.report_system.pojo.ReportStatus;
import com.ds.report_system.pojo.StatusCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

    Page<ReportEntity> findByStatus(ReportStatus status, Pageable pageable);

    Page<ReportEntity> findByPriority(ReportPriority priority, Pageable pageable);

    @Query("SELECT new com.ds.report_system.pojo.StatusCount(r.status, COUNT(r)) FROM ReportEntity r GROUP BY r.status")
    List<StatusCount> countByStatus();

    Page<ReportEntity> findByUserUsername(String username, Pageable pageable);

    Page<ReportEntity> findByUserUsernameAndStatus(String username, ReportStatus status, Pageable pageable);

    Page<ReportEntity> findByUserUsernameAndPriority(String username, ReportPriority priority, Pageable pageable);

    Page<ReportEntity> findByUserUsernameAndStatusAndPriority(String username, ReportStatus status, ReportPriority priority, Pageable pageable);




}
