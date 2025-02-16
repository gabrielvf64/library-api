package com.box.library.response;

public class ReportResponse {

    private final String content;
    private final String contentType;
    private final String extension;

    public ReportResponse(String content, String contentType, String extension) {
        this.content = content;
        this.contentType = contentType;
        this.extension = extension;
    }

    public String getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }

    public String getExtension() {
        return extension;
    }
}
