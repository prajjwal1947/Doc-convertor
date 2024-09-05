package pdftohtml.pdftohtml.services;


import com.aspose.words.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class DocToHtml {
    public  static  void Doctohtml(String fileName,String Type) throws Exception {
        System.out.println("coming here>>>>>>");
        Document docDocument = new Document("src/test/resource/" + fileName + "."+Type);
        docDocument.cleanup();
        // Get all paragraphs from the document.
        NodeCollection paragraphs = docDocument.getChildNodes(NodeType.PARAGRAPH, true);
        List<String> fonts = Arrays.asList("Andale Mono", "Arial", "Book Antiqua", "Comic Sans MS", "Courier New", "Georgia", "Helvetica", "Impact", "Symbol", "Tahoma", "Times New Roman", "Terbuchet MS", "Verdana");
        for (Paragraph paragraph : (Iterable<Paragraph>) paragraphs) {
            if (paragraph.getChildNodes(NodeType.RUN, true).get(0) != null) {
            }
            NodeCollection runArray = docDocument.getChildNodes(NodeType.RUN, true);
            for (Run run : (Iterable<Run>) runArray) {
                if (run.getFont() != null && !(run.getFont().getColor().equals(Color.BLACK))) {
                    String content = run.getText();
                    run.getFont().setColor(Color.BLACK);
                }
                String fontName = run.getFont().getName();
                if (!(fonts.contains(fontName))) {
                    run.getFont().setName("Times New Roman");
                }
                if (!(run.getFont().getHighlightColor().equals(Color.WHITE))) {
                    run.getFont().setHighlightColor(Color.WHITE);
                }
            }
        }
        HtmlSaveOptions options = new HtmlSaveOptions(SaveFormat.HTML);
        options.setExportTextInputFormFieldAsText(true);
        options.setExportRoundtripInformation(true);
        options.setExportPageSetup(true);
        options.setExportImagesAsBase64(true);
        options.setCssStyleSheetType(CssStyleSheetType.INLINE);
        docDocument.save("src/test/resource/" + fileName + ".html", options);
    }

}
