package pdftohtml.pdftohtml.controller;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pdftohtml.pdftohtml.model.RequestDetails;
import pdftohtml.pdftohtml.model.ResponseDetails;
import pdftohtml.pdftohtml.model.S3reqDetails;
import pdftohtml.pdftohtml.services.DocToHtml;
import pdftohtml.pdftohtml.services.HtmltoPdf;
import pdftohtml.pdftohtml.services.S3services;
import pdftohtml.pdftohtml.services.Pdftohtml;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class PdftoHtmlContoller {

    @GetMapping("/index")
    public String hello() {
        return "uploader";
    }

    @PostMapping("/upload")
    public ResponseEntity<RequestDetails> uploadFile(
            @RequestParam("file") MultipartFile multipartFile)
            throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();

      String filecode = Pdftohtml.saveFile(fileName, multipartFile);
//      String pdftohtml= PdftoHtmlService.pdftohtml(fileName,multipartFile,"pdf");
        RequestDetails response = new RequestDetails();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("src\\test\\resource" + filecode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/downloadFile") public ResponseEntity<ResponseDetails> downloadFile(@RequestBody S3reqDetails s3reqdetails) throws Exception {
        S3services s3service=new S3services();
        String uploadedPath = "";
        Pdftohtml pdftohtml=new Pdftohtml();
        DocToHtml doctohtml=new DocToHtml();
        HtmltoPdf htmltoPdf= new HtmltoPdf();
        S3ObjectInputStream objectContent = null;
        String path=s3reqdetails.getPathS3bucket();
        String filename=s3reqdetails.getFileName();
        String type=s3reqdetails.getType();
        System.out.println("dataaa>>>>>>"+filename);
        System.out.println("data>>>>>>>>>"+type);
        if(type.equals("doc")){
            objectContent = (S3ObjectInputStream) s3service.downloadFileFromS3(path, "", filename+".doc");
          doctohtml.Doctohtml(filename,type);
        } else if (type.equals("pdf")) {
            objectContent = (S3ObjectInputStream) s3service.downloadFileFromS3(path, "", filename+".pdf");
            pdftohtml.convertPDFtoHtml(filename);
            String htmlPath=path+".html";
             uploadedPath= s3service.uploadObjectToS3("src/test/resource/"+filename+".html",htmlPath,"");
        } else if (type.equals("docx")) {
            objectContent = (S3ObjectInputStream) s3service.downloadFileFromS3(path, "", filename+".docx");
            doctohtml.Doctohtml(filename,type);
            String htmlPath=path+".html";
//             uploadedPath= s3service.uploadObjectToS3("src/test/resource/"+filename+".html",htmlPath,"""");
        }
        else if (type.equals("html")){
            objectContent = (S3ObjectInputStream) s3service.downloadFileFromS3(path, "", filename+".html") ;
            htmltoPdf.htmlToPdfConverter(filename,type);

            String htmlPath=path.replace(".html", ".pdf");;
            uploadedPath= s3service.uploadObjectToS3("src/test/resource/"+filename+".pdf",htmlPath,"");
        }

        ResponseDetails responseDetails= new ResponseDetails();
        responseDetails.setS3FilePath(uploadedPath);
        responseDetails.setType(type);

        return new ResponseEntity<>(responseDetails, HttpStatus.OK);
    }
}
