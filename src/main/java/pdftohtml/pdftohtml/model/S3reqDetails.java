package pdftohtml.pdftohtml.model;

public class S3reqDetails {
    private String type;
    private String pathS3bucket;
    private String fileName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPathS3bucket() {
        return pathS3bucket;
    }

    public void setPathS3bucket(String pathS3bucket) {
        this.pathS3bucket = pathS3bucket;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
