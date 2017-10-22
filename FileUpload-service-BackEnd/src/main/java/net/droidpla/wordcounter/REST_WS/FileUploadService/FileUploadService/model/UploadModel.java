package net.droidpla.wordcounter.REST_WS.FileUploadService.FileUploadService.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by min2ha on 19/10/2017.
 */
public class UploadModel {
    private String extraField;

    private MultipartFile[] files;

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
