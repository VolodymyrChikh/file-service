package org.fileservice.dto;


import org.fileservice.domain.datatype.InteractionType;

public record FileAccessLink(InteractionType accessType, String url) {

}