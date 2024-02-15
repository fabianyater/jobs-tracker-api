package com.api.jobstracker.infrastructure.model.request;

import com.api.jobstracker.commons.constant.FormatConstant;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApplicationRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String companyName;

    @NotEmpty
    @Pattern(regexp = FormatConstant.URL_PATTERN)
    private String url;

    @NotEmpty
    private String jobName;

    @NotEmpty
    @Pattern(regexp = FormatConstant.ONLY_LETTERS_PATTERN)
    private String status;
}
