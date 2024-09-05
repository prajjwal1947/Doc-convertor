package pdftohtml.pdftohtml.services;

import com.aspose.pdf.*;

import java.util.Arrays;
import java.util.Base64;
//import com.aspose.pdf.Color;
import com.aspose.pdf.drawing.Graph;
import com.aspose.words.CssStyleSheetType;
import com.aspose.words.NodeType;
import com.aspose.words.Run;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.aspose.pdf.Color.*;

public class Pdftohtml {
    public static String saveFile(String fileName, MultipartFile multipartFile)
            throws IOException {
        Path uploadPath = Paths.get("src\\test\\resource");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileCode = RandomStringUtils.randomAlphanumeric(8);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
        Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
//        File file = new File("src/test/resource/"+fileCode + "-" + fileName);
        Document pdfDocument = new Document("src/test/resource/" + fileCode + "-" + fileName);
        String htmlName = fileCode + "-" + fileName + ".html";
        pdfDocument.save(htmlName, SaveFormat.Html);
        File htmlFile = new File(htmlName);
        return fileCode + "-" + fileName;

    }

    public static void convertPDFtoHtml(String fileName) throws Exception {
        DocToHtml docToHtml= new DocToHtml();
        Document pdfDocument = new Document("src/test/resource/" + fileName + ".pdf");
        // Search text fragments and set edit option as remove unused fonts


//        HtmlSaveOptions htmlSaveOptions = new HtmlSaveOptions();
//        htmlSaveOptions.PartsEmbeddingMode = HtmlSaveOptions.PartsEmbeddingModes.EmbedAllIntoHtml;
//        htmlSaveOptions.RasterImagesSavingMode = HtmlSaveOptions.RasterImagesSavingModes.AsEmbeddedPartsOfPngPageBackground;
        DocSaveOptions docSaveOptions = new DocSaveOptions();
        // Define the type of output Word file
        docSaveOptions.setFormat(DocSaveOptions.DocFormat.DocX);

        // Set the recognition mode to Flow for enabling it for editing in future
        docSaveOptions.setMode(DocSaveOptions.RecognitionMode.Flow);

        // Set the Horizontal proximity that defines width of space between text elements as 2.5
        docSaveOptions.setRelativeHorizontalProximity(2.5f);

        // Switch on the recognition of bullets from the source PDF
        docSaveOptions.setRecognizeBullets(true);
        pdfDocument.save("src/test/resource/" + fileName + ".docx",docSaveOptions);
          docToHtml.Doctohtml(fileName,"docx");
//        String htmlFilePath = "src/test/resource/" + fileName + ".html";
//        JsoupService.removeHighlightedTextFromHtml(htmlFilePath);
////        removeHighlightFromHtml(htmlFilePath);
//        String cssContent = readCSSFile("src/test/resource/" + fileName + "_files/style.css");
//        updateHTMLWithInlineCSS("src/test/resource/" + fileName + ".html", cssContent);
//        System.out.println("PDF converted to HTML with inline CSS successfully.");

    }

    private static String readCSSFile(String cssFilePath) {
        try {
            return Files.readString(Paths.get(cssFilePath));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private static void updateHTMLWithInlineCSS(String htmlFilePath, String cssContent) {
        try {
            // Read the contents of the HTML file
            String htmlContent = Files.readString(Paths.get(htmlFilePath));

            // Parse the HTML using Jsoup
            org.jsoup.nodes.Document doc = Jsoup.parse(htmlContent);


            Elements linkElements = doc.head().select("link[href=\"style.css\"]");

            // Remove the link elements
            linkElements.remove();

            // Create a new style element and set its content to the CSS styles
            Element styleElement = doc.createElement("style");
            styleElement.attr("type", "text/css");
            TextNode cssTextNode = new TextNode(cssContent);
            styleElement.appendChild(cssTextNode);

            // Append the style element to the head of the document
            doc.head().appendChild(styleElement);

            // Update the HTML content with the modified document
            String updatedHTMLContent = doc.html();

            // Write the updated HTML content back to the file
            Files.write(Paths.get(htmlFilePath), updatedHTMLContent.getBytes());
//          String updatedHtml = updateHTMLWithBase64Images(updatedHTMLContent,"src/test/resource/demo_files");
//            Files.write(Paths.get(updatedHtml), updatedHTMLContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void removeHighlightFromHtml(String htmlFilePath) {
//        // Load the HTML file
//        com.aspose.pdf.HtmlLoadOptions htmlLoadOptions = new com.aspose.pdf.HtmlLoadOptions();
//        Document htmlDocument = new Document(htmlFilePath, htmlLoadOptions);
//
//        // Remove highlighting styles from the HTML file
//        com.aspose.pdf.PageCollection pages = htmlDocument.getPages();
//        for (com.aspose.pdf.Page page : pages) {
//            com.aspose.pdf.TextFragmentAbsorber textFragmentAbsorber = new com.aspose.pdf.TextFragmentAbsorber();
//            page.accept(textFragmentAbsorber);
//            com.aspose.pdf.TextFragmentCollection textFragments = textFragmentAbsorber.getTextFragments();
//            for (com.aspose.pdf.TextFragment textFragment : textFragments) {
//                textFragment.getTextState().setBackgroundColor(com.aspose.pdf.Color.getTransparent());
//            }
//        }
//
//        // Save the modified HTML file
//        htmlDocument.save(htmlFilePath);
//        System.out.println("Highlight removed from HTML successfully.");
//    }





}


