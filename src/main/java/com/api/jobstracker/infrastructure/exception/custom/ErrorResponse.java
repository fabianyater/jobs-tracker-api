package com.api.jobstracker.infrastructure.exception.custom;

import com.api.jobstracker.commons.constant.FormatConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.*;
import org.apache.commons.lang.StringUtils;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String type;

    @Builder.Default
    private int code = 0;

    @Builder.Default
    private String message = StringUtils.EMPTY;

    @Builder.Default
    private String location = StringUtils.EMPTY;

    @Builder.Default
    private String moreInfo = StringUtils.EMPTY;

    @Builder.Default
    private String uuid = StringUtils.EMPTY;

    @Builder.Default
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatConstant.ERROR_DATE_PATTERN)
    private ZonedDateTime timestamp = ZonedDateTime.now();
}
