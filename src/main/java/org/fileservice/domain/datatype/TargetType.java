package org.fileservice.domain.datatype;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TargetType {

    USER("users/%d/");
    private final String folderPrefix;

    public String getPrefixForId(Long id) {
        return folderPrefix.formatted(id);
    }
}