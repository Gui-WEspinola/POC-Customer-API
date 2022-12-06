package io.GuiWEspinola.poc1.exception.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiErrorsDTO {

    private LocalDateTime timestamp;

    private String message;

    private Integer httpStatus;

    private String path;
}
