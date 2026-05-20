package com.ds.report_system.dto.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReportRequest {
    @NotBlank
    @Size(min=10, max=40)
    private String title;
    @NotBlank(message = "Minimum 20 chars")
    @Size(min=20, max=300)
    private String description;
}
