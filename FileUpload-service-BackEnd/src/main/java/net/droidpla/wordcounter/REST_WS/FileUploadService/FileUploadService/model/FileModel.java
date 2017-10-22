package net.droidpla.wordcounter.REST_WS.FileUploadService.FileUploadService.model;

/**
 * Created by min2ha on 21/10/2017.
 */
public class FileModel {
    private String name;
    private String size;
    private String extension;
    private String dateCreated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
