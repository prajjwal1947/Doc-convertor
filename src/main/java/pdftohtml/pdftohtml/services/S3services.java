package pdftohtml.pdftohtml.services;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import pdftohtml.pdftohtml.common.Constants;
import pdftohtml.pdftohtml.utility.UnzipUtility;

import java.io.*;

public class S3services {
    private AmazonS3 s3Client;
    private String tempResourcesPath = Constants.TEMP_RESOURCE_PATH;
    private AmazonS3 getS3Client(){
        if(s3Client==null){
            s3Client = new AmazonS3Client();
        }
         s3Client = getS3ClientLocal();  //for local
        return s3Client;
    }

    public AmazonS3 getS3ClientLocal() {
        String aws_access_key_id = "";   //add access key
        String aws_secret_access_key = ""; //add secret key
        AWSCredentials credentials = new BasicAWSCredentials(
                aws_access_key_id,
                aws_secret_access_key);
        if (s3Client == null) {
            s3Client = new AmazonS3Client(credentials);
        }
        return s3Client;
    }
    public InputStream downloadFileFromS3(String srcFilePath,String bucketName,String fileName)
            throws IOException  {
        String key = srcFilePath;
        S3Object fullObject = null;
        try {
            s3Client=getS3ClientLocal();
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
            S3ObjectInputStream objectContent = null;
            objectContent = fullObject.getObjectContent();
            File file = new File(tempResourcesPath + fileName);
            OutputStream outputStream = new FileOutputStream(file);
            IOUtils.copy((InputStream) objectContent, outputStream);
            outputStream.close();
            String contentEncoding = fullObject.getObjectMetadata().getContentEncoding();
            if(contentEncoding.equals("gzip")){
                UnzipUtility unzipUtility = new UnzipUtility();
                unzipUtility.unzip(tempResourcesPath + fileName);
            }
        } catch (AmazonServiceException e) {
            System.out.println(e.getErrorMessage());
        } catch (SdkClientException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fullObject.getObjectContent();
        }

    public String uploadObjectToS3(String filePath, String savePath, String bucketName) {
        String fileObjKeyName = savePath;
        String fileName = filePath;
        try {
            s3Client=getS3Client();
            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            System.out.println(e.getErrorMessage());

        } catch (SdkClientException e) {
            System.out.println(e.getMessage());

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
     return fileObjKeyName;
    }
    }





