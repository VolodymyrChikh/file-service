package org.fileservice.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import org.fileservice.domain.File;
import org.fileservice.domain.datatype.InteractionType;
import org.fileservice.dto.FileRequest;
import org.fileservice.dto.FileResponse;
import org.fileservice.mapper.helper.FileMapperHelper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = FileMapperHelper.class)
public interface FileMapper {

    @Mapping(target = "fileAccessLink", source = "file", qualifiedByName = "toFileAccessLink")
    FileResponse toFileResponse(File file, @Context InteractionType interactionType);

    @Mapping(target = "name", source = "fileRequest", qualifiedByName = "toFileName")
    File toFile(FileRequest fileRequest);
}