package com.api.jobstracker.infrastructure.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApplicationResponse {
    private Integer id;
    private String url;
    private String jobName;
    private String companyName;
    private String applicationDate;
    private String updateDate;
    private String status;
    private String description;
}
