package pdftohtml.pdftohtml.services;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class JsoupService {


    public static void removeHighlightedTextFromHtml(String htmlFilePath) throws IOException {
        try {
            // Load the HTML file
            File input = new File(htmlFilePath);
            Document doc = Jsoup.parse(input, "UTF-8");

            // Find all elements with highlighting styles
            Elements elements = doc.select("[style*=background-color]");
            System.out.println("elemnets>>>>>>>>>>>>>>"+elements);
            // Remove the highlighting styles from the elements
            for (Element element : elements) {
                element.removeAttr("style");
            }

            // Save the modified HTML file
            FileOutputStream outputStream = new FileOutputStream(htmlFilePath);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(doc.outerHtml());
            writer.close();

            System.out.println("Highlighted text removed from HTML successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
