package org.fileservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import org.fileservice.domain.File;

public interface FileRepository extends JpaRepository<File, Long>, QuerydslPredicateExecutor<File> {

    List<File> getFileByTargetId(Long targetId);

}