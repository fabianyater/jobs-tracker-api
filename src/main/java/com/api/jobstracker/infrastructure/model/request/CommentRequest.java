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
public class CommentRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Pattern(regexp = FormatConstant.ONLY_NUMBERS_PATTERN)
    private int jobApplicationId;

    @NotEmpty
    private String comment;
}
