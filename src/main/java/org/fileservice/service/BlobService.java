package org.fileservice.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import org.fileservice.configuration.properties.SasProperties;
import org.fileservice.domain.datatype.InteractionType;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.sas.SasProtocol;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlobService {

  private final SasProperties.SasAccessProperties sasAccessProperties;
  private final BlobContainerClient               blobContainerClient;

  public String generateAccessLink(String fileName, InteractionType permission) {
    BlobSasPermission sasPermission = BlobSasPermission.parse(permission.getStringRepresentation());

    BlobServiceSasSignatureValues sasValues = new BlobServiceSasSignatureValues(OffsetDateTime
        .now()
        .plusSeconds(sasAccessProperties.accessDuration()), sasPermission)
        .setProtocol(SasProtocol.HTTPS_ONLY)
        .setCacheControl("no-cache");

    BlobClient blobClient = blobContainerClient.getBlobClient(fileName);

    return blobClient.getBlobUrl() + "?" + blobClient.generateSas(sasValues);
  }

  public void deleteFromStorage(String blobName) {
    blobContainerClient.getBlobClient(blobName).deleteIfExists();
  }

}