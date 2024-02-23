package org.fileservice.mapper.helper;

import java.util.UUID;

import org.mapstruct.Context;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import org.fileservice.domain.File;
import org.fileservice.domain.datatype.InteractionType;
import org.fileservice.dto.FileAccessLink;
import org.fileservice.dto.FileRequest;
import org.fileservice.service.BlobService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileMapperHelper {

  private final BlobService blobService;

  @Named("toFileAccessLink")
  public FileAccessLink toFileAccessLink(File file, @Context InteractionType interactionType) {
    return new FileAccessLink(interactionType, blobService.generateAccessLink(file.getName(), interactionType));
  }

  @Named("toFileName")
  public String toFileName(FileRequest fileRequest) {
    return generateFileName(generateFolderName(fileRequest), fileRequest.getType().getSubtype());
  }

  private String generateFolderName(FileRequest fileRequest) {
    return fileRequest.getTargetType().getPrefixForId(fileRequest.getTargetId());
  }

  private String generateFileName(String folderName, String fileExtension) {
    return folderName + UUID.randomUUID() + ".%s".formatted(fileExtension);
  }

}