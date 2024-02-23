package org.fileservice.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.MimeType;

import org.fileservice.converter.MimeTypeConverter;
import org.fileservice.domain.datatype.Status;
import org.fileservice.domain.datatype.TargetType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static org.fileservice.domain.datatype.Status.DELETION_REQUESTED;
import static org.fileservice.domain.datatype.Status.PRESENT;
import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
@EntityListeners(AuditingEntityListener.class)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(updatable = false)
    private Long ownerId;

    @Enumerated(STRING)
    private Status status = PRESENT;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDate deletionDate;
    @Convert(converter = MimeTypeConverter.class)
    private MimeType  type;

    @Enumerated(STRING)
    private TargetType targetType;

    private Long targetId;

    public void requestDeletion(long deleteAfterDuration) {
        this.setStatus(DELETION_REQUESTED);
        this.setDeletionDate(LocalDate.now().plusDays(deleteAfterDuration));
    }

}