package org.fileservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.fileservice.domain.File;
import org.fileservice.dto.FileRequest;
import org.fileservice.dto.FileResponse;
import org.fileservice.service.FileService;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    @ResponseStatus(CREATED)
    public FileResponse save(@RequestBody @Valid FileRequest fileRequest) {
        return fileService.save(fileRequest);
    }

    @GetMapping
    public Page<FileResponse> getAll(@QuerydslPredicate(root = File.class) Predicate filters,
                                     @PageableDefault Pageable pageable) {
        return fileService.getAll(filters, pageable);
    }

    @GetMapping(path = "/{fileId}")
    public FileResponse getById(@PathVariable Long fileId) {
        return fileService.getById(fileId);
    }

    @DeleteMapping("/{fileId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long fileId) {
        fileService.deleteById(fileId);
    }

}