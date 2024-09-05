package pdftohtml.pdftohtml.services;


import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

public class HtmltoPdf {

    public static void  htmlToPdfConverter(String filename,String type) throws Exception {


        Document document = new Document("src/test/resource/" + filename + "."+type);

        document.save("src/test/resource/" + filename + ".pdf",SaveFormat.PDF);
    }



}
