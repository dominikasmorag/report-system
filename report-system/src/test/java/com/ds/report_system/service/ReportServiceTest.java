package com.ds.report_system.service;

import com.ds.report_system.entity.ReportEntity;
import com.ds.report_system.exceptions.report.ReportNotFoundException;
import com.ds.report_system.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    ReportRepository repository;

    @InjectMocks
    ReportService service;

    @Test
    void shouldThrowReportNotFoundException() {
        ReportEntity reportEntity = new ReportEntity();

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                ReportNotFoundException.class,
                () -> service.findById(1L)
        );
    }

}
