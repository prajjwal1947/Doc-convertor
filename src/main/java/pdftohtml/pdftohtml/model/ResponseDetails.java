package pdftohtml.pdftohtml.model;

public class ResponseDetails {
    private String S3FilePath;
    private String Type;



    public String getS3FilePath() {
        return S3FilePath;
    }

    public void setS3FilePath(String s3FilePath) {
        S3FilePath = s3FilePath;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
