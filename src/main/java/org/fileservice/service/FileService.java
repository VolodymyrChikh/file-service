package org.fileservice.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.fileservice.configuration.properties.FileDeletionProperties.DeletionProperties;
import org.fileservice.domain.File;
import org.fileservice.dto.FileRequest;
import org.fileservice.dto.FileResponse;
import org.fileservice.exception.DetailedEntityNotFoundException;
import org.fileservice.mapper.FileMapper;
import org.fileservice.repository.FileRepository;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static org.fileservice.domain.datatype.InteractionType.READ;
import static org.fileservice.domain.datatype.InteractionType.WRITE;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository     fileRepository;
    private final FileMapper         fileMapper;
    private final DeletionProperties deletionProperties;

    @Transactional
    public FileResponse save(FileRequest fileRequest) {
        File savedFile = fileRepository.save(fileMapper.toFile(fileRequest));

        return fileMapper.toFileResponse(savedFile, WRITE);
    }

    @Transactional
    public Page<FileResponse> getAll(Predicate filters, Pageable pageable) {
        return fileRepository.findAll(filters, pageable).map(file -> fileMapper.toFileResponse(file, READ));
    }

    @Transactional
    public FileResponse getById(Long fileId) {
        File requestedFile = fileRepository
                .findById(fileId)
                .orElseThrow(() -> new DetailedEntityNotFoundException("Entity of id [%s] couldn't be found".formatted(fileId), "file", fileId));

        return fileMapper.toFileResponse(requestedFile, READ);
    }

    @Transactional
    public void deleteById(Long fileId) {
        log.info("Deletion on file[id={}] has been requested!", fileId);
        fileRepository.findById(fileId).ifPresent(this::requestDeletion);
    }

    @Transactional
    public FileResponse getAllByTargetId(Long targetId){
        List<File> files = fileRepository.getFileByTargetId(targetId);

        if (files == null || files.isEmpty()) {
            return null;
        }

        return fileMapper.toFileResponse(files.stream().max(Comparator.comparing(File::getCreatedAt)).orElse(null), READ);
    }

    private void requestDeletion(File file) {
        file.requestDeletion(deletionProperties.deleteAfterDuration());
        fileRepository.save(file);
    }

}