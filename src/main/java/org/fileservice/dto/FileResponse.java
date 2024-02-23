package org.fileservice.dto;

import java.time.LocalDateTime;

import org.springframework.util.MimeType;

import org.fileservice.domain.datatype.Status;
import org.fileservice.domain.datatype.TargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {

    private Long           id;
    private String         name;
    private Status         status;
    private Long           ownerId;
    private String         type;
    private LocalDateTime  createdAt;
    private TargetType     targetType;
    private Long           targetId;
    private FileAccessLink fileAccessLink;

    public void setType(MimeType mimeType) {
        this.type = mimeType.toString();
    }

}