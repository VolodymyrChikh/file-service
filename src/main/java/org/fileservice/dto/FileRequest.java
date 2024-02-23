package org.fileservice.dto;

import org.springframework.util.MimeType;

import org.fileservice.domain.datatype.TargetType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest {

    @NotNull
    private MimeType type;

    @NotNull
    @Min(1)
    private Long ownerId;

    @NotNull
    @Min(1)
    private Long targetId;

    @NotNull
    private TargetType targetType;
}